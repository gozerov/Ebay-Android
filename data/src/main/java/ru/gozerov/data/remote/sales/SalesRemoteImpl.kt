package ru.gozerov.data.remote.sales

import ru.gozerov.data.remote.sales.models.SaleRemote
import ru.gozerov.data.remote.sales.retrofit.SalesApi
import javax.inject.Inject

class SalesRemoteImpl @Inject constructor(
    private val salesApi: SalesApi
) : SalesRemote {

    override suspend fun getSales(): List<SaleRemote> = salesApi.getSales().sales

}