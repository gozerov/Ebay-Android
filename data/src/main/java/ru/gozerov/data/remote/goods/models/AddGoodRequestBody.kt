package ru.gozerov.data.remote.goods.models

data class AddGoodRequestBody(
    val name: String,
    val description: String,
    val price: Int,
    val images: List<String>?
)