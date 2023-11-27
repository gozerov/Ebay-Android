package ru.gozerov.presentation.screens.home.selected_category

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.platform.ViewCompositionStrategy
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.TextRange
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.os.bundleOf
import androidx.fragment.app.viewModels
import dagger.hilt.android.AndroidEntryPoint
import ru.gozerov.domain.models.Good
import ru.gozerov.presentation.R
import ru.gozerov.presentation.screens.home.home_page.filter.FilterDialog
import ru.gozerov.presentation.utils.BaseFragment
import ru.gozerov.presentation.utils.DefaultText
import ru.gozerov.presentation.utils.OutlinedButton
import ru.gozerov.presentation.utils.ProductCard
import ru.gozerov.presentation.utils.SearchProduct
import ru.gozerov.presentation.utils.ToolbarAction
import ru.gozerov.presentation.utils.ToolbarHolder
import ru.gozerov.presentation.utils.findNavigationProvider
import ru.gozerov.presentation.utils.log
import kotlin.math.ceil

@AndroidEntryPoint
class SelectedCategoryFragment : BaseFragment<SelectedCategoryViewModel<SelectedCategoryIntent, SelectedCategoryViewState>>() {

    override val viewModel: SelectedCategoryViewModel<SelectedCategoryIntent, SelectedCategoryViewState>  by viewModels { factory }

    override fun setNavigator() {
        findNavigationProvider().setNavigator(requireActivity(), R.id.fragmentContainerGlobal)
    }

    override var actions: Map<ToolbarHolder.ActionType, ToolbarAction?> = mapOf(
        ToolbarHolder.ActionType.NAV_UP to {},
        ToolbarHolder.ActionType.CART to {}
    )

    override fun onAttach(context: Context) {
        toolbarLabel = "Category"
        titleStyle = R.style.BlackTitleTextAppearance
        super.onAttach(context)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return ComposeView(requireContext()).apply {
            setViewCompositionStrategy(ViewCompositionStrategy.DisposeOnViewTreeLifecycleDestroyed)
            arguments?.getString(ARG_CATEGORY)?.let {
                setContent {
                    LaunchedEffect(key1 = null) {
                        viewModel.handleIntent(SelectedCategoryIntent.LoadProducts(it))
                    }
                    SelectedCategoryScreen(it)
                }
            }
        }
    }

    @Composable
    fun SelectedCategoryScreen(category: String) {
        val products: MutableState<List<Good>?> = remember { mutableStateOf(null) }
        val showFilterDialog = remember { mutableStateOf(false) }

        LaunchedEffect(key1 = null) {
            viewModel.viewState.collect { state ->
                when(state) {
                    is SelectedCategoryViewState.Empty -> {}
                    is SelectedCategoryViewState.SuccessLoading -> {
                        products.value = state.products
                    }
                    is SelectedCategoryViewState.Error -> {}
                }
            }
        }
        if (products.value?.isEmpty() == false) {
            val textFieldValue = remember { mutableStateOf(TextFieldValue(text = "", TextRange(0))) }
            if (showFilterDialog.value) {
                FilterDialog {
                    showFilterDialog.value = false
                }
            }
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .verticalScroll(rememberScrollState())
            ) {
                CategoryName(category = category)
                SearchProduct(
                    modifier = Modifier
                        .fillMaxWidth()
                        .align(Alignment.CenterHorizontally)
                        .padding(horizontal = 24.dp)
                        .clip(RoundedCornerShape(4.dp)),
                    textFieldValue = textFieldValue
                )
                Spacer(modifier = Modifier.height(32.dp))
                ProductList(products = products.value!!, showFilterDialog)
            }
        }
    }

    @Composable
    fun CategoryName(category: String) {
        DefaultText(
            modifier = Modifier.padding(all = 24.dp),
            text = category,
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold
        )
    }

    @Composable
    fun ProductList(products: List<Good>, showFilterDialog: MutableState<Boolean>) {
        Column(modifier = Modifier
            .fillMaxWidth()
            .background(color = colorResource(id = R.color.grey_light))
            ) {
            Spacer(modifier = Modifier.height(16.dp))
            LazyVerticalGrid(
                modifier = Modifier
                    .height((324 * ceil(products.size.toDouble() / 2)).dp)
                    .fillMaxWidth(),
                columns = GridCells.Fixed(2),
                userScrollEnabled = false,
                content = {
                    items(products.size) {
                        ProductCard(product = products[it], context = requireContext())
                    }
                }
            )
            OutlinedButton(
                modifier = Modifier
                    .padding(
                        start = 24.dp, end = 24.dp, top = 24.dp, bottom = 24.dp
                    )
                    .fillMaxWidth(),
                onClick = {
                    showFilterDialog.value = true
                },
                text = "Filter & Sorting"
            )
       }
    }

    companion object {

        private const val ARG_CATEGORY = "ARG_CATEGORY"

        fun newInstance(category: String): SelectedCategoryFragment {
            return SelectedCategoryFragment().apply {
                arguments = bundleOf(ARG_CATEGORY to category)
            }
        }
    }

}