package ru.gozerov.presentation.screens.login.sign_up.account_data

import ru.gozerov.presentation.utils.ViewState

sealed class SetAccountDataViewState : ViewState {

    object Empty : SetAccountDataViewState()

    object Success : SetAccountDataViewState()

    object UnknownError : SetAccountDataViewState()

}
