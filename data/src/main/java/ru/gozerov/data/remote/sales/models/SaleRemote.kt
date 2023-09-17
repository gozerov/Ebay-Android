package ru.gozerov.data.remote.sales.models

import ru.gozerov.domain.models.Sale

data class SaleRemote(
    val id: Int,
    val title: String,
    val description: String,
    val url: String,
    val image: String
)

fun SaleRemote.toSale() = Sale(id, title, description, url, image)