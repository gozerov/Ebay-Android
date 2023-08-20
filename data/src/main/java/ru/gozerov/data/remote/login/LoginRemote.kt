package ru.gozerov.data.remote.login

import ru.gozerov.data.remote.login.models.DataToken
import ru.gozerov.data.remote.login.models.SignInRequestBody
import ru.gozerov.data.remote.login.models.SignUpRequestBody

interface LoginRemote {

    suspend fun signUp(signUpRequestBody: SignUpRequestBody) : DataToken

    suspend fun signIn(signInRequestBody: SignInRequestBody) : DataToken

}