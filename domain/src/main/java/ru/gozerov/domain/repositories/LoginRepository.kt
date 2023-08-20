package ru.gozerov.domain.repositories

import ru.gozerov.domain.models.SignUpBody
import ru.gozerov.domain.models.SignInBody
import ru.gozerov.domain.models.Token

interface LoginRepository {

    suspend fun signUp(signUpBody: SignUpBody) : Token

    suspend fun signIn(signInBody: SignInBody) : Token

}