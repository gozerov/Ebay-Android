package ru.gozerov.presentation.screens.login.reset

import ru.gozerov.presentation.utils.ViewState

sealed class ResetPasswordViewState : ViewState {

    object Empty : ResetPasswordViewState()

    object Success : ResetPasswordViewState()

    object InvalidEmail : ResetPasswordViewState()

    object UnknownError : ResetPasswordViewState()

    object VerificationIsUnavailable : ResetPasswordViewState()

}
