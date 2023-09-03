package ru.gozerov.presentation.screens.login.verification

import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import ru.gozerov.domain.usecases.PerformVerification
import ru.gozerov.presentation.utils.BaseViewModel
import ru.gozerov.presentation.utils.HttpErrors
import javax.inject.Inject

class VerificationCodeViewModel<T, U> @Inject constructor(
    private val performVerification: PerformVerification
): BaseViewModel<VerificationCodeIntent, VerificationCodeViewState>() {

    var isConfirmInProgress: Boolean = false
        private set

    override fun handleIntent(intent: VerificationCodeIntent) {
        viewModelScope.launch {
            when(intent) {
                is VerificationCodeIntent.StartScreen -> {
                    isConfirmInProgress = false
                    _viewState.emit(VerificationCodeViewState.Empty)
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

}