package ru.gozerov.data.utils.dispatchers

import kotlinx.coroutines.CoroutineDispatcher

sealed class Dispatcher(
    val value: CoroutineDispatcher
) {

    data class IODispatcher(
        val dispatcher: CoroutineDispatcher
    ) : Dispatcher(dispatcher)

}
