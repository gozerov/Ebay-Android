package ru.gozerov.domain.usecases

import ru.gozerov.domain.models.Category
import ru.gozerov.domain.repositories.GoodsRepository
import javax.inject.Inject

class GetCategories @Inject constructor(
    private val goodsRepository: GoodsRepository
) : UseCase<Unit, List<Category>>() {

    override suspend fun loadData(arg: Unit): List<Category> {
        return goodsRepository.getCategories()
    }

}