package ir.pooriak.core.base

import io.reactivex.rxjava3.observers.ResourceSingleObserver
import ir.pooriak.core.usecase.DomainResult
import ir.pooriak.core.usecase.UseCaseState
import retrofit2.HttpException
import java.io.IOException

/**
 * Created by POORIAK on 13,September,2023
 */
class NetworkDisposableObserver<T : Any, SR, M : Mapper<SR, T>>(
    private val result: DomainResult<SR>? = null,
    private val mapper: M? = null,
    private val onSuccess: ((T) -> Unit)? = null,
    private val onApiError: ((ApiError) -> Unit)? = null,
    private val onNetworkError: ((UseCaseState.NetworkError<SR>) -> Unit)? = null,
    private val onUndefinedError: ((Throwable) -> Unit)? = null
) : ResourceSingleObserver<GenericResponse<T>>() {
    override fun onSuccess(response: GenericResponse<T>) {

        when (response) {
            is NetworkResponse.ApiError -> {
                response.apiError.let {
                    result?.invoke(UseCaseState.ApiError(it))
                    onApiError?.invoke(it)
                }
            }

            is NetworkResponse.NetworkError -> {
                handleHttpError(response.error, result, onNetworkError)
            }

            is NetworkResponse.Success -> result?.let {
                mappedToJsonModelAndNotify(response.data)
                response.data.let { onSuccess?.invoke(it) }
            }

            is NetworkResponse.UnknownError -> {
                handleUndefinedError(response.error)
            }
        }
        dispose()
    }

    override fun onError(e: Throwable) {
        handleUndefinedError(e)
        dispose()
    }

    private fun handleUndefinedError(e: Throwable?) {
        e?.let {
            when (e) {
                is NetworkConnectionException -> {
//                    networkConnectionNotification()
                }

                is VPNConnectionException -> {
//                    vpnConnectionNotification()
                }

                is NetworkSSLHandshakeException -> {
//                    networkSSLHandshakeNotification()
                }

                else -> {
                    result?.invoke(UseCaseState.Error(it))
                    onUndefinedError?.invoke(it)
                    it.printStackTrace()
                }
            }
        }
    }

    private fun mappedToJsonModelAndNotify(data: T) {
        val mappedData = when (data) {
            is DataModel -> mapper?.dataToDomainModel(data)
            else -> Unit
        } as SR
        result?.invoke(UseCaseState.Success(mappedData))

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
}

class NetworkConnectionException : IOException()
class VPNConnectionException : IOException()
class NetworkSSLHandshakeException : IOException()