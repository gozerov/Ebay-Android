package ru.gozerov.domain.usecases

import ru.gozerov.domain.repositories.LoginRepository
import javax.inject.Inject

class ResetPasswordByEmail @Inject constructor(
    private val loginRepository: LoginRepository
) : UseCase<String, Unit>() {

    override suspend fun loadData(arg: String) {
        loginRepository.resetPassword(arg)
    }

}