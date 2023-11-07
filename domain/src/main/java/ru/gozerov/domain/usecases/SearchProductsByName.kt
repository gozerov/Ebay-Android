package ru.gozerov.domain.usecases

import ru.gozerov.domain.models.Good
import ru.gozerov.domain.repositories.GoodsRepository
import javax.inject.Inject

class SearchProductsByName @Inject constructor(
    private val goodsRepository: GoodsRepository
) : UseCase<String, List<Good>>() {

    override suspend fun loadData(arg: String): List<Good> {
        return goodsRepository.searchProductsByName(arg)
    }

}