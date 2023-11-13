package ru.gozerov.domain.repositories

import androidx.paging.PagingSource
import ru.gozerov.domain.models.Category
import ru.gozerov.domain.models.Good

interface GoodsRepository : Repository {

    suspend fun getGoods() : List<Good>

    suspend fun getGoodById(id: Int) : Good

    suspend fun getGoodsByPage() : PagingSource<Int, Good>

    suspend fun getGoodsPack() : Map<String, List<Good>>

    suspend fun getFeaturedGoods() : List<Good>

    suspend fun getGoodsByCategory(category: String) : Pair<Category, List<Good>>

    suspend fun addGood(good: Good) : Boolean

    suspend fun getCategories() : List<Category>

    suspend fun searchProductsByName(arg: String): List<Good>

}