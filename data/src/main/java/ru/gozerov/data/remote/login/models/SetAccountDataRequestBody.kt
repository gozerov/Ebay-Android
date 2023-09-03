package ru.gozerov.data.remote.login.models

data class SetAccountDataRequestBody(
    val token: String,
    val username: String,
    val password: String
)
