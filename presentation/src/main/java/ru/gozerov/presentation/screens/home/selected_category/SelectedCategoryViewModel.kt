package ru.gozerov.presentation.screens.home.selected_category

import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import ru.gozerov.domain.usecases.GetGoodsByCategory
import ru.gozerov.presentation.utils.BaseViewModel
import javax.inject.Inject

class SelectedCategoryViewModel<T, U> @Inject constructor(
    private val getGoodsByCategory: GetGoodsByCategory
): BaseViewModel<SelectedCategoryIntent, SelectedCategoryViewState>() {

    override fun handleIntent(intent: SelectedCategoryIntent) {
        viewModelScope.launch {
            when(intent) {
                is SelectedCategoryIntent.LoadProducts -> {
                    getGoodsByCategory.execute(
                        arg = intent.category,
                        onSuccess = {
                            _viewState.emit(SelectedCategoryViewState.SuccessLoading(it.second))
                        },
                        onError = {
                            _viewState.emit(SelectedCategoryViewState.Error)
                        }
                    )
                }
            }
        }
    }

}