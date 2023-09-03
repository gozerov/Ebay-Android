package ru.gozerov.presentation.screens.login.verification

import ru.gozerov.presentation.utils.Intent

sealed class VerificationCodeIntent : Intent {

    object StartScreen : VerificationCodeIntent()

    data class ConfirmVerificationCode(
        val code: Int
    ) : VerificationCodeIntent()

}
