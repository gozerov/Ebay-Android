package ru.gozerov.presentation.screens.home.product_details

import ru.gozerov.domain.models.Good

sealed class ProductDetailsViewState {

    object Empty : ProductDetailsViewState()

    data class SuccessLoading(
        val good: Good
    ) : ProductDetailsViewState()

    object Error : ProductDetailsViewState()

    data class FeaturedProductsLoaded(
        val products: List<Good>
    ): ProductDetailsViewState()

}