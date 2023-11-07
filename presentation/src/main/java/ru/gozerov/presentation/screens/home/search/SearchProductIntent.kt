package ru.gozerov.presentation.screens.home.search

import ru.gozerov.presentation.utils.Intent

sealed class SearchProductIntent : Intent {

    object EmptySearch : SearchProductIntent()

    data class SearchByName(
        val name: String
    ) : SearchProductIntent()

}