package ru.gozerov.domain.usecases

import ru.gozerov.domain.models.NewPassword
import ru.gozerov.domain.repositories.LoginRepository
import javax.inject.Inject

class EnterNewPassword @Inject constructor(
    private val loginRepository: LoginRepository
): UseCase<NewPassword, Unit>() {

    override suspend fun loadData(arg: NewPassword) {
        loginRepository.updatePassword(arg)
    }

}