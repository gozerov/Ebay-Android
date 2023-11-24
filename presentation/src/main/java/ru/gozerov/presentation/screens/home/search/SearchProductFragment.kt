package ru.gozerov.presentation.screens.home.search

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.History
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.platform.ViewCompositionStrategy
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextRange
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.TextUnitType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.fragment.app.viewModels
import dagger.hilt.android.AndroidEntryPoint
import ru.gozerov.presentation.R
import ru.gozerov.presentation.utils.BaseFragment
import ru.gozerov.presentation.utils.DefaultText
import ru.gozerov.presentation.utils.Screens
import ru.gozerov.presentation.utils.SearchProduct
import ru.gozerov.presentation.utils.ToolbarAction
import ru.gozerov.presentation.utils.ToolbarHolder
import ru.gozerov.presentation.utils.findNavigationProvider

@AndroidEntryPoint
class SearchProductFragment : BaseFragment<SearchProductViewModel<SearchProductIntent, SearchProductViewState>>() {

    override val viewModel: SearchProductViewModel<SearchProductIntent, SearchProductViewState> by viewModels { factory }

    override fun setNavigator() {
        findNavigationProvider().setNavigator(requireActivity(), R.id.fragmentContainerTabs)
    }

    override var actions: Map<ToolbarHolder.ActionType, ToolbarAction?> = mapOf(
        ToolbarHolder.ActionType.NAV_UP to {}
    )

    override fun onAttach(context: Context) {
        toolbarLabel = "Search"
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
            setContent {
                SearchProductScreen()
            }
        }
    }

    @Composable
    fun SearchProductScreen() {
        var viewState: SearchProductViewState by remember { mutableStateOf(SearchProductViewState.Empty) }

        LaunchedEffect(key1 = null) {
            viewModel.viewState.collect { state ->
                viewState = state
            }
        }
        val textFieldValue = remember { mutableStateOf(TextFieldValue(text = "", TextRange(0))) }
        Column(
            modifier = Modifier
                .fillMaxSize()
        ) {
            SearchProduct(
                modifier = Modifier
                    .fillMaxWidth()
                    .align(Alignment.CenterHorizontally)
                    .padding(24.dp)
                    .clip(RoundedCornerShape(4.dp)),
                textFieldValue = textFieldValue,
                onSearch = {
                    viewModel.handleIntent(SearchProductIntent.SearchByName(textFieldValue.value.text))
                },
                onEmptyField = {
                    viewModel.handleIntent(SearchProductIntent.EmptySearch)
                }
            )

            when (viewState) {
                is SearchProductViewState.Empty -> {
                    RecentSearchList(textFieldValue = textFieldValue)
                }
                is SearchProductViewState.SuccessSearch -> {
                    if (textFieldValue.value.text.isNotBlank())
                        CurrentSearchList(textFieldValue = textFieldValue, viewState as SearchProductViewState.SuccessSearch)
                    else
                        RecentSearchList(textFieldValue = textFieldValue)
                }
                is SearchProductViewState.Error -> {}
            }
        }

    }

    @Composable
    fun ColumnScope.RecentSearchList(textFieldValue: MutableState<TextFieldValue>) {
        val historyItems = remember { mutableStateListOf("SemicolonSpace", "Jetpack Compose", "Android") }
        DefaultText(
            text = "Recent searched",
            fontWeight = FontWeight.Bold,
            fontSize = 16.sp,
            modifier = Modifier
                .align(Alignment.Start)
                .padding(start = 24.dp)
        )
        LazyColumn(
            contentPadding = PaddingValues(
                horizontal = 24.dp,
                vertical = 8.dp
            ),
            state = rememberLazyListState(),
            content = {
                items(historyItems) {
                    Row(
                        modifier = Modifier
                            .padding(vertical = 8.dp)
                            .fillMaxWidth()
                            .clickable {
                                textFieldValue.value = TextFieldValue(it, TextRange(it.length))
                                viewModel.handleIntent(SearchProductIntent.SearchByName(it))
                            },
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(
                            modifier = Modifier
                                .padding(end = 16.dp)
                                .width(20.dp)
                                .height(20.dp)
                                .clickable {
                                    findNavigationProvider()
                                        .getRouter()
                                        .navigateTo(Screens.composableNavigation())
                                },
                            imageVector = Icons.Default.History,
                            contentDescription = null,
                            tint = colorResource(id = R.color.half_grey)
                        )
                        DefaultText(text = it)
                        Box(
                            modifier = Modifier.fillMaxWidth(),
                            contentAlignment = Alignment.CenterEnd
                        ) {
                            Icon(
                                modifier = Modifier
                                    .width(20.dp)
                                    .height(20.dp),
                                painter = painterResource(id = R.drawable.baseline_close_24),
                                contentDescription = null,
                            )
                        }

                    }
                }
            }
        )
    }

    @Composable
    fun ColumnScope.CurrentSearchList(textFieldValue: MutableState<TextFieldValue>, state: SearchProductViewState.SuccessSearch) {
        if (state.goods.isNotEmpty()) {
            DefaultText(
                text = "Search Suggestions",
                fontWeight = FontWeight.Bold,
                fontSize = 16.sp,
                modifier = Modifier
                    .align(Alignment.Start)
                    .padding(start = 24.dp)
            )
            Spacer(modifier = Modifier.height(12.dp))
            LazyColumn(
                contentPadding = PaddingValues(
                    horizontal = 24.dp,
                    vertical = 8.dp
                ),
                state = rememberLazyListState(),
                content = {
                    items(state.goods) {
                        Row(
                            modifier = Modifier
                                .padding(vertical = 8.dp)
                                .fillMaxWidth()
                                .clickable {
                                    textFieldValue.value =
                                        TextFieldValue(it.name, TextRange(it.name.length))
                                    viewModel.handleIntent(SearchProductIntent.SearchByName(it.name))
                                },
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Icon(
                                modifier = Modifier
                                    .padding(end = 16.dp)
                                    .width(20.dp)
                                    .height(20.dp),
                                imageVector = Icons.Default.Search,
                                contentDescription = null,
                                tint = colorResource(id = R.color.half_grey)
                            )
                            DefaultText(text = it.name)
                        }
                    }
                }
            )
        } else {
            NoItems()
        }

    }

    @Preview
    @Composable
    fun NoItems() {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Image(
                modifier = Modifier.size(64.dp),
                painter = painterResource(id = R.drawable.img),
                contentDescription = ""
            )
            Spacer(modifier = Modifier.height(16.dp))
            DefaultText(
                text = "There are no suitable products",
                fontSize = 18.sp
            )
            Spacer(modifier = Modifier.height(16.dp))
            DefaultText(
                modifier = Modifier.padding(horizontal = 36.dp),
                text = "Please try using other keywords to find the product name",
                color = colorResource(id = R.color.half_grey),
                textAlign = TextAlign.Center
            )
        }
    }

}