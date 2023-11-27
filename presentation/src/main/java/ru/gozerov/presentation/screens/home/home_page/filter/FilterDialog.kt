package ru.gozerov.presentation.screens.home.home_page.filter

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CheckboxDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.RangeSlider
import androidx.compose.material3.SliderDefaults
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.TabRowDefaults
import androidx.compose.material3.TabRowDefaults.tabIndicatorOffset
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import kotlinx.coroutines.launch
import ru.gozerov.presentation.R
import ru.gozerov.presentation.utils.DefaultDivider
import ru.gozerov.presentation.utils.DefaultText

const val PAGE_FILTER = 0
const val PAGE_SORTING = 1

@Composable
fun FilterDialog(onDismissRequest: () -> Unit) {
    Dialog(
        onDismissRequest = { onDismissRequest() }
    ) {
        Card(
            modifier = Modifier
                .clip(RoundedCornerShape(16.dp))
                .width(340.dp)
                .height(480.dp),
            shape = RectangleShape,
            colors = CardDefaults.cardColors(containerColor = Color.White)
        ) {
            TitleSection(onDismissRequest)
            TabsSection()
        }
    }
}


@Composable
fun TitleSection(onDismissRequest: () -> Unit) {
    Row(
        modifier = Modifier
            .padding(start = 24.dp, end = 12.dp, top = 12.dp)
            .fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        DefaultText(
            text = "Filter & Sorting",
            textAlign = TextAlign.Start,
            fontWeight = FontWeight.Bold,
            fontSize = 16.sp
        )
        Box(
            modifier = Modifier.fillMaxWidth(),
            contentAlignment = Alignment.CenterEnd
        ) {
            IconButton(onClick = { onDismissRequest() }) {
                Icon(
                    imageVector = Icons.Default.Close,
                    contentDescription = null
                )
            }
        }

    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun TabsSection() {
    val tabs = listOf("Filter", "Sorting")
    val pagerState = rememberPagerState { tabs.size }
    val coroutineScope = rememberCoroutineScope()
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight()
    ) {
        TabRow(
            modifier = Modifier.width(160.dp),
            selectedTabIndex = pagerState.currentPage,
            divider = {  },
            containerColor = Color.White,
            indicator = {
                it.forEachIndexed { index, tabPosition ->
                    if (index == pagerState.currentPage) {
                        TabRowDefaults.Indicator(
                            height = 2.dp,
                            modifier = Modifier
                                .tabIndicatorOffset(tabPosition)
                                .padding(horizontal = 16.dp),
                            color = colorResource(id = R.color.blue_ocean)
                        )
                    }
                }
            }
        ) {
            tabs.forEachIndexed { ind, tab ->
                Tab(
                    modifier = Modifier
                        .height(28.dp)
                        .align(Alignment.Start),
                    text = {
                        DefaultText(text = tab)
                    },
                    selected = ind == pagerState.currentPage,
                    onClick = {
                        coroutineScope.launch {
                            pagerState.animateScrollToPage(ind)
                        }
                    }
                )
            }
        }
        Divider(thickness = 1.dp, color = colorResource(id = R.color.grey_light))
            HorizontalPager(
            state = pagerState,
            modifier = Modifier.fillMaxWidth()
        ) { pageIndex ->
            when(pageIndex) {
                PAGE_FILTER -> FilterSection()
                PAGE_SORTING -> SortingSection()
            }
        }
    }
}

@Composable
fun FilterSection() {
    Column(
        modifier = Modifier
            .padding(horizontal = 24.dp, vertical = 16.dp)
            .fillMaxSize()
    ) {
        PriceSliderSection()
        DefaultDivider(vertical = 20.dp, horizontal = 0.dp)
        CategorySection()
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PriceSliderSection() {
    var sliderPosition by remember { mutableStateOf(0f..1000f) }
    DefaultText(text = "Price")
    RangeSlider(
        value = sliderPosition,
        colors = SliderDefaults.colors(
            thumbColor = colorResource(id = R.color.blue_ocean),
            activeTrackColor = colorResource(id = R.color.blue_ocean),
            inactiveTrackColor = colorResource(id = R.color.blue_10)
        ),
        valueRange = 0f..1000f,
        onValueChange = {
            sliderPosition = it
        },
        startThumb = { SliderThumb() },
        endThumb = { SliderThumb() }
    )
    Row {
        DefaultText(
            modifier = Modifier.padding(start = 4.dp),
            text = (10*sliderPosition.start.toInt()).toString(),
            fontWeight = FontWeight.Bold
        )
        DefaultText(
            modifier = Modifier
                .padding(end = 4.dp)
                .fillMaxWidth(),
            text = (10*sliderPosition.endInclusive.toInt()).toString(),
            textAlign = TextAlign.End,
            fontWeight = FontWeight.Bold
        )
    }
}

@Composable
fun SliderThumb() {
    Box(
        modifier = Modifier
            .size(24.dp)
            .clip(CircleShape)
            .background(colorResource(id = R.color.blue_ocean)),
        contentAlignment = Alignment.Center
    ) {
        Box(
            modifier = Modifier
                .size(12.dp)
                .clip(CircleShape)
                .background(Color.White)
        )
    }
}

@Preview(showBackground = true, backgroundColor = 0xFFFFFFFF)
@Composable
fun CategorySection() {
    var isChecked by remember { mutableStateOf(false) }
    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        DefaultText(text = "Computer")
        Box(
            modifier = Modifier.fillMaxWidth(),
            contentAlignment = Alignment.CenterEnd
        ) {
            Checkbox(
                modifier = Modifier.align(Alignment.CenterEnd),
                checked = isChecked,
                onCheckedChange = {
                    isChecked = it
                },
                colors = CheckboxDefaults.colors(
                    uncheckedColor = colorResource(id = R.color.half_grey),
                    checkedColor = colorResource(id = R.color.green_checkbox)
                )
            )
        }
    }
}

@Composable
fun SortingSection() {
    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        Text(text = "Sorting")
    }
}