package ru.gozerov.data.repositories.login

import kotlinx.coroutines.withContext
import ru.gozerov.data.remote.login.LoginRemote
import ru.gozerov.data.remote.login.models.toSignInRequestBody
import ru.gozerov.data.remote.login.models.toSignUpRequestBody
import ru.gozerov.data.remote.login.models.toToken
import ru.gozerov.data.utils.dispatchers.Dispatcher
import ru.gozerov.domain.models.SignInBody
import ru.gozerov.domain.models.SignUpBody
import ru.gozerov.domain.models.Token
import ru.gozerov.domain.repositories.LoginRepository
import javax.inject.Inject

class LoginRepositoryImpl @Inject constructor(
    private val dispatcher: Dispatcher,
    private val loginRemote: LoginRemote
): LoginRepository {

    override suspend fun signUp(signUpBody: SignUpBody): Token = withContext(dispatcher.value) {
        return@withContext loginRemote.signUp(signUpBody.toSignUpRequestBody()).toToken()
    }

    override suspend fun signIn(signInBody: SignInBody): Token = withContext(dispatcher.value) {
        return@withContext loginRemote.signIn(signInBody.toSignInRequestBody()).toToken()
    }

}