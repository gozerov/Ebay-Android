package ru.gozerov.domain.usecases

import ru.gozerov.domain.models.Good
import ru.gozerov.domain.repositories.GoodsRepository
import javax.inject.Inject

class GetGoodById @Inject constructor(
    private val goodsRepository: GoodsRepository
) : UseCase<Int, Good>() {

    override suspend fun loadData(arg: Int): Good {
        return goodsRepository.getGoodById(arg)
    }

}