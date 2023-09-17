package ru.gozerov.domain.usecases

import ru.gozerov.domain.models.Sale
import ru.gozerov.domain.repositories.SalesRepository
import javax.inject.Inject

class GetSales @Inject constructor(
    private val salesRepository: SalesRepository
) : UseCase<Unit, List<Sale>>() {

    override suspend fun loadData(arg: Unit): List<Sale> {
        return salesRepository.getSales()
    }

}