package ru.gozerov.domain.models

data class Good(
    val vendorCode: Int,
    val name: String,
    val description: String,
    val price: Int,
    val images: List<String>
)
