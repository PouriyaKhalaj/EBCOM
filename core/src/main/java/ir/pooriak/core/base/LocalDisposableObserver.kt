package ir.pooriak.core.base

import io.reactivex.rxjava3.observers.ResourceSingleObserver
import ir.pooriak.core.usecase.LocalDomainResult
import ir.pooriak.core.usecase.LocalUseCaseState
import ir.pooriak.core.usecase.UseCaseState
import retrofit2.HttpException

/**
 * Created by POORIAK on 13,September,2023
 */
class LocalDisposableObserver<T : Any, SR, M : Mapper<SR, T>>(
    private val result: LocalDomainResult<SR>? = null,
    private val mapper: M,
    private val onSuccess: ((T) -> Unit)? = null,
    private val onApiError: ((ApiError) -> Unit)? = null,
    private val onNetworkError: ((UseCaseState.NetworkError<SR>) -> Unit)? = null,
    private val onUndefinedError: ((Throwable) -> Unit)? = null
) : ResourceSingleObserver<T>() {
    override fun onSuccess(response: T) {
        mappedToDomainModelAndNotify(response)
        dispose()
    }

    private fun handleUndefinedError(e: Throwable?) {
        e?.let {
            result?.invoke(LocalUseCaseState.Error(it))
            onUndefinedError?.invoke(it)
            it.printStackTrace()
        }
    }

    private fun mappedToDomainModelAndNotify(data: T) {
        val mappedData = when (data) {
            is EntityModel -> mapper.dataToDomainModel(data)
            is List<*> -> data.map {
                when (it) {
                    is EntityModel -> it.toDomainModel()
                    else -> {}
                }
            }

            else -> Unit
        } as SR

        result?.invoke(LocalUseCaseState.Success(mappedData))

    }

    private fun handleHttpError(
        e: HttpException,
        callback: ((UseCaseState<SR>) -> Unit)?,
        onNetworkError: ((UseCaseState.NetworkError<SR>) -> Unit)? = null
    ) {
        val detail = UseCaseState.NetworkError<SR>(NetworkErrorDetail(e.code(), e.message()))
        callback?.invoke(detail)
        onNetworkError?.invoke(detail)
        e.printStackTrace()
    }

    override fun onError(e: Throwable) {
        handleUndefinedError(e)
        dispose()
    }
}