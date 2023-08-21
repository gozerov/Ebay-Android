package ru.gozerov.data.remote.goods.retrofit

import com.squareup.moshi.Moshi
import kotlinx.coroutines.runBlocking
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query
import ru.gozerov.data.remote.goods.models.AddGoodRequestBody
import ru.gozerov.data.remote.goods.models.AddGoodResponseBody
import ru.gozerov.data.remote.goods.models.GetGoodByIdResponseBody
import ru.gozerov.data.remote.goods.models.GetGoodsResponseBody

interface GoodsApi {

    @GET("/goods/get")
    suspend fun getGoods() : GetGoodsResponseBody

    @GET("/goods/get")
    suspend fun getGoodsInParts(@Query("page") page: Int) : GetGoodsResponseBody

    @GET("/goods/get")
    suspend fun getGoodById(@Query("id") id: Int) : GetGoodByIdResponseBody

    @POST("/goods/add")
    suspend fun addGood(@Body addGoodRequestBody: AddGoodRequestBody) : AddGoodResponseBody

}

fun main() {
    val loggingInterceptor = HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY)
    val client = OkHttpClient.Builder()
        .addInterceptor(loggingInterceptor)
        .addInterceptor { chain ->
            val builder = chain.request().newBuilder()
            builder.addHeader("Bearer-Authorization", "3c18a9ce-299d-4393-a648-03dce2f97ec3")
            chain.proceed(builder.build())
        }
        .build()

    val moshi = Moshi.Builder().build()
    val moshiConverterFactory = MoshiConverterFactory.create(moshi)

    val retrofit = Retrofit.Builder()
        .baseUrl("http://0.0.0.0:8080")
        .client(client)
        .addConverterFactory(moshiConverterFactory)
        .build()
    val goodsApi = retrofit.create(GoodsApi::class.java)
    runBlocking {
        println(goodsApi.getGoodById(100010).good.toString())
    }
}