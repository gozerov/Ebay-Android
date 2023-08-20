package ru.gozerov.domain.models

data class SignUpBody(
    val login: String,
    val email: String,
    val password: String
)
