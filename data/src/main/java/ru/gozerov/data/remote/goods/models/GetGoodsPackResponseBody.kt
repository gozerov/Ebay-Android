package ru.gozerov.data.remote.goods.models

import ru.gozerov.data.models.GoodData

data class GetGoodsPackResponseBody(
    val value: Map<String, List<GoodData>>
)
