package ru.gozerov.data.remote.login.models

import ru.gozerov.domain.models.Token

data class DataToken(
    val value: String
)

fun DataToken.toToken() : Token = Token(value = value)
