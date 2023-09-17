package ru.gozerov.data.remote.sales

import ru.gozerov.data.remote.sales.models.SaleRemote

interface SalesRemote {

    suspend fun getSales() : List<SaleRemote>

}