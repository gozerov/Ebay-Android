package ru.gozerov.domain.repositories

import ru.gozerov.domain.models.AccountDataRequestBody
import ru.gozerov.domain.models.NewPassword
import ru.gozerov.domain.models.SignUpBody
import ru.gozerov.domain.models.SignInBody
import ru.gozerov.domain.models.Token
import ru.gozerov.domain.models.VerificationRequestBody
import ru.gozerov.domain.models.VerificationResponseBody

interface LoginRepository {

    suspend fun signUp(signUpBody: SignUpBody)

    suspend fun setAccountData(arg: AccountDataRequestBody)

    suspend fun signIn(signInBody: SignInBody) : Token

    suspend fun resetPassword(email: String)

    suspend fun submitVerificationCode(code: Int) : VerificationResponseBody

    suspend fun cancelVerification()

    suspend fun updatePassword(arg: NewPassword)

}