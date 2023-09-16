package ir.pooriak.core.usecase

import ir.pooriak.core.base.NetworkErrorDetail

/**
 * Created by POORIAK on 13,September,2023
 */
typealias DomainResult<T> = (UseCaseState<T>) -> Unit
typealias LocalDomainResult<T> = (LocalUseCaseState<T>) -> Unit
typealias CacheDomainResult<T> = (CacheUseCaseState<T>) -> Unit

interface Params

sealed class UseCaseState<T> {

    /**
     * A state to notify loading data
     */
    class Loading<T> : UseCaseState<T>()

    /**
     * A state that shows the [data] is the last known update.
     */
    data class Success<T>(val data: T) : UseCaseState<T>()

    /**
     * A state that shows the [error] is the last known update.
     */
    data class ApiError<T>(val error: ir.pooriak.core.base.ApiError) : UseCaseState<T>()

    /**
     * A state to show a [throwable] is thrown .
     */
    data class Error<T>(val throwable: Throwable) : UseCaseState<T>()


    /**
     * A state to show a [http] errors .
     */
    data class NetworkError<T>(val http: NetworkErrorDetail) : UseCaseState<T>()

}

sealed class LocalUseCaseState<T> {


    /**
     * A state to notify loading data
     */
    class Loading<T> : LocalUseCaseState<T>()

    /**
     * A state that shows the [data] is the last known update.
     */
    data class Success<T>(val data: T) : LocalUseCaseState<T>()

    /**
     * A state to show a [throwable] is thrown .
     */
    data class Error<T>(val throwable: Throwable) : LocalUseCaseState<T>()

}

sealed class CacheUseCaseState<T> {

    /**
     * A state to notify loading data
     */
    class Loading<T> : CacheUseCaseState<T>()

    /**
     * A state that shows the [data] is the last known update.
     */
    data class Success<T>(val data: T) : CacheUseCaseState<T>()

}