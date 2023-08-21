package ru.gozerov.data.remote.login

import ru.gozerov.data.remote.login.models.DataToken
import ru.gozerov.data.remote.login.models.SignInRequestBody
import ru.gozerov.data.remote.login.models.SignUpRequestBody
import ru.gozerov.data.remote.login.retrofit.LoginApi
import javax.inject.Inject

class LoginRemoteImpl @Inject constructor(
    private val loginApi: LoginApi
) : LoginRemote {

    override suspend fun signUp(signUpRequestBody: SignUpRequestBody): DataToken {
        return DataToken(loginApi.signUp(signUpRequestBody).token)
    }

    override suspend fun signIn(signInRequestBody: SignInRequestBody): DataToken {
        return DataToken(loginApi.signIn(signInRequestBody).token)
    }
}