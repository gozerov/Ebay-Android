package ru.gozerov.data.remote.login.models

data class ResetPasswordResponseBody(
    val token: String
)

fun ResetPasswordResponseBody.toDataToken() = DataToken(value = token)