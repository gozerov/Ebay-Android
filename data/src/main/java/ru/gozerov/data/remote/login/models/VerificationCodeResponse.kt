package ru.gozerov.data.remote.login.models

import ru.gozerov.domain.models.VerificationResponseBody

data class VerificationCodeResponse(
    val token: String
)

fun VerificationCodeResponse.toVerificationResponseBody(): VerificationResponseBody = VerificationResponseBody(
    token = token
)