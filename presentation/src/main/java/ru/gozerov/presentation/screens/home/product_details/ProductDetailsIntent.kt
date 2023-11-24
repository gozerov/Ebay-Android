package ru.gozerov.presentation.screens.home.product_details

import ru.gozerov.presentation.utils.Intent

sealed class ProductDetailsIntent : Intent {

    data class LoadProduct(
        val id: Int
    ) : ProductDetailsIntent()

}
