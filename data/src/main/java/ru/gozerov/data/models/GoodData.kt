package ru.gozerov.data.models

import ru.gozerov.data.remote.goods.models.AddGoodRequestBody
import ru.gozerov.domain.models.Good

data class GoodData(
    val vendorCode: Int,
    val name: String,
    val description: String,
    val price: Int,
    val images: List<String>
)

fun GoodData.toGood() : Good = Good(
    vendorCode = vendorCode,
    name = name,
    description = description,
    price = price,
    images = images
)

fun GoodData.toAddRequestBody() : AddGoodRequestBody = AddGoodRequestBody(
    name = name,
    description = description,
    price = price,
    images = images
)

fun Good.toGoodData() : GoodData = GoodData(
    vendorCode = vendorCode,
    name = name,
    description = description,
    price = price,
    images = images
)