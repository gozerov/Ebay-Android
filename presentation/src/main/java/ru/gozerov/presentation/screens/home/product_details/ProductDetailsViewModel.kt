package ru.gozerov.presentation.screens.home.product_details

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.BufferOverflow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import ru.gozerov.domain.usecases.GetFeaturedGoods
import ru.gozerov.domain.usecases.GetGoodById
import javax.inject.Inject

@HiltViewModel
class ProductDetailsViewModel @Inject constructor(
    private val getGoodById: GetGoodById,
    private val getFeaturedGoods: GetFeaturedGoods
) : ViewModel() {

    private val _viewState = MutableSharedFlow<ProductDetailsViewState>(1, 0, BufferOverflow.DROP_OLDEST)
    val viewState: SharedFlow<ProductDetailsViewState> = _viewState.asSharedFlow()

    fun handleIntent(intent: ProductDetailsIntent) {
        viewModelScope.launch {
            when(intent) {
                is ProductDetailsIntent.LoadProduct -> {
                    launch {
                        getGoodById.execute(
                            arg = intent.id,
                            onSuccess = {
                                _viewState.emit(ProductDetailsViewState.SuccessLoading(it))
                            }
                        )
                    }
                    launch {
                        getFeaturedGoods.execute(
                            arg = Unit,
                            onSuccess = {
                                _viewState.emit(ProductDetailsViewState.FeaturedProductsLoaded(it))
                            }
                        )
                    }
                }
            }
        }

    }

}