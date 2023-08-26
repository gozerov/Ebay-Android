package ru.gozerov.data.remote.goods.paging

import androidx.paging.PagingSource
import androidx.paging.PagingState
import ru.gozerov.data.models.toGood
import ru.gozerov.data.remote.goods.GoodsRemote
import ru.gozerov.domain.models.Good
import javax.inject.Inject

class GoodsPagingSource @Inject constructor(
    private val goodsRemote: GoodsRemote
) : PagingSource<Int, Good>() {

    override fun getRefreshKey(state: PagingState<Int, Good>): Int? {
        val anchorPosition = state.anchorPosition ?: return null
        val anchorPage = state.closestPageToPosition(anchorPosition) ?: return null
        return anchorPage.prevKey?.plus(1) ?: anchorPage.nextKey?.minus(1)
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Good> {
        return try {
            val pageIndex = params.key ?: DEFAULT_PAGE_INDEX
            val goods = goodsRemote.getGoodsByPage(pageIndex)
            LoadResult.Page(
                data = goods.map { it.toGood() },
                prevKey = if (pageIndex == 0) null else pageIndex - 1,
                nextKey = if (goods.isEmpty()) null else pageIndex + 1
            )
        } catch (e: Exception) {
            LoadResult.Error(e)
        }
    }

    companion object {

        const val DEFAULT_PAGE_INDEX = 0

    }

}