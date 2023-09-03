package ru.gozerov.presentation.screens.login.enter_new_password

import ru.gozerov.presentation.utils.ViewState

sealed class EnterNewPasswordViewState : ViewState {

    object Empty : EnterNewPasswordViewState()

    object Success : EnterNewPasswordViewState()

    object PasswordMismatch : EnterNewPasswordViewState()

    object UnknownError : EnterNewPasswordViewState()

}