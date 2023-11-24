package ru.gozerov.presentation.screens.home.selected_category

import android.content.Context
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.appcompat.app.AlertDialog
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.ScrollState
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.FlingBehavior
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.gestures.scrollable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.NoPhotography
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.platform.ViewCompositionStrategy
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextRange
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.os.bundleOf
import androidx.fragment.app.viewModels
import coil.compose.AsyncImage
import dagger.hilt.android.AndroidEntryPoint
import ru.gozerov.domain.models.Good
import ru.gozerov.presentation.R
import ru.gozerov.presentation.databinding.DialogProductPopUpBinding
import ru.gozerov.presentation.utils.BaseFragment
import ru.gozerov.presentation.utils.DefaultText
import ru.gozerov.presentation.utils.SearchProduct
import ru.gozerov.presentation.utils.StarIconFull
import ru.gozerov.presentation.utils.ToolbarAction
import ru.gozerov.presentation.utils.ToolbarHolder
import ru.gozerov.presentation.utils.findNavigationProvider

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
                    SelectedCategoryScreen(it)
                }
            }
        }
    }


    @Composable
    fun SelectedCategoryScreen(category: String) {
        val textFieldValue = remember { mutableStateOf(TextFieldValue(text = "", TextRange(0))) }
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
            Spacer(modifier = Modifier.height(24.dp))
            ProductList()
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
    fun ProductList() {
        Column(modifier = Modifier
            .fillMaxWidth()
            .background(color = colorResource(id = R.color.grey_light)),) {
            Spacer(modifier = Modifier.height(16.dp))
            LazyVerticalGrid(
                modifier = Modifier
                    .fillMaxWidth()
                    .height((324 * 2).dp),
                columns = GridCells.Fixed(2),
                userScrollEnabled = false,
                content = {
                    items(4) {
                        ProductCard(product = Good(1, "Good", "Description", 1000))
                    }
                }
            )
            Button(modifier = Modifier
                .padding(
                    start = 24.dp, end = 24.dp, top = 12.dp, bottom = 24.dp
                )
                .fillMaxWidth(),
                shape = RoundedCornerShape(8.dp),
                colors = ButtonDefaults.buttonColors(containerColor = Color.White),
                border = BorderStroke(1.dp, Color.Black),
                onClick = { }) {
                DefaultText(
                    modifier = Modifier
                        .padding(vertical = 4.dp),
                    text = "Filter & Sorting"
                )
            }
        }
    }

    @Composable
    fun NoPhoto(modifier: Modifier = Modifier) {
        Icon(
            modifier = modifier,
            imageVector = Icons.Default.NoPhotography,
            contentDescription = null
        )
    }

    @Composable
    fun ProductCard(product: Good) {
        Card(
            shape = RoundedCornerShape(16.dp),
            colors = CardDefaults.cardColors(containerColor = Color.White),
            modifier = Modifier
                .padding(all = 8.dp)
                .width(192.dp)
                .height(308.dp)

        ) {
            if (product.images.isNullOrEmpty())
                NoPhoto(modifier = Modifier
                    .height(212.dp)
                    .padding(horizontal = 36.dp)
                    .fillMaxWidth()
                )
            else
                AsyncImage(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp, vertical = 16.dp)
                        .height(180.dp),
                    model = product.images!!.first(),
                    contentDescription = null
                )
            DefaultText(
                modifier = Modifier
                    .padding(horizontal = 12.dp)
                    .fillMaxWidth(),
                text = product.name,
                fontWeight = FontWeight.Bold,
                maxLines = 2
            )
            DefaultText(
                modifier = Modifier.padding(
                    top = 8.dp, start = 12.dp
                ),
                text = "${product.price} $",
                fontWeight = FontWeight.Bold,
                maxLines = 1,
                color = colorResource(id = R.color.red_price)
            )
            Row(
                verticalAlignment = Alignment.CenterVertically, modifier = Modifier.padding(
                    horizontal = 12.dp, vertical = 8.dp
                )
            ) {
                product.rating?.let {
                    StarIconFull()
                    Spacer(modifier = Modifier.width(4.dp))
                    DefaultText(text = it.toString())
                    Spacer(modifier = Modifier.width(20.dp))
                    DefaultText(
                        text = "${product.reviews?.size ?: 0} Reviews",
                        fontSize = 12.sp
                    )
                } ?: DefaultText(
                    text = "No Reviews",
                    fontSize = 12.sp
                )
                Box(
                    modifier = Modifier.weight(1f), contentAlignment = Alignment.CenterEnd
                ) {
                    Icon(
                        painter = painterResource(id = R.drawable.baseline_more_vert_24),
                        contentDescription = null,
                        modifier = Modifier
                            .size(18.dp)
                            .clickable {
                                val dialogBinding =
                                    DialogProductPopUpBinding.inflate(LayoutInflater.from(context))
                                val dialog = AlertDialog
                                    .Builder(requireContext())
                                    .setView(dialogBinding.root)
                                    .setCancelable(true)
                                    .create()
                                val width =
                                    requireContext().resources.getDimensionPixelOffset(R.dimen.dialog_width)
                                dialogBinding.closeDialog.setOnClickListener {
                                    dialog.cancel()
                                }
                                dialog.window?.setBackgroundDrawable(ColorDrawable(android.graphics.Color.TRANSPARENT))
                                dialog.show()
                                dialog.window?.setLayout(
                                    width,
                                    LinearLayout.LayoutParams.WRAP_CONTENT
                                )
                            }
                    )
                }

            }
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