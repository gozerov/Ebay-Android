package ru.gozerov.data.remote.goods.models

import ru.gozerov.data.models.GoodData

data class GetGoodsResponseBody(
    val goods: List<GoodData>
)
