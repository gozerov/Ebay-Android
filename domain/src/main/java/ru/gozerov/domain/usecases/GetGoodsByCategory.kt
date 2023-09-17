package ru.gozerov.domain.usecases

import ru.gozerov.domain.models.Category
import ru.gozerov.domain.models.Good
import ru.gozerov.domain.repositories.GoodsRepository
import javax.inject.Inject

class GetGoodsByCategory @Inject constructor(
    private val goodsRepository: GoodsRepository
) : UseCase<String, Pair<Category, List<Good>>>() {

    override suspend fun loadData(arg: String): Pair<Category, List<Good>> {
        return goodsRepository.getGoodsByCategory(arg)
    }

}