package ru.gozerov.domain.models

data class VerificationRequestBody(
    val token: String,
    val code: Int
)
