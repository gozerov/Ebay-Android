package ru.gozerov.data.remote.goods

import ru.gozerov.data.models.GoodData
import ru.gozerov.data.models.toAddRequestBody
import javax.inject.Inject

class GoodsRemoteImpl @Inject constructor(
    private val goodsApi: GoodsApi
): GoodsRemote {

    override suspend fun getGoods(): List<GoodData> = goodsApi.getGoods().goods

    override suspend fun getGoodById(id: Int): GoodData = goodsApi.getGoodById(id).good

    override suspend fun getGoodsByPage(page: Int): List<GoodData> = goodsApi.getGoodsInParts(page).goods

    override suspend fun addGood(goodData: GoodData): Boolean = goodsApi.addGood(goodData.toAddRequestBody()).isSuccessful

}