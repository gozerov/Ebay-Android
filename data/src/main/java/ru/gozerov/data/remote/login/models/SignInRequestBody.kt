package ru.gozerov.data.remote.login.models

import ru.gozerov.domain.models.SignInBody

data class SignInRequestBody(
    val login: String,
    val password: String
)


fun SignInBody.toSignInRequestBody() : SignInRequestBody = SignInRequestBody(
    login = login,
    password = password
)


