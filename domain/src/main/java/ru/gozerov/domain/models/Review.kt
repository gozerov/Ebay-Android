package ru.gozerov.domain.models

data class Review(
    val id: Int,
    val userEmail: String,
    val goodId: Int,
    val rating: Double,
    val addedAgo: String?,
    val body: String?
)
