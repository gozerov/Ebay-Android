package ru.gozerov.data.remote.goods

import ru.gozerov.data.models.GoodData
import ru.gozerov.domain.models.Good

interface GoodsRemote {

    suspend fun getGoods() : List<GoodData>

    suspend fun getGoodById(id: Int) : GoodData

    suspend fun getGoodsByPage(page: Int) : List<GoodData>

    suspend fun addGood(goodData: GoodData) : Boolean

}