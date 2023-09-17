package ru.gozerov.data.models

data class ReviewData(
    val id: Int,
    val userEmail: String,
    val goodId: Int,
    val rating: Double,
    val addedAgo: String?,
    val body: String?
)
