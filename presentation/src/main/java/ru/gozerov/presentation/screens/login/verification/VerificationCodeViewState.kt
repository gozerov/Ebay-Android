package ru.gozerov.presentation.screens.login.verification

import ru.gozerov.presentation.utils.ViewState

sealed class VerificationCodeViewState : ViewState {

    object Empty : VerificationCodeViewState()

    object Success : VerificationCodeViewState()

    object NotValidCode : VerificationCodeViewState()

    object UnknownError : VerificationCodeViewState()

    object ResendUnavailable : VerificationCodeViewState()

    data class Timer(
        val time: String
    ) : VerificationCodeViewState()

    object TimerEnd: VerificationCodeViewState()

}