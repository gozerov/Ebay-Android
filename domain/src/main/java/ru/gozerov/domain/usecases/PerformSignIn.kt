package ru.gozerov.domain.usecases

import ru.gozerov.domain.models.SignInBody
import ru.gozerov.domain.models.Token
import ru.gozerov.domain.repositories.LoginRepository
import javax.inject.Inject

class PerformSignIn @Inject constructor(
    private val loginRepository: LoginRepository
): UseCase<SignInBody, Token>() {

    override suspend fun loadData(arg: SignInBody): Token {
        return loginRepository.signIn(arg)
    }

}