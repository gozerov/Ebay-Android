package ru.gozerov.presentation.screens.login.enter_new_password

import ru.gozerov.presentation.utils.Intent

sealed class EnterNewPasswordIntent : Intent {

    object StartScreen : EnterNewPasswordIntent()

    data class SubmitPasswords(
        val password: String,
        val confirmedPassword: String
    ) : EnterNewPasswordIntent()

}