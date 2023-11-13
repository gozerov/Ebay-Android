package ru.gozerov.presentation.screens.home.product_details

sealed class ProductDetailsIntent {

    data class LoadProduct(
        val id: Int
    ) : ProductDetailsIntent()

}
