package ru.gozerov.presentation.screens.login.reset

import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import ru.gozerov.domain.usecases.ResetPasswordByEmail
import ru.gozerov.presentation.screens.login.verification.VerificationCodeViewState
import ru.gozerov.presentation.utils.BaseViewModel
import ru.gozerov.presentation.utils.HttpErrors
import javax.inject.Inject

class ResetPasswordViewModel<T, U> @Inject constructor(
    private val resetPasswordByEmail: ResetPasswordByEmail
) : BaseViewModel<ResetPasswordIntent, ResetPasswordViewState>() {

    override fun handleIntent(intent: ResetPasswordIntent) {
        viewModelScope.launch {
            when(intent) {
                is ResetPasswordIntent.StartScreen ->
                    _viewState.emit(ResetPasswordViewState.Empty)
                is ResetPasswordIntent.ResetPasswordByEmail -> {
                    resetPasswordByEmail.execute(
                        arg = intent.email,
                        onSuccess = {
                            _viewState.emit(ResetPasswordViewState.Success)
                        },
                        onHttpError = {
                            when(it) {
                                HttpErrors.USER_NOT_FOUND -> _viewState.emit(ResetPasswordViewState.InvalidEmail)
                                HttpErrors.NOT_ENOUGH_TIME -> _viewState.emit(ResetPasswordViewState.VerificationIsUnavailable)
                                else -> _viewState.emit(ResetPasswordViewState.UnknownError)
                            }
                        },
                        onError = {
                            _viewState.emit(ResetPasswordViewState.UnknownError)
                        }
                    )
                }
            }
        }
    }

}