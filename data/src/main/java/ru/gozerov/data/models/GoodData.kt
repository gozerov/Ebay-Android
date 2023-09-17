package ru.gozerov.data.models

import ru.gozerov.data.remote.goods.models.AddGoodRequestBody
import ru.gozerov.domain.models.Good
import ru.gozerov.domain.models.Review

data class GoodData(
    val vendorCode: Int,
    val name: String,
    val description: String,
    val price: Int,
    val images: List<String>?,
    val reviews: List<ReviewData>? = null,
    val rating: Double? = null
)

fun GoodData.toGood() : Good = Good(
    vendorCode = vendorCode,
    name = name,
    description = description,
    price = price,
    images = images,
    reviews = reviews?.map { it.toReview() },
    rating = rating
)

fun GoodData.toAddRequestBody() : AddGoodRequestBody = AddGoodRequestBody(
    name = name,
    description = description,
    price = price,
    images = images
)

fun ReviewData.toReview() = Review(id, userEmail, goodId, rating, addedAgo, body)

fun Review.toReviewData() = ReviewData(id, userEmail, goodId, rating, addedAgo, body)

fun Good.toGoodData() : GoodData = GoodData(
    vendorCode = vendorCode,
    name = name,
    description = description,
    price = price,
    images = images,
    reviews = reviews?.map { it.toReviewData() },
    rating = rating
)