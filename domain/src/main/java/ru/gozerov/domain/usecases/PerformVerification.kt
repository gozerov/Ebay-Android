package ru.gozerov.domain.usecases

import ru.gozerov.domain.repositories.LoginRepository
import javax.inject.Inject

class PerformVerification @Inject constructor(
    private val loginRepository: LoginRepository
): UseCase<Int, Unit>() {

    override suspend fun loadData(arg: Int) {
        loginRepository.submitVerificationCode(arg)
    }

}