package ru.gozerov.data.remote.login.models

data class ConfirmPasswordRequestBody(
    val token: String,
    val password: String,
    val confirmedPassword: String
)
