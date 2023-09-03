package ru.gozerov.presentation.screens.login.sign_in

import ru.gozerov.presentation.utils.Intent

sealed class SignInIntent : Intent {

    object StartScreen : SignInIntent()

    data class TryLogin(
        val email: String,
        val password: String
    ) : SignInIntent()

}