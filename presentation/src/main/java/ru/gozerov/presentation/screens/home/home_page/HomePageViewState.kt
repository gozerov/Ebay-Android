package ru.gozerov.presentation.screens.home.home_page

import androidx.paging.PagingSource
import ru.gozerov.domain.models.Category
import ru.gozerov.domain.models.Good
import ru.gozerov.domain.models.Sale
import ru.gozerov.presentation.utils.ViewState

sealed class HomePageViewState : ViewState {

    object Empty : HomePageViewState()

    data class SuccessGoodsLoading(
        val value: Map<String, List<Good>>
    ) : HomePageViewState()

    data class SuccessCategoriesLoading(
        val value: List<Category>
    ) : HomePageViewState()

    data class SuccessSalesLoading(
        val value: List<Sale>
    ) : HomePageViewState()

    object UnknownError : HomePageViewState()

}