package ru.gozerov.data.remote.login.models

import ru.gozerov.domain.models.SignInBody

data class SignInRequestBody(
    val email: String,
    val password: String
)


fun SignInBody.toSignInRequestBody() : SignInRequestBody = SignInRequestBody(
    email = email,
    password = password
)


