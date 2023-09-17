package ru.gozerov.data.remote.goods.models

import ru.gozerov.data.models.CategoryData

data class GetCategoriesResponseBody(
    val value: List<CategoryData>
)
