package ru.gozerov.presentation.screens.login.sign_up.account_data

import ru.gozerov.presentation.utils.Intent

sealed class SetAccountDataIntent : Intent {

    object StartScreen : SetAccountDataIntent()

    data class SetAccountData(
        val username: String,
        val password: String
    ) : SetAccountDataIntent()

}
