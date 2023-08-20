package ru.gozerov.data.remote.login

import com.squareup.moshi.Moshi
import kotlinx.coroutines.runBlocking
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.Body
import retrofit2.http.POST
import ru.gozerov.data.remote.login.models.LoginResponseBody
import ru.gozerov.data.remote.login.models.SignInRequestBody
import ru.gozerov.data.remote.login.models.SignUpRequestBody

interface LoginApi {

    @POST("/register")
    suspend fun signUp(@Body signUpRequestBody: SignUpRequestBody) : LoginResponseBody

    @POST("/login")
    suspend fun signIn(@Body signInRequestBody: SignInRequestBody) : LoginResponseBody

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
        println(loginApi.signUp(SignUpRequestBody("admin2", "@email", "111")).token)
    }
}