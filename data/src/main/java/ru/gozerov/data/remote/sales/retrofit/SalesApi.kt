package ru.gozerov.data.remote.sales.retrofit

import retrofit2.http.GET
import ru.gozerov.data.remote.sales.models.GetSalesResponse

interface SalesApi {

    @GET("/sales")
    suspend fun getSales() : GetSalesResponse

}