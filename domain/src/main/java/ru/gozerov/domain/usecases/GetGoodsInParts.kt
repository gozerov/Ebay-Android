package ru.gozerov.domain.usecases

import androidx.paging.PagingSource
import ru.gozerov.domain.models.Good
import ru.gozerov.domain.repositories.GoodsRepository
import javax.inject.Inject

class GetGoodsInParts @Inject constructor(
    private val goodsRepository: GoodsRepository
) : UseCase<Unit, PagingSource<Int, Good>>() {

    override suspend fun loadData(arg: Unit): PagingSource<Int, Good> {
        return goodsRepository.getGoodsByPage()
    }

}