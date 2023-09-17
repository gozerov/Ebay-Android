package ru.gozerov.domain.usecases

import ru.gozerov.domain.models.Good
import ru.gozerov.domain.repositories.GoodsRepository
import javax.inject.Inject

class GetGoodsPack @Inject constructor(
    private val goodsRepository: GoodsRepository
) : UseCase<Unit, Map<String, List<Good>>>() {

    override suspend fun loadData(arg: Unit): Map<String, List<Good>> {
        return goodsRepository.getGoodsPack()
    }

}