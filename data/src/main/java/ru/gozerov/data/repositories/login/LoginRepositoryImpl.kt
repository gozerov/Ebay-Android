package ru.gozerov.data.repositories.login

import kotlinx.coroutines.withContext
import ru.gozerov.data.cache.SharedPreferencesStorage
import ru.gozerov.data.remote.login.LoginRemote
import ru.gozerov.data.remote.login.models.CancelVerificationRequestBody
import ru.gozerov.data.remote.login.models.ConfirmPasswordRequestBody
import ru.gozerov.data.remote.login.models.ResetPasswordRequestBody
import ru.gozerov.data.remote.login.models.SetAccountDataRequestBody
import ru.gozerov.data.remote.login.models.toSignInRequestBody
import ru.gozerov.data.remote.login.models.toSignUpRequestBody
import ru.gozerov.data.remote.login.models.toToken
import ru.gozerov.data.remote.login.models.toVerificationResponseBody
import ru.gozerov.data.utils.dispatchers.Constants.API_LOGIN_TOKEN
import ru.gozerov.data.utils.dispatchers.Constants.API_VERIFICATION_TOKEN
import ru.gozerov.data.utils.dispatchers.Dispatcher
import ru.gozerov.domain.models.AccountDataRequestBody
import ru.gozerov.domain.models.NewPassword
import ru.gozerov.domain.models.SignInBody
import ru.gozerov.domain.models.SignUpBody
import ru.gozerov.domain.models.Token
import ru.gozerov.domain.models.VerificationRequestBody
import ru.gozerov.domain.models.VerificationResponseBody
import ru.gozerov.domain.repositories.LoginRepository
import javax.inject.Inject

class LoginRepositoryImpl @Inject constructor(
    private val dispatcher: Dispatcher,
    private val loginRemote: LoginRemote,
    private val sharedPreferencesStorage: SharedPreferencesStorage
): LoginRepository {

    override suspend fun signUp(signUpBody: SignUpBody): Unit = withContext(dispatcher.value) {
        val token = loginRemote.signUp(signUpBody.toSignUpRequestBody()).toToken()
        if (token.value.isNotEmpty())
            sharedPreferencesStorage.writeString(API_VERIFICATION_TOKEN, token.value)
    }

    override suspend fun setAccountData(arg: AccountDataRequestBody) = withContext(dispatcher.value) {
        val token = sharedPreferencesStorage.readString(API_VERIFICATION_TOKEN).toString()
        val requestBody = SetAccountDataRequestBody(
            token = token,
            username = arg.username,
            password = arg.password
        )
        val loginToken = loginRemote.setAccountData(requestBody)
        if (loginToken.value.isNotEmpty()) {
            sharedPreferencesStorage.writeString(API_LOGIN_TOKEN, loginToken.value)
        }
    }

    override suspend fun signIn(signInBody: SignInBody): Token = withContext(dispatcher.value) {
        return@withContext loginRemote.signIn(signInBody.toSignInRequestBody()).toToken().apply {
            if (this.value.isNotEmpty())
                sharedPreferencesStorage.writeString(API_LOGIN_TOKEN, this.value)
        }
    }

    override suspend fun resetPassword(email: String) = withContext(dispatcher.value) {
        val token = loginRemote.resetPasswordByEmail(ResetPasswordRequestBody(email)).value
        sharedPreferencesStorage.writeString(API_VERIFICATION_TOKEN, token)
    }

    override suspend fun submitVerificationCode(code: Int): VerificationResponseBody = withContext(dispatcher.value) {
        val token = sharedPreferencesStorage.readString(API_VERIFICATION_TOKEN).toString()
        val verificationCode = VerificationRequestBody(
            token = token,
            code = code
        )
        return@withContext loginRemote.submitVerificationCode(verificationCode).toVerificationResponseBody()
    }

    override suspend fun cancelVerification() = withContext(dispatcher.value) {
        val token = sharedPreferencesStorage.readString(API_VERIFICATION_TOKEN).toString()
        loginRemote.cancelVerification(CancelVerificationRequestBody(token))
    }

    override suspend fun updatePassword(arg: NewPassword) = withContext(dispatcher.value) {
        val token = sharedPreferencesStorage.readString(API_VERIFICATION_TOKEN).toString()
        loginRemote.updatePassword(ConfirmPasswordRequestBody(token, arg.password, arg.confirmedPassword))
    }

}