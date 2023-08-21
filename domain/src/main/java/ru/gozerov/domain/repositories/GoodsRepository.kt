package ru.gozerov.domain.repositories

import androidx.paging.PagingSource
import ru.gozerov.domain.models.Good

interface GoodsRepository : Repository {

    suspend fun getGoods() : List<Good>

    suspend fun getGoodById(id: Int) : Good

    suspend fun getGoodsByPage() : PagingSource<Int, Good>

    suspend fun addGood(good: Good) : Boolean

}