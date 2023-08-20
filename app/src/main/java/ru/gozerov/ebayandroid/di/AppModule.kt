package ru.gozerov.ebayandroid.di

import android.content.Context
import android.content.SharedPreferences
import com.squareup.moshi.Moshi
import dagger.Module
import dagger.Provides
import kotlinx.coroutines.Dispatchers
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import ru.gozerov.data.remote.goods.GoodsApi
import ru.gozerov.data.remote.login.LoginApi
import ru.gozerov.data.utils.dispatchers.Dispatcher
import javax.inject.Singleton

@Module(includes = [AppBindModule::class])
class AppModule {

    @Provides
    fun provideIODispatcher(): Dispatcher = Dispatcher.IODispatcher(Dispatchers.IO)

    @Provides
    @Singleton
    fun provideLoggingInterceptor() : HttpLoggingInterceptor {
        return HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY)
    }

    @Provides
    @Singleton
    fun provideMoshiConverterFactory() : MoshiConverterFactory {
        return MoshiConverterFactory.create(Moshi.Builder().build())
    }

    @Provides
    @Singleton
    fun provideSharedPreferences(context: Context) : SharedPreferences {
        return context.getSharedPreferences("EbaySharedPreferences", Context.MODE_PRIVATE)
    }

    @Provides
    fun provideToken(sharedPreferences: SharedPreferences) : String {
        return sharedPreferences.getString("API_TOKEN", "") ?: ""
    }

    @Provides
    @Singleton
    fun provideRetrofit(
        moshiConverterFactory: MoshiConverterFactory,
        loggingInterceptor: HttpLoggingInterceptor,
        token: String
    ): Retrofit {
        val client = OkHttpClient.Builder()
            .addInterceptor(loggingInterceptor)
            .addInterceptor { chain ->
                val builder = chain.request().newBuilder()
                builder.addHeader("Bearer-Authorization", token)
                chain.proceed(builder.build())
            }
            .build()
        return Retrofit.Builder()
            .baseUrl("http://10.0.2.2:8080")
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