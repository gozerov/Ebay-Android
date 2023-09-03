package ru.gozerov.domain.usecases

import ru.gozerov.domain.models.AccountDataRequestBody
import ru.gozerov.domain.repositories.LoginRepository
import javax.inject.Inject

class SetAccountData @Inject constructor(
    private val loginRepository: LoginRepository
) : UseCase<AccountDataRequestBody, Unit>() {

    override suspend fun loadData(arg: AccountDataRequestBody) {
        loginRepository.setAccountData(arg)
    }

}