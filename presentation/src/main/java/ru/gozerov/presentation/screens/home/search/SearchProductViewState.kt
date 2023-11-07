package ru.gozerov.presentation.screens.home.search

import ru.gozerov.domain.models.Good
import ru.gozerov.presentation.utils.ViewState

sealed class SearchProductViewState : ViewState {

    object Empty : SearchProductViewState()

    data class SuccessSearch(
        val goods: List<Good>
    ) : SearchProductViewState()

    object Error : SearchProductViewState()

}
