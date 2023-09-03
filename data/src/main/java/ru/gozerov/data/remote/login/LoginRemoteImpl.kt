package ru.gozerov.data.remote.login

import ru.gozerov.data.remote.login.models.ConfirmPasswordRequestBody
import ru.gozerov.data.remote.login.models.DataToken
import ru.gozerov.data.remote.login.models.ResetPasswordRequestBody
import ru.gozerov.data.remote.login.models.SetAccountDataRequestBody
import ru.gozerov.data.remote.login.models.SignInRequestBody
import ru.gozerov.data.remote.login.models.SignUpRequestBody
import ru.gozerov.data.remote.login.models.VerificationCodeResponse
import ru.gozerov.data.remote.login.models.toDataToken
import ru.gozerov.data.remote.login.retrofit.LoginApi
import ru.gozerov.domain.models.NewPassword
import ru.gozerov.domain.models.VerificationRequestBody
import javax.inject.Inject

class LoginRemoteImpl @Inject constructor(
    private val loginApi: LoginApi
) : LoginRemote {

    override suspend fun signUp(signUpRequestBody: SignUpRequestBody): DataToken {
        return DataToken(loginApi.signUp(signUpRequestBody).token)
    }

    override suspend fun setAccountData(setAccountDataRequestBody: SetAccountDataRequestBody): DataToken {
        return DataToken(loginApi.setAccountData(setAccountDataRequestBody).token)
    }

    override suspend fun signIn(signInRequestBody: SignInRequestBody): DataToken {
        return DataToken(loginApi.signIn(signInRequestBody).token)
    }

    override suspend fun resetPasswordByEmail(resetPasswordRequestBody: ResetPasswordRequestBody): DataToken {
        return loginApi.resetPasswordByEmail(resetPasswordRequestBody).toDataToken()
    }

    override suspend fun submitVerificationCode(verificationRequestBody: VerificationRequestBody): VerificationCodeResponse {
        return loginApi.verification(verificationRequestBody)
    }

    override suspend fun updatePassword(confirmPasswordRequestBody: ConfirmPasswordRequestBody) {
        loginApi.confirmPassword(confirmPasswordRequestBody)
    }

}