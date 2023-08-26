package ru.gozerov.ebayandroid.di

import com.squareup.moshi.Moshi
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import ru.gozerov.data.remote.goods.retrofit.GoodsApi
import ru.gozerov.data.remote.login.retrofit.LoginApi
import ru.gozerov.data.utils.dispatchers.Constants
import javax.inject.Singleton

@Module
class RetrofitModule {

    @Provides
    @Singleton
    fun provideRetrofit(
        token: String
    ): Retrofit {
        val moshiConverterFactory = MoshiConverterFactory.create(Moshi.Builder().build())
        val loggingInterceptor = HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY)
        val client = OkHttpClient.Builder()
            .addInterceptor(loggingInterceptor)
            .addInterceptor { chain ->
                val builder = chain.request().newBuilder()
                builder.addHeader("Bearer-Authorization", token)
                chain.proceed(builder.build())
            }
            .build()
        return Retrofit.Builder()
            .baseUrl(Constants.BASE_URL)
            .client(client)
            .addConverterFactory(moshiConverterFactory)
            .build()
    }

    @Provides
    @Singleton
    fun provideLoginApi(
        retrofit: Retrofit
    ) : LoginApi {
        return retrofit.create(LoginApi::class.java)
    }

    @Provides
    @Singleton
    fun provideGoodsApi(
        retrofit: Retrofit
    ) : GoodsApi {
        return retrofit.create(GoodsApi::class.java)
    }

}