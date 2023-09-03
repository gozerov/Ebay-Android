package ru.gozerov.domain.usecases

import ru.gozerov.domain.repositories.LoginRepository
import javax.inject.Inject

class CancelVerification @Inject constructor(
    private val loginRepository: LoginRepository
) : UseCase<Unit, Unit>() {

    override suspend fun loadData(arg: Unit) {
        loginRepository.cancelVerification()
    }

}