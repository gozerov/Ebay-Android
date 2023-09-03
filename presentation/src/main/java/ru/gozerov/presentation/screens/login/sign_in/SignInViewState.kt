package ru.gozerov.presentation.screens.login.sign_in

import ru.gozerov.presentation.utils.ViewState

sealed class SignInViewState : ViewState {

    object Empty : SignInViewState()
    object UserNotFound : SignInViewState()
    object WrongPassword : SignInViewState()
    object UnknownError : SignInViewState()

    object SuccessLogin : SignInViewState()


}
