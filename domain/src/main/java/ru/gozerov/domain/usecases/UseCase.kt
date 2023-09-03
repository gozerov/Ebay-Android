package ru.gozerov.domain.usecases

import retrofit2.HttpException

abstract class UseCase<T, R> {

    protected abstract suspend fun loadData(arg: T) : R

    suspend fun execute(
        arg: T,
        onSuccess: suspend (R) -> Unit,
        onError: (suspend (e: Exception) -> Unit)? = null,
        onHttpError: (suspend (e: String?) -> Unit)? = null
    ) {
        try {
            val result = loadData(arg)
            onSuccess(result)
        } catch (e: Exception) {
            if (e is HttpException)
                onHttpError?.invoke(e.response()?.errorBody()?.string())
            else
                onError?.invoke(e)
        }
    }

}