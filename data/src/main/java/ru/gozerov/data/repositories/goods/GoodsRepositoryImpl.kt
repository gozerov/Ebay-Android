package ru.gozerov.data.repositories.goods

import kotlinx.coroutines.withContext
import ru.gozerov.data.models.toGood
import ru.gozerov.data.models.toGoodData
import ru.gozerov.data.remote.goods.GoodsRemote
import ru.gozerov.data.utils.dispatchers.Dispatcher
import ru.gozerov.domain.models.Good
import ru.gozerov.domain.repositories.GoodsRepository
import javax.inject.Inject

class GoodsRepositoryImpl @Inject constructor(
    private val dispatcher: Dispatcher,
    private val goodsRemote: GoodsRemote
): GoodsRepository {

    override suspend fun getGoods(): List<Good> = withContext(dispatcher.value) {
        return@withContext goodsRemote.getGoods().map { it.toGood() }
    }

    override suspend fun getGoodById(id: Int): Good = withContext(dispatcher.value) {
        return@withContext goodsRemote.getGoodById(id).toGood()
    }

    override suspend fun getGoodsByPage(page: Int): List<Good> = withContext(dispatcher.value) {
        return@withContext goodsRemote.getGoodsByPage(page).map { it.toGood() }
    }

    override suspend fun addGood(good: Good): Boolean = withContext(dispatcher.value) {
        return@withContext goodsRemote.addGood(good.toGoodData())
    }

}