package ru.gozerov.presentation.screens.home.home_page

import ru.gozerov.presentation.utils.Intent

sealed class HomePageIntent : Intent {

    object StartScreen : HomePageIntent()

    data class SaveScrollPosition(
        val scrollPosition: Int
    ) : HomePageIntent()

}
