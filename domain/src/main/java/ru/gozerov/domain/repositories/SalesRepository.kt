package ru.gozerov.domain.repositories

import ru.gozerov.domain.models.Sale

interface SalesRepository : Repository {

    suspend fun getSales() : List<Sale>

}