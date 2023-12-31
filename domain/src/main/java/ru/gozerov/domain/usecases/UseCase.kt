package ru.gozerov.domain.usecases

import android.util.Log
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
                    Log.e("AAA", e.message.toString())
            if (e is HttpException)
                onHttpError?.run {
                    invoke(e.response()?.errorBody()?.string())
                } ?: onError?.invoke(e)
            else
                onError?.invoke(e)
        }
    }

}