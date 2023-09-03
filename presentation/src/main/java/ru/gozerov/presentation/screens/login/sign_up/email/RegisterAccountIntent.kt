package ru.gozerov.presentation.screens.login.sign_up.email

import ru.gozerov.presentation.utils.Intent

sealed class RegisterAccountIntent : Intent {

    object StartScreen : RegisterAccountIntent()
    
    data class PerformSignUp(
        val email: String
    ) : RegisterAccountIntent()

}