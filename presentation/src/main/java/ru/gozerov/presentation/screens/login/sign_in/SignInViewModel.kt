package ru.gozerov.presentation.screens.login.sign_in

import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import ru.gozerov.domain.models.SignInBody
import ru.gozerov.domain.usecases.PerformSignIn
import ru.gozerov.presentation.utils.BaseViewModel
import ru.gozerov.presentation.utils.HttpErrors
import javax.inject.Inject

class SignInViewModel<T, U> @Inject constructor(
    private val performSignIn: PerformSignIn
): BaseViewModel<SignInIntent, SignInViewState>() {

    var isLoginInProgress = false
        private set

    override fun handleIntent(intent: SignInIntent) {
        viewModelScope.launch {
            when(intent) {
                is SignInIntent.StartScreen -> {
                    _viewState.emit(SignInViewState.Empty)
                }
                is SignInIntent.TryLogin -> {
                    isLoginInProgress = true
                    performSignIn.execute(
                        SignInBody(intent.email, intent.password),
                        onSuccess = {
                            isLoginInProgress = false
                            _viewState.emit(SignInViewState.SuccessLogin)
                        },
                        onHttpError = {
                            isLoginInProgress = false
                            when (it) {
                                HttpErrors.USER_NOT_FOUND -> _viewState.emit(SignInViewState.UserNotFound)
                                HttpErrors.WRONG_PASSWORD -> _viewState.emit(SignInViewState.WrongPassword)
                                else -> _viewState.emit(SignInViewState.UnknownError)
                            }
                        },
                        onError = {
                            isLoginInProgress = false
                            _viewState.emit(SignInViewState.UnknownError)
                        }
                    )
                }
            }
        }

    }

}