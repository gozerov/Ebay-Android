package ru.gozerov.data.remote.login

import ru.gozerov.data.remote.login.models.ConfirmPasswordRequestBody
import ru.gozerov.data.remote.login.models.DataToken
import ru.gozerov.data.remote.login.models.ResetPasswordRequestBody
import ru.gozerov.data.remote.login.models.SetAccountDataRequestBody
import ru.gozerov.data.remote.login.models.SignInRequestBody
import ru.gozerov.data.remote.login.models.SignUpRequestBody
import ru.gozerov.data.remote.login.models.VerificationCodeResponse
import ru.gozerov.domain.models.NewPassword
import ru.gozerov.domain.models.VerificationRequestBody

interface LoginRemote {

    suspend fun signUp(signUpRequestBody: SignUpRequestBody) : DataToken

    suspend fun setAccountData(setAccountDataRequestBody: SetAccountDataRequestBody) : DataToken

    suspend fun signIn(signInRequestBody: SignInRequestBody) : DataToken

    suspend fun resetPasswordByEmail(resetPasswordRequestBody: ResetPasswordRequestBody) : DataToken

    suspend fun submitVerificationCode(verificationRequestBody: VerificationRequestBody) : VerificationCodeResponse

    suspend fun updatePassword(confirmPasswordRequestBody: ConfirmPasswordRequestBody)


}