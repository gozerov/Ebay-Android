package ru.gozerov.presentation.screens.login.reset

import ru.gozerov.presentation.utils.Intent

sealed class ResetPasswordIntent : Intent {

    object StartScreen : ResetPasswordIntent()

    data class ResetPasswordByEmail(
        val email: String
    ) : ResetPasswordIntent()

}