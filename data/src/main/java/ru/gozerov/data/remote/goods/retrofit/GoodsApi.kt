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
import ru.gozerov.data.remote.goods.models.GetCategoriesResponseBody
import ru.gozerov.data.remote.goods.models.GetGoodByIdResponseBody
import ru.gozerov.data.remote.goods.models.GetGoodsByCategoryResponseBody
import ru.gozerov.data.remote.goods.models.GetGoodsPackResponseBody
import ru.gozerov.data.remote.goods.models.GetGoodsResponseBody
import ru.gozerov.data.utils.dispatchers.Constants

interface GoodsApi {

    @GET("/goods/get")
    suspend fun getGoods() : GetGoodsResponseBody

    @GET("/goods/get")
    suspend fun getGoodsInParts(@Query("page") page: Int) : GetGoodsResponseBody

    @GET("/goods/get")
    suspend fun getGoodsByCategory(@Query("category") name: String) : GetGoodsByCategoryResponseBody

    @POST("/goods/pack")
    suspend fun getGoodsPack() : GetGoodsPackResponseBody

    @GET("/goods/get")
    suspend fun getGoodById(@Query("id") id: Int) : GetGoodByIdResponseBody

    @GET("/category")
    suspend fun getCategories() : GetCategoriesResponseBody

    @POST("/goods/add")
    suspend fun addGood(@Body addGoodRequestBody: AddGoodRequestBody) : AddGoodResponseBody

}

fun main() {
    val moshiConverterFactory = MoshiConverterFactory.create(Moshi.Builder().build())
    val loggingInterceptor = HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY)
    val client = OkHttpClient.Builder()
        .addInterceptor(loggingInterceptor)
        .addInterceptor { chain ->
            val builder = chain.request().newBuilder()
            builder.addHeader("Bearer-Authorization", "23a43645-8c5f-454b-8973-aa23e78e3ba5")
            chain.proceed(builder.build())
        }
        .build()
    val retrofit = Retrofit.Builder()
        .baseUrl(Constants.BASE_URL)
        .client(client)
        .addConverterFactory(moshiConverterFactory)
        .build()
    val goodsApi = retrofit.create(GoodsApi::class.java)
    runBlocking {
        println(goodsApi.getGoodsPack().value.toString())
    }
}