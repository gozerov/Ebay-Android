package ru.gozerov.data.repositories.goods

import androidx.paging.PagingSource
import kotlinx.coroutines.withContext
import ru.gozerov.data.models.toCategory
import ru.gozerov.data.models.toGood
import ru.gozerov.data.models.toGoodData
import ru.gozerov.data.remote.goods.GoodsRemote
import ru.gozerov.data.remote.goods.paging.GoodsPagingSource
import ru.gozerov.data.utils.dispatchers.Dispatcher
import ru.gozerov.domain.models.Category
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

    override suspend fun getGoodsByPage(): PagingSource<Int, Good> = withContext(dispatcher.value) {
        return@withContext GoodsPagingSource(goodsRemote)
    }

    override suspend fun getGoodsPack(): Map<String, List<Good>> = withContext(dispatcher.value){
        return@withContext goodsRemote.getGoodsPack().mapValues { e -> e.value.map { it.toGood() } }
    }

    override suspend fun getFeaturedGoods(): List<Good> = withContext(dispatcher.value) {
       return@withContext goodsRemote.getFeaturedGoods().map { it.toGood() }
    }

    override suspend fun getGoodsByCategory(category: String): Pair<Category, List<Good>> = withContext(dispatcher.value) {
        return@withContext goodsRemote.getGoodsByCategory(category).run { first.toCategory() to second.map { it.toGood() } }
    }

    override suspend fun addGood(good: Good): Boolean = withContext(dispatcher.value) {
        return@withContext goodsRemote.addGood(good.toGoodData())
    }

    override suspend fun getCategories(): List<Category> = withContext(dispatcher.value) {
        return@withContext goodsRemote.getCategories().map { it.toCategory() }
    }

    override suspend fun searchProductsByName(arg: String): List<Good> = withContext(dispatcher.value) {
        return@withContext goodsRemote.searchProductsByName(arg).map { it.toGood() }
    }

}