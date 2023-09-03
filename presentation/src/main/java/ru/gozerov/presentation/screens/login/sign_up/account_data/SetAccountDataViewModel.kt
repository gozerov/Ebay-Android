package ru.gozerov.presentation.screens.login.sign_up.account_data

import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import ru.gozerov.domain.models.AccountDataRequestBody
import ru.gozerov.domain.usecases.CancelVerification
import ru.gozerov.domain.usecases.SetAccountData
import ru.gozerov.presentation.utils.BaseViewModel
import javax.inject.Inject

class SetAccountDataViewModel<T, U> @Inject constructor(
    private val setAccountData: SetAccountData
): BaseViewModel<SetAccountDataIntent, SetAccountDataViewState>() {

    var isSettingInProgress = false
        private set

    override fun handleIntent(intent: SetAccountDataIntent) {
        viewModelScope.launch {
            when(intent) {
                is SetAccountDataIntent.StartScreen ->{
                    isSettingInProgress = false
                    _viewState.emit(SetAccountDataViewState.Empty)
                }
                is SetAccountDataIntent.SetAccountData -> {
                    isSettingInProgress = true
                    setAccountData.execute(
                        arg = AccountDataRequestBody(intent.username, intent.password),
                        onSuccess = {
                            _viewState.emit(SetAccountDataViewState.Success)
                        },
                        onHttpError = {
                            isSettingInProgress = false
                            _viewState.emit(SetAccountDataViewState.UnknownError)
                        },
                        onError = {
                            isSettingInProgress = false
                            _viewState.emit(SetAccountDataViewState.UnknownError)
                        }
                    )
                }
            }
        }
    }

}