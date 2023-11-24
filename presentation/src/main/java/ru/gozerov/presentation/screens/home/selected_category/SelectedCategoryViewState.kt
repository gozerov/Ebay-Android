package ru.gozerov.presentation.screens.home.selected_category

import ru.gozerov.domain.models.Good
import ru.gozerov.presentation.utils.ViewState

sealed class SelectedCategoryViewState : ViewState {

    object Empty : SelectedCategoryViewState()

    data class SuccessLoading(
        val products: List<Good>
    ) : SelectedCategoryViewState()

    object Error : SelectedCategoryViewState()

}
