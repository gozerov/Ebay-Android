package ru.gozerov.data.remote.goods

import ru.gozerov.data.models.CategoryData
import ru.gozerov.data.models.GoodData
import ru.gozerov.data.models.toAddRequestBody
import ru.gozerov.data.remote.goods.retrofit.GoodsApi
import javax.inject.Inject

class GoodsRemoteImpl @Inject constructor(
    private val goodsApi: GoodsApi,
): GoodsRemote {

    override suspend fun getGoods(): List<GoodData> = goodsApi.getGoods().goods

    override suspend fun getGoodById(id: Int): GoodData = goodsApi.getGoodById(id).good

    override suspend fun getGoodsByPage(page: Int): List<GoodData> = goodsApi.getGoodsInParts(page).goods

    override suspend fun getGoodsPack(): Map<String, List<GoodData>> = goodsApi.getGoodsPack().value

    override suspend fun getFeaturedGoods(): List<GoodData> = goodsApi.getGoodsInParts(0).goods

    override suspend fun getGoodsByCategory(name: String): Pair<CategoryData, List<GoodData>> =
        goodsApi.getGoodsByCategory(name).run { this.category to this.goods }

    override suspend fun addGood(goodData: GoodData): Boolean = goodsApi.addGood(goodData.toAddRequestBody()).isSuccessful

    override suspend fun getCategories(): List<CategoryData> = goodsApi.getCategories().value

    override suspend fun searchProductsByName(name: String): List<GoodData> = goodsApi.searchProductsByName(name).goods

}