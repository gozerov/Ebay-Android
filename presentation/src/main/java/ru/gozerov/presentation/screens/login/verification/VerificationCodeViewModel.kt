package ru.gozerov.presentation.screens.login.verification

import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import ru.gozerov.domain.usecases.CancelVerification
import ru.gozerov.domain.usecases.PerformVerification
import ru.gozerov.domain.usecases.ResetPasswordByEmail
import ru.gozerov.presentation.utils.BaseViewModel
import ru.gozerov.presentation.utils.HttpErrors
import javax.inject.Inject

class VerificationCodeViewModel<T, U> @Inject constructor(
    private val performVerification: PerformVerification,
    private val resetPasswordByEmail: ResetPasswordByEmail
): BaseViewModel<VerificationCodeIntent, VerificationCodeViewState>() {

    var isConfirmInProgress: Boolean = false
        private set

    override fun handleIntent(intent: VerificationCodeIntent) {
        viewModelScope.launch {
            when(intent) {
                is VerificationCodeIntent.StartScreen -> {
                    isConfirmInProgress = false
                    startTimer()
                    _viewState.emit(VerificationCodeViewState.Empty)
                }
                is VerificationCodeIntent.ResendCode -> {
                    resetPasswordByEmail.execute(
                        arg = intent.email,
                        onSuccess = {
                            startTimer()
                        },
                        onHttpError = {
                            when(it) {
                                HttpErrors.NOT_ENOUGH_TIME -> _viewState.emit(VerificationCodeViewState.ResendUnavailable)
                                else -> _viewState.emit(VerificationCodeViewState.UnknownError)
                            }
                        },
                        onError = {
                            _viewState.emit(VerificationCodeViewState.UnknownError)
                        }
                    )
                }
                is VerificationCodeIntent.ConfirmVerificationCode -> {
                    isConfirmInProgress = true
                    performVerification.execute(
                        arg = intent.code,
                        onSuccess = {
                            _viewState.emit(VerificationCodeViewState.Success)
                        },
                        onHttpError = {
                            isConfirmInProgress = false
                            when (it) {
                                HttpErrors.CODE_IS_NOT_VALID -> _viewState.emit(
                                    VerificationCodeViewState.NotValidCode
                                )
                                HttpErrors.NOT_ENOUGH_TIME -> _viewState.emit(VerificationCodeViewState.ResendUnavailable)

                                else -> _viewState.emit(VerificationCodeViewState.UnknownError)
                            }
                        },
                        onError = {
                            isConfirmInProgress = false
                            _viewState.emit(VerificationCodeViewState.UnknownError)
                        }
                    )
                }
            }

        }
    }
    private fun startTimer() {
        viewModelScope.launch {
            for (i in 60 downTo 0) {
                val t = if (i in 0..9) "0$i" else i.toString()
                _viewState.emit(VerificationCodeViewState.Timer(t))
                delay(1000)
                if (i == 0)
                    _viewState.emit(VerificationCodeViewState.TimerEnd)
            }
        }
    }

}