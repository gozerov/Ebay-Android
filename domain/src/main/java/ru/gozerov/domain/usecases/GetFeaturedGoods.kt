package ru.gozerov.domain.usecases

import ru.gozerov.domain.models.Good
import ru.gozerov.domain.repositories.GoodsRepository
import javax.inject.Inject

class GetFeaturedGoods @Inject constructor(
    private val goodsRepository: GoodsRepository
) : UseCase<Unit, List<Good>>() {

    override suspend fun loadData(arg: Unit): List<Good> {
        return goodsRepository.getFeaturedGoods()
    }

}