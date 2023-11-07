package ru.gozerov.presentation.screens.home.product_details

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowForwardIos
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.content.res.ResourcesCompat
import com.google.accompanist.drawablepainter.rememberDrawablePainter
import ru.gozerov.presentation.R
import ru.gozerov.presentation.utils.DefaultText

@OptIn(ExperimentalFoundationApi::class)
@Preview(showBackground = true, backgroundColor = 0xFFFFFFFF)
@Composable
fun ProductDetailsScreen() {
    val pagerState = rememberPagerState { 5 }
    val reviews = remember { mutableStateListOf(0, 1, 2) }
    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
    ) {
        HorizontalPager(
            modifier = Modifier
                .padding(24.dp)
                .fillMaxWidth()
                .aspectRatio(1f)
                .background(
                    color = colorResource(R.color.grey_light),
                    shape = RoundedCornerShape(16.dp)
                ),
            state = pagerState
        ) {
            Box(
                modifier = Modifier.fillMaxSize()
            ) {
                Image(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(48.dp),
                    painter = painterResource(id = R.drawable.img),
                    contentDescription = null
                )
                DefaultText(
                    modifier = Modifier
                        .align(Alignment.BottomStart)
                        .padding(start = 16.dp, bottom = 16.dp),
                    text = "${it + 1}/5 photo"
                )
            }
        }

        Spacer(modifier = Modifier.height(12.dp))
        DefaultText(
            modifier = Modifier.padding(horizontal = 24.dp),
            text = "TMA-2HD Wireless",
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold
        )
        Spacer(modifier = Modifier.height(12.dp))
        DefaultText(
            modifier = Modifier.padding(horizontal = 24.dp),
            text = "1000 $",
            fontSize = 16.sp,
            fontWeight = FontWeight.Bold,
            color = colorResource(id = R.color.red_price)
        )
        Spacer(modifier = Modifier.height(16.dp))
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.padding(horizontal = 24.dp)
        ) {
            Icon(
                painter = painterResource(id = R.drawable.baseline_star_16),
                //tint = colorResource(id = R.color.yellow_star),
                contentDescription = null
            )
            Spacer(modifier = Modifier.width(4.dp))
            DefaultText(text = "4.6")
            Spacer(modifier = Modifier.width(20.dp))
            DefaultText(text = "86 Reviews")
        }
        Divider(
            modifier = Modifier
                .padding(
                    horizontal = 24.dp,
                    vertical = 24.dp
                ),
            color = colorResource(id = R.color.grey_light)
        )

        Row(verticalAlignment = Alignment.CenterVertically) {
            Spacer(modifier = Modifier.width(24.dp))
            Image(
                modifier = Modifier.size(48.dp),
                painter = painterResource(id = R.drawable.baseline_account_circle_24),
                contentDescription = null
            )
            Spacer(modifier = Modifier.width(24.dp))
            Column(Modifier.weight(1f)) {
                DefaultText(text = "Samsung Electronics")
                Spacer(modifier = Modifier.height(4.dp))
                DefaultText(
                    text = "Official Store",
                    fontSize = 12.sp
                )
            }
            Icon(
                modifier = Modifier
                    .padding(end = 24.dp)
                    .size(16.dp),
                imageVector = Icons.Default.ArrowForwardIos,
                tint = colorResource(id = R.color.half_grey),
                contentDescription = null,
            )
        }

        Divider(
            modifier = Modifier.padding(24.dp),
            color = colorResource(id = R.color.grey_light)
        )
        DefaultText(
            modifier = Modifier.padding(horizontal = 24.dp),
            text = "Product Description",
            fontSize = 18.sp,
            fontWeight = FontWeight.Bold
        )
        DefaultText(
            modifier = Modifier
                .padding(horizontal = 24.dp, vertical = 16.dp),
            text = /*LoremIpsum().values.joinToString()*/ "Some description"
        )
        Divider(
            modifier = Modifier.padding(
                start = 24.dp,
                end = 24.dp,
                top = 8.dp,
                bottom = 24.dp
            ),
            color = colorResource(id = R.color.grey_light)
        )
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.End
        ) {
            DefaultText(
                modifier = Modifier.padding(horizontal = 24.dp),
                text = "Reviews (86)",
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold
            )
            Box(
                modifier = Modifier.weight(1f),
                contentAlignment = Alignment.CenterEnd
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.baseline_star_16),
                    //tint = colorResource(id = R.color.yellow_star),
                    contentDescription = null
                )
            }
            Spacer(modifier = Modifier.width(4.dp))
            DefaultText(
                modifier = Modifier.padding(end = 24.dp),
                text = "4.6",
                fontSize = 18.sp
            )
        }
        Column(
            Modifier.padding(vertical = 12.dp)
        ) {
            reviews.forEach { _ ->
                Column {
                    Row(
                        verticalAlignment = Alignment.CenterVertically
                    ){
                        Spacer(modifier = Modifier.width(24.dp))
                        Image(
                            modifier = Modifier.size(48.dp),
                            painter = painterResource(id = R.drawable.baseline_account_circle_24),
                            contentDescription = null
                        )
                        Spacer(modifier = Modifier.width(12.dp))
                        Column(
                            modifier = Modifier.weight(1f)
                        ) {
                            DefaultText(text = "Samsung Electronics")
                            Spacer(modifier = Modifier.height(4.dp))
                            Row {
                                repeat(5) {
                                    Icon(
                                        modifier = Modifier.padding(horizontal = 4.dp),
                                        painter = painterResource(id = R.drawable.baseline_star_16),
                                        //tint = colorResource(id = R.color.yellow_star),
                                        contentDescription = null
                                    )
                                }
                            }
                        }
                        DefaultText(
                            modifier = Modifier
                                .align(Alignment.Top)
                                .padding(top = 8.dp, end = 24.dp),
                            text = "1 year ago"
                        )
                    }
                    DefaultText(
                        modifier = Modifier
                            .padding(
                                start = 84.dp,
                                end = 24.dp,
                                top = 8.dp,
                                bottom = 12.dp
                            ),
                        text = "Some text"
                    )
                }

            }
        }
        Button(
            modifier = Modifier
                .padding(
                    start = 24.dp,
                    end = 24.dp,
                    top = 12.dp,
                    bottom = 24.dp
                )
                .fillMaxWidth(),
            shape = RoundedCornerShape(8.dp),
            colors = ButtonDefaults.buttonColors(containerColor = Color.White),
            border = BorderStroke(1.dp, Color.Black),
            onClick = { }
        ) {
            DefaultText(
                modifier = Modifier.padding(vertical = 4.dp),
                text = "See All Reviews"
            )
        }
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .background(colorResource(id = R.color.grey_light))
                .padding(vertical = 24.dp)
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.End
            ) {
                DefaultText(
                    modifier = Modifier.padding(horizontal = 24.dp),
                    text = "Featured Products",
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold
                )
                Box(
                    modifier = Modifier.weight(1f),
                    contentAlignment = Alignment.CenterEnd
                ) {
                    DefaultText(
                        modifier = Modifier.padding(end = 24.dp),
                        text = "See All",
                        color = colorResource(id = R.color.blue_ocean),
                        fontSize = 18.sp
                    )
                }

            }
            LazyRow(
                modifier = Modifier.padding(
                    start = 16.dp,
                    top = 16.dp
                ),
                state = rememberLazyListState()
            ) {
                items(5) {
                    ProductCard()
                }
            }
            Button(
                modifier = Modifier
                    .padding(
                        start = 24.dp,
                        end = 24.dp,
                        top = 24.dp
                    )
                    .fillMaxWidth(),
                shape = RoundedCornerShape(8.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = colorResource(id = R.color.blue_ocean)
                ),
                onClick = { }
            ) {
                DefaultText(
                    modifier = Modifier.padding(vertical = 4.dp),
                    text = "Add to Cart",
                    color = Color.White
                )
            }
        }
    }
}

@Preview(showBackground = true, backgroundColor = 0xFFFFFFF)
@Composable
fun ProductCard() {
    Card(
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        modifier = Modifier
            .padding(horizontal = 8.dp)
            .width(192.dp)
            .height(300.dp)

    ) {
        Image(
            modifier = Modifier
                .fillMaxWidth()
                .padding(
                    horizontal = 12.dp,
                    vertical = 16.dp
                )
                .height(180.dp),
            painter = rememberDrawablePainter(
                drawable = ResourcesCompat.getDrawable(
                    LocalContext.current.resources,
                    R.drawable.background_product_card,
                    null
                )
            ),
            contentDescription = null
        )
        DefaultText(
            modifier = Modifier
                .padding(horizontal = 12.dp)
                .fillMaxWidth(),
            text = "Good 1",
            fontWeight = FontWeight.Bold,
            maxLines = 2
        )
        DefaultText(
            modifier = Modifier
                .padding(
                    top = 8.dp,
                    start = 12.dp
                ),
            text = "1000 $",
            fontWeight = FontWeight.Bold,
            maxLines = 1,
            color = colorResource(id = R.color.red_price)
        )
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .padding(
                    horizontal = 12.dp,
                    vertical = 8.dp
                )
        ) {
            Icon(
                painter = painterResource(id = R.drawable.baseline_star_16),
                //tint = colorResource(id = R.color.yellow_star),
                contentDescription = null
            )
            Spacer(modifier = Modifier.width(4.dp))
            DefaultText(text = "4.6")
            Spacer(modifier = Modifier.width(20.dp))
            DefaultText(text = "86 Reviews")
            Box(
                modifier = Modifier.weight(1f),
                contentAlignment = Alignment.CenterEnd
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.baseline_more_vert_24),
                    contentDescription = null,
                    modifier = Modifier.size(18.dp)
                )
            }
        }
    }
}