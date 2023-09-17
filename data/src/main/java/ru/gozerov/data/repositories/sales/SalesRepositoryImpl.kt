package ru.gozerov.data.repositories.sales

import kotlinx.coroutines.withContext
import ru.gozerov.data.cache.SharedPreferencesStorage
import ru.gozerov.data.remote.sales.SalesRemote
import ru.gozerov.data.remote.sales.models.toSale
import ru.gozerov.data.utils.dispatchers.Dispatcher
import ru.gozerov.domain.models.Sale
import ru.gozerov.domain.repositories.SalesRepository
import javax.inject.Inject

class SalesRepositoryImpl @Inject constructor(
    private val salesRemote: SalesRemote,
    private val dispatcher: Dispatcher
) : SalesRepository {

    override suspend fun getSales(): List<Sale> = withContext(dispatcher.value) {
        salesRemote.getSales().map { it.toSale() }
    }

}