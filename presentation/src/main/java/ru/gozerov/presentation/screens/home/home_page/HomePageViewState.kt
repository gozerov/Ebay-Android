package ru.gozerov.presentation.screens.home.home_page

import androidx.paging.PagingSource
import ru.gozerov.domain.models.Category
import ru.gozerov.domain.models.Good
import ru.gozerov.domain.models.Sale
import ru.gozerov.presentation.utils.ViewState

sealed class HomePageViewState(
    val scrollPosition: Int? = null
) : ViewState {

    data class Empty(
        val scroll: Int? = null,
    ) : HomePageViewState(scroll)

    data class SuccessGoodsLoading(
        val scroll: Int? = null,
        val value: Map<String, List<Good>>
    ) : HomePageViewState(scroll)

    data class SuccessCategoriesLoading(
        val scroll: Int? = null,
        val value: List<Category>
    ) : HomePageViewState(scroll)

    data class SuccessSalesLoading(
        val scroll: Int? = null,
        val value: List<Sale>
    ) : HomePageViewState(scroll)

    data class UnknownError(
        val scroll: Int? = null
    ) : HomePageViewState(scroll)

}