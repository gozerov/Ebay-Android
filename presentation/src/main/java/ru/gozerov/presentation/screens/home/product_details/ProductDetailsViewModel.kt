package ru.gozerov.presentation.screens.home.product_details

import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import ru.gozerov.domain.usecases.GetFeaturedGoods
import ru.gozerov.domain.usecases.GetGoodById
import ru.gozerov.presentation.utils.BaseViewModel
import javax.inject.Inject

class ProductDetailsViewModel<T, U> @Inject constructor(
    private val getGoodById: GetGoodById,
    private val getFeaturedGoods: GetFeaturedGoods
) : BaseViewModel<ProductDetailsIntent, ProductDetailsViewState>() {

    override fun handleIntent(intent: ProductDetailsIntent) {
        viewModelScope.launch {
            when(intent) {
                is ProductDetailsIntent.LoadProduct -> {
                    launch {
                        getGoodById.execute(
                            arg = intent.id,
                            onSuccess = {
                                _viewState.emit(ProductDetailsViewState.SuccessLoading(it))
                            },
                            onError = {
                                _viewState.emit(ProductDetailsViewState.Error)
                            }
                        )
                    }
                    launch {
                        getFeaturedGoods.execute(
                            arg = Unit,
                            onSuccess = {
                                _viewState.emit(ProductDetailsViewState.FeaturedProductsLoaded(it))
                            },
                            onError = {
                                _viewState.emit(ProductDetailsViewState.Error)
                            }
                        )
                    }
                }
            }
        }

    }

}