package ru.gozerov.presentation.screens.home.selected_category

import ru.gozerov.presentation.utils.Intent

sealed class SelectedCategoryIntent : Intent {

    data class LoadProducts(
        val category: String
    ) : SelectedCategoryIntent()

}