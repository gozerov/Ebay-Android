package ru.gozerov.presentation.screens.login.sign_up.email

import ru.gozerov.presentation.utils.ViewState

sealed class RegisterAccountViewState : ViewState {

    object Empty : RegisterAccountViewState()

    object Success : RegisterAccountViewState()

    object AccountAlreadyExist : RegisterAccountViewState()

    object UnknownError : RegisterAccountViewState()

}