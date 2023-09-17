package ru.gozerov.data.models

import ru.gozerov.domain.models.Category

data class CategoryData(
    val id: Int,
    val name: String
)

fun CategoryData.toCategory() = Category(id, name)
