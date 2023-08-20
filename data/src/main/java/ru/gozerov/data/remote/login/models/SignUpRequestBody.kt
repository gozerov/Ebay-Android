package ru.gozerov.data.remote.login.models

import ru.gozerov.domain.models.SignUpBody

data class SignUpRequestBody(
    val login: String,
    val email: String,
    val password: String
)

fun SignUpBody.toSignUpRequestBody() : SignUpRequestBody = SignUpRequestBody(
    login = login,
    email = email,
    password = password
)
