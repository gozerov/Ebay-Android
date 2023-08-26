package ru.gozerov.domain.usecases

import ru.gozerov.domain.models.SignUpBody
import ru.gozerov.domain.models.Token
import ru.gozerov.domain.repositories.LoginRepository
import javax.inject.Inject

class PerformSignUp @Inject constructor(
    private val loginRepository: LoginRepository
) : UseCase<SignUpBody, Token>() {

    override suspend fun loadData(arg: SignUpBody): Token {
       return loginRepository.signUp(arg)
    }

}