package ru.gozerov.presentation.screens.home.home_page

import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import ru.gozerov.domain.usecases.GetCategories
import ru.gozerov.domain.usecases.GetGoodsInParts
import ru.gozerov.domain.usecases.GetGoodsPack
import ru.gozerov.domain.usecases.GetSales
import ru.gozerov.presentation.utils.BaseViewModel
import javax.inject.Inject

class HomePageViewModel<T, U> @Inject constructor(
    private val getGoodsPack: GetGoodsPack,
    private val getSales: GetSales,
    private val getCategories: GetCategories
) : BaseViewModel<HomePageIntent, HomePageViewState>() {

    var scrollPosition: Int? = null
        private set

    init {
        viewModelScope.launch {
            launch {
                getCategories.execute(
                    arg = Unit,
                    onSuccess = {
                        _viewState.emit(HomePageViewState.SuccessCategoriesLoading(scrollPosition, it))
                    },
                    onError = {
                        _viewState.emit(HomePageViewState.UnknownError(scrollPosition))
                    }
                )
            }
            launch {
                getGoodsPack.execute(
                    arg = Unit,
                    onSuccess = {
                        _viewState.emit(HomePageViewState.SuccessGoodsLoading(scrollPosition, it))
                    },
                    onError = {
                        _viewState.emit(HomePageViewState.UnknownError(scrollPosition))
                    }
                )
            }
            launch {
                getSales.execute(
                    arg = Unit,
                    onSuccess = {
                        _viewState.emit(HomePageViewState.SuccessSalesLoading(scrollPosition, it))
                    },
                    onError = {
                        _viewState.emit(HomePageViewState.UnknownError(scrollPosition))
                    }
                )
            }

        }
    }

    override fun handleIntent(intent: HomePageIntent) {
        viewModelScope.launch {
            when(intent) {
                is HomePageIntent.StartScreen -> _viewState.emit(HomePageViewState.Empty(scrollPosition))
                is HomePageIntent.SaveScrollPosition -> scrollPosition = intent.scrollPosition
            }
        }
    }

}