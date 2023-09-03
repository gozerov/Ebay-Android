package ru.gozerov.domain.usecases

import ru.gozerov.domain.models.SignInBody
import ru.gozerov.domain.repositories.LoginRepository
import javax.inject.Inject

class PerformSignIn @Inject constructor(
    private val loginRepository: LoginRepository
): UseCase<SignInBody, Unit>() {

    override suspend fun loadData(arg: SignInBody) {
        loginRepository.signIn(arg)
    }

}