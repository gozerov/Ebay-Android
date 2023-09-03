package ru.gozerov.presentation.screens.login.enter_new_password

import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import ru.gozerov.domain.models.NewPassword
import ru.gozerov.domain.usecases.CancelVerification
import ru.gozerov.domain.usecases.EnterNewPassword
import ru.gozerov.presentation.utils.BaseViewModel
import javax.inject.Inject

class EnterNewPasswordViewModel<T, U> @Inject constructor(
    private val enterNewPassword: EnterNewPassword
) : BaseViewModel<EnterNewPasswordIntent, EnterNewPasswordViewState>() {

    var isConfirmInProgress = false
        private set

    override fun handleIntent(intent: EnterNewPasswordIntent) {
        viewModelScope.launch {
            when(intent) {
                is EnterNewPasswordIntent.StartScreen -> {
                    isConfirmInProgress = false
                    _viewState.emit(EnterNewPasswordViewState.Empty)
                }
                is EnterNewPasswordIntent.SubmitPasswords -> {
                    if (intent.password != intent.confirmedPassword)
                        _viewState.emit(EnterNewPasswordViewState.PasswordMismatch)
                    else {
                        isConfirmInProgress = true
                        enterNewPassword.execute(
                            arg = NewPassword(intent.password, intent.confirmedPassword),
                            onSuccess = { _viewState.emit(EnterNewPasswordViewState.Success) },
                            onHttpError = {
                                isConfirmInProgress = false
                                _viewState.emit(EnterNewPasswordViewState.UnknownError)
                            },
                            onError = {
                                isConfirmInProgress = false
                                _viewState.emit(EnterNewPasswordViewState.UnknownError)
                            }
                        )
                    }
                }
            }
        }

    }

}