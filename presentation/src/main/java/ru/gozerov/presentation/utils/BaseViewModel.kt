package ru.gozerov.presentation.utils

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.channels.BufferOverflow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.asSharedFlow

abstract class BaseViewModel<I: Intent, V : ViewState> : ViewModel() {

    protected val _viewState: MutableSharedFlow<V> = MutableSharedFlow(1, 0, BufferOverflow.DROP_OLDEST)
    val viewState: SharedFlow<V> = _viewState.asSharedFlow()

    abstract fun handleIntent(intent: I)

}