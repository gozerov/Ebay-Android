package ru.gozerov.data.remote.login.retrofit

import com.squareup.moshi.Moshi
import kotlinx.coroutines.runBlocking
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.HttpException
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.Body
import retrofit2.http.Header
import retrofit2.http.POST
import ru.gozerov.data.remote.login.models.CancelVerificationRequestBody
import ru.gozerov.data.remote.login.models.ConfirmPasswordRequestBody
import ru.gozerov.data.remote.login.models.DataToken
import ru.gozerov.data.remote.login.models.LoginResponseBody
import ru.gozerov.data.remote.login.models.ResetPasswordRequestBody
import ru.gozerov.data.remote.login.models.ResetPasswordResponseBody
import ru.gozerov.data.remote.login.models.SetAccountDataRequestBody
import ru.gozerov.data.remote.login.models.SetAccountDataResponseBody
import ru.gozerov.data.remote.login.models.SignInRequestBody
import ru.gozerov.data.remote.login.models.SignUpRequestBody
import ru.gozerov.data.remote.login.models.VerificationCodeResponse
import ru.gozerov.domain.models.NewPassword
import ru.gozerov.domain.models.VerificationRequestBody
import kotlin.math.log

interface LoginApi {

    @POST("/register")
    suspend fun signUp(@Body signUpRequestBody: SignUpRequestBody) : LoginResponseBody

    @POST("/setaccountdata")
    suspend fun setAccountData(@Body setAccountDataRequestBody: SetAccountDataRequestBody) : SetAccountDataResponseBody

    @POST("/login")
    suspend fun signIn(@Body signInRequestBody: SignInRequestBody) : LoginResponseBody

    @POST("/reset")
    suspend fun resetPasswordByEmail(@Body resetPasswordRequestBody: ResetPasswordRequestBody) : ResetPasswordResponseBody

    @POST("/verification")
    suspend fun verification(@Body verificationRequestBody: VerificationRequestBody) : VerificationCodeResponse

    @POST("/verification/cancel")
    suspend fun cancelVerification(@Body cancelVerificationRequestBody: CancelVerificationRequestBody)

    @POST("/password")
    suspend fun confirmPassword(@Body confirmPasswordRequestBody: ConfirmPasswordRequestBody)

}

fun main() {
    val loggingInterceptor = HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY)
    val client = OkHttpClient.Builder()
        .addInterceptor(loggingInterceptor)
        .build()

    val moshi = Moshi.Builder().build()
    val moshiConverterFactory = MoshiConverterFactory.create(moshi)

    val retrofit = Retrofit.Builder()
        .baseUrl("http://0.0.0.0:8080")
        .client(client)
        .addConverterFactory(moshiConverterFactory)
        .build()
    val loginApi = retrofit.create(LoginApi::class.java)
    runBlocking {
        try {
            println(loginApi.signIn(SignInRequestBody("1", "1")))
        } catch (e: HttpException) {
            println(e.response()?.errorBody()?.string())
        }
    }
}