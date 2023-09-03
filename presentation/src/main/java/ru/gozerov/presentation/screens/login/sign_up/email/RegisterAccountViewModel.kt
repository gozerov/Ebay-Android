package ru.gozerov.presentation.screens.login.sign_up.email

import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import ru.gozerov.domain.usecases.PerformSignUp
import ru.gozerov.presentation.utils.BaseViewModel
import ru.gozerov.presentation.utils.HttpErrors
import javax.inject.Inject

class RegisterAccountViewModel<T, U> @Inject constructor(
    private val performSignUp: PerformSignUp
): BaseViewModel<RegisterAccountIntent, RegisterAccountViewState>() {

    var isRegisterInProgress = false
        private set

    override fun handleIntent(intent: RegisterAccountIntent) {
        viewModelScope.launch {
            when(intent) {
                is RegisterAccountIntent.StartScreen -> {
                    isRegisterInProgress = false
                    _viewState.emit(RegisterAccountViewState.Empty)
                }
                is RegisterAccountIntent.PerformSignUp -> {
                    isRegisterInProgress = true
                    performSignUp.execute(
                        arg = intent.email,
                        onSuccess = {
                            _viewState.emit(RegisterAccountViewState.Success)
                        },
                        onHttpError = {
                            isRegisterInProgress = false
                            when(it) {
                                HttpErrors.ACCOUNT_ALREADY_EXIST -> _viewState.emit(RegisterAccountViewState.AccountAlreadyExist)
                                else -> _viewState.emit(RegisterAccountViewState.UnknownError)
                            }
                        },
                        onError = {
                            isRegisterInProgress = false
                            _viewState.emit(RegisterAccountViewState.UnknownError)
                        }
                    )
                }
            }
        }
    }

}