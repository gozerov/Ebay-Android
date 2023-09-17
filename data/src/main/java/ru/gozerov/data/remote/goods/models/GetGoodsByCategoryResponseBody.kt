package ru.gozerov.data.remote.goods.models

import ru.gozerov.data.models.CategoryData
import ru.gozerov.data.models.GoodData

data class GetGoodsByCategoryResponseBody(
    val category: CategoryData,
    val goods: List<GoodData>
)
