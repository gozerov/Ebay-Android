package ru.gozerov.domain.models

data class NewPassword(
    val password: String,
    val confirmedPassword: String
)
