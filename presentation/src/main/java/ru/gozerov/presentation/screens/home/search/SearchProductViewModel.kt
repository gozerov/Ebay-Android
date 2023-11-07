package ru.gozerov.presentation.screens.home.search

import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import ru.gozerov.domain.usecases.SearchProductsByName
import ru.gozerov.presentation.utils.BaseViewModel
import javax.inject.Inject

class SearchProductViewModel<T, U> @Inject constructor(
    private val searchProductsByName: SearchProductsByName
) : BaseViewModel<SearchProductIntent, SearchProductViewState>() {

    override fun handleIntent(intent: SearchProductIntent) {
        viewModelScope.launch {
            when(intent) {
                is SearchProductIntent.EmptySearch -> _viewState.emit(SearchProductViewState.Empty)
                is SearchProductIntent.SearchByName -> {
                    searchProductsByName.execute(
                        arg = intent.name,
                        onSuccess = {
                            _viewState.emit(SearchProductViewState.SuccessSearch(it))
                        },
                        onError = {
                            _viewState.emit(SearchProductViewState.Error)
                        }
                    )
                }
            }
        }

    }

}