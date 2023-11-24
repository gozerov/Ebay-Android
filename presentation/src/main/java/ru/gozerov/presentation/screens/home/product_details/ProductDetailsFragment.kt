package ru.gozerov.presentation.screens.home.product_details

import android.content.Context
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.annotation.StyleRes
import androidx.appcompat.app.AlertDialog
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
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
import androidx.compose.foundation.pager.PagerState
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowForwardIos
import androidx.compose.material.icons.filled.NoPhotography
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.ViewCompositionStrategy
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.content.res.ResourcesCompat
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImage
import com.google.accompanist.drawablepainter.rememberDrawablePainter
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import ru.gozerov.domain.models.Good
import ru.gozerov.domain.models.Review
import ru.gozerov.presentation.R
import ru.gozerov.presentation.databinding.DialogProductPopUpBinding
import ru.gozerov.presentation.utils.BaseFragment
import ru.gozerov.presentation.utils.DefaultDivider
import ru.gozerov.presentation.utils.DefaultText
import ru.gozerov.presentation.utils.StarIconFull
import ru.gozerov.presentation.utils.StarIconOutlined
import ru.gozerov.presentation.utils.ToolbarAction
import ru.gozerov.presentation.utils.ToolbarHolder
import ru.gozerov.presentation.utils.findNavigationProvider
import java.text.DecimalFormat


@AndroidEntryPoint
class ProductDetailsFragment : BaseFragment<ProductDetailsViewModel<ProductDetailsIntent, ProductDetailsViewState>>() {

    override val viewModel: ProductDetailsViewModel<ProductDetailsIntent, ProductDetailsViewState> by viewModels { factory }

    override var actions: Map<ToolbarHolder.ActionType, ToolbarAction?> = mapOf(
        ToolbarHolder.ActionType.NAV_UP to {},
        ToolbarHolder.ActionType.SHARE to null,
        ToolbarHolder.ActionType.CART to null
    )

    override fun setNavigator() {
        findNavigationProvider().setNavigator(requireActivity(), R.id.fragmentContainerTabs)
    }
    override fun onAttach(context: Context) {
        toolbarLabel = "ProductDetails"
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
            arguments?.getInt(ARG_PRODUCT_ID)?.let {
                setContent {
                    ProductDetailsScreen(productId = it)
                }
            }
        }
    }

    @OptIn(ExperimentalFoundationApi::class)
    @Composable
    fun ProductDetailsScreen(productId: Int) {
        val product: MutableState<Good?> = remember { mutableStateOf(null) }
        val featuredProducts: MutableState<List<Good>?> = remember { mutableStateOf(null) }
        LaunchedEffect(key1 = null) {
            launch {
                viewModel.handleIntent(ProductDetailsIntent.LoadProduct(id = productId))
            }
            launch {
                viewModel.viewState.collect {
                    when (it) {
                        is ProductDetailsViewState.Empty -> {}
                        is ProductDetailsViewState.SuccessLoading -> {
                            product.value = it.good
                        }
                        is ProductDetailsViewState.FeaturedProductsLoaded -> {
                            featuredProducts.value = it.products
                        }
                        is ProductDetailsViewState.Error -> {}
                    }
                }
            }
        }
        product.value?.let {
            val pagerState = rememberPagerState { it.images?.size ?: 0 }
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .verticalScroll(rememberScrollState())
            ) {
                ImageGallery(pagerState = pagerState, it.images)
                Spacer(modifier = Modifier.height(12.dp))
                NameWithPriceSection(it)
                DefaultDivider()
                SellerSection()
                DefaultDivider()
                ProductDescriptionSection(it)
                MainReviewsSection(it.reviews, it.rating ?: 0.0)
                featuredProducts.value?.let { featuredProducts ->
                    FeaturedProductsSection(featuredProducts)
                }
            }
        }

    }

    @OptIn(ExperimentalFoundationApi::class)
    @Composable
    fun ImageGallery(pagerState: PagerState, images: List<String>?) {
        if (images == null) {
            Box(
                modifier = Modifier
                    .padding(24.dp)
                    .fillMaxWidth()
                    .aspectRatio(1f)
                    .background(
                        color = colorResource(R.color.grey_light),
                        shape = RoundedCornerShape(16.dp)
                    ),
                contentAlignment = Alignment.Center
            ) {
                NoPhoto(modifier = Modifier
                    .padding(96.dp)
                    .fillMaxSize())
            }
        } else {
            HorizontalPager(
                modifier = Modifier
                    .padding(24.dp)
                    .fillMaxWidth()
                    .aspectRatio(1f)
                    .background(
                        color = colorResource(R.color.grey_light), shape = RoundedCornerShape(16.dp)
                    ), state = pagerState
            ) { imgIndex ->
                Box(
                    modifier = Modifier.fillMaxSize()
                ) {
                    if (images.isEmpty())
                        NoPhoto()
                    else {
                        AsyncImage(
                            modifier = Modifier
                                .fillMaxSize()
                                .padding(48.dp),
                            model = images[imgIndex],
                            contentDescription = null
                        )

                    }

                    DefaultText(
                        modifier = Modifier
                            .align(Alignment.BottomStart)
                            .padding(start = 16.dp, bottom = 16.dp),
                        text = "${imgIndex + 1}/${pagerState.pageCount} photo"
                    )
                }
            }
        }
    }

    @Composable
    fun NameWithPriceSection(product: Good) {
        DefaultText(
            modifier = Modifier.padding(horizontal = 24.dp),
            text = product.name,
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold
        )
        Spacer(modifier = Modifier.height(12.dp))
        DefaultText(
            modifier = Modifier.padding(horizontal = 24.dp),
            text = "${product.price} $",
            fontSize = 16.sp,
            fontWeight = FontWeight.Bold,
            color = colorResource(id = R.color.red_price)
        )
        Spacer(modifier = Modifier.height(16.dp))
        ReviewsSection(product.reviews?.size ?: 0, product.rating ?: 0.0)
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
    fun ReviewsSection(reviewsCount: Int, totalRating: Double) {
        if (reviewsCount > 0) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.padding(horizontal = 24.dp)
            ) {
                StarIconFull()
                Spacer(modifier = Modifier.width(4.dp))
                DefaultText(text = "$totalRating")
                Spacer(modifier = Modifier.width(20.dp))
                DefaultText(text = "$reviewsCount Reviews")
            }
        }
    }

    @Composable
    fun SellerSection() {
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
                    text = "Official Store", fontSize = 12.sp
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
    }

    @Composable
    fun ProductDescriptionSection(product: Good) {
        DefaultText(
            modifier = Modifier.padding(horizontal = 24.dp),
            text = "Product Description",
            fontSize = 18.sp,
            fontWeight = FontWeight.Bold
        )
        DefaultText(
            modifier = Modifier.padding(horizontal = 24.dp, vertical = 16.dp),
            text = product.description
        )
    }

    @Composable
    fun MainReviewsSection(reviews: List<Review>?, totalRating: Double) {
        if (!reviews.isNullOrEmpty()) {
            DefaultDivider(end = 8.dp)
            ReviewsSectionBold(reviews.size, totalRating)
            Spacer(modifier = Modifier.height(12.dp))
            Column(
                Modifier.padding(vertical = 12.dp)
            ) {
                reviews.forEach { review ->
                    ReviewCard(review)
                }
            }
            OutlinedButton()
        }
    }

    @Composable
    fun ReviewsSectionBold(reviewsCount: Int, totalRating: Double) {
        Row(
            verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.End
        ) {
            DefaultText(
                modifier = Modifier.padding(horizontal = 24.dp),
                text = "Reviews ($reviewsCount)",
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold
            )
            Box(
                modifier = Modifier.weight(1f), contentAlignment = Alignment.CenterEnd
            ) {
                StarIconFull()
            }
            Spacer(modifier = Modifier.width(4.dp))
            DefaultText(
                modifier = Modifier.padding(end = 24.dp),
                text = totalRating.toString(),
                fontSize = 18.sp
            )
        }
    }

    @Composable
    fun ReviewCard(review: Review) {
        Column {
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
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
                    DefaultText(text = review.userEmail)
                    Spacer(modifier = Modifier.height(4.dp))
                    Row {
                        repeat(5) {
                            if (it < review.rating.toInt())
                                StarIconFull(modifier = Modifier
                                    .padding(start = if (it == 0) 2.dp else 0.dp, end = 2.dp))
                            else
                                StarIconOutlined(modifier = Modifier
                                    .padding(start = if (it == 0) 2.dp else 0.dp, end = 2.dp))
                        }
                    }
                }
                review.addedAgo?.let {
                    DefaultText(
                        modifier = Modifier
                            .align(Alignment.Top)
                            .padding(top = 4.dp, end = 24.dp),
                        text = it
                    )
                }

            }
            DefaultText(
                modifier = Modifier.padding(
                    start = 84.dp, end = 24.dp, top = 8.dp, bottom = 12.dp
                ), text = "Some text"
            )
        }

    }

    @Composable
    fun OutlinedButton() {
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
                modifier = Modifier.padding(vertical = 4.dp), text = "See All Reviews"
            )
        }
    }

    @Composable
    fun FeaturedProductsSection(products: List<Good>) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .background(colorResource(id = R.color.grey_light))
                .padding(vertical = 24.dp)
        ) {
            Spacer(modifier = Modifier.height(16.dp))
            FeaturedProductTopSection()
            Spacer(modifier = Modifier.height(16.dp))
            LazyRow(
                modifier = Modifier.padding(
                    start = 16.dp, top = 16.dp
                ), state = rememberLazyListState()
            ) {
                items(products.size) {
                    ProductCard(products[it])
                }
            }
            AddToCartButton()
        }
    }

    @Composable
    fun FeaturedProductTopSection() {
        Row(
            verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.End
        ) {
            DefaultText(
                modifier = Modifier.padding(horizontal = 24.dp),
                text = "Featured Products",
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold
            )
            Box(
                modifier = Modifier.weight(1f), contentAlignment = Alignment.CenterEnd
            ) {
                DefaultText(
                    modifier = Modifier.padding(end = 24.dp),
                    text = "See All",
                    color = colorResource(id = R.color.blue_ocean)
                )
            }

        }
    }

    @Composable
    fun AddToCartButton() {
        Button(modifier = Modifier
            .padding(
                start = 24.dp, end = 24.dp, top = 24.dp
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

    @Composable
    fun ProductCard(product: Good) {
        Card(
            shape = RoundedCornerShape(16.dp),
            colors = CardDefaults.cardColors(containerColor = Color.White),
            modifier = Modifier
                .padding(horizontal = 8.dp)
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

        private const val ARG_PRODUCT_ID = "productId"

        fun newInstance(productId: Int): ProductDetailsFragment {
            val fragment = ProductDetailsFragment()
            fragment.arguments = bundleOf(ARG_PRODUCT_ID to productId)
            return fragment
        }

    }

}
