package ru.gozerov.data.remote.login.models

import ru.gozerov.domain.models.SignUpBody

data class SignUpRequestBody(
    val email: String
)

fun SignUpBody.toSignUpRequestBody() : SignUpRequestBody = SignUpRequestBody(
    email = email
)
