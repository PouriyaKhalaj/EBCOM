package ir.pooriak.core.base.repository

import android.annotation.SuppressLint
import androidx.annotation.StringDef
import androidx.room.rxjava3.EmptyResultSetException
import io.reactivex.rxjava3.core.Single
import io.reactivex.rxjava3.schedulers.Schedulers
import io.reactivex.rxjava3.subjects.BehaviorSubject
import ir.pooriak.core.tools.Debouncer
import java.util.concurrent.TimeUnit


/**
 * Created by POORIAK on 16,September,2023
 *
 * A generic class that can provide a resource backed by both the database and the network. The resource is fetch when the output is subscribed to.
 *
 * @param <DatabaseResponseType>
 * @param <NetworkResponseType>
</NetworkResponseType></DatabaseResponseType> */
abstract class NetworkBoundResource<DatabaseResponseType : Any, NetworkResponseType : Any> {
    /**
     * A easy way to get the current state of data
     */
    val value: DatabaseResponseType?
        get() = result.value

    /**
     * Can be changed to return a Resource class that encapsulate both the data and its state as well.
     */
    private val result = BehaviorSubject.create<DatabaseResponseType>()

    /**
     * Helps in resetting the output or result, by filtering out last value when {@code State} is OFF
     */
    @State
    private var state = OFF

    /**
     * A simple solution for when the observable would be subscribed in more than one places, since fetching more than once isn't really necessary unless it meets {@code shouldFetch} condition.
     */
    private var firstCallFinished: Boolean = false

    /**
     * A simple helper to debounce initial subscription if the data is subscribed to more than one streams at the same time.
     */
    private val debouncer = Debouncer()

    /**
     * Fetch the data from network and persist into DB, make required transformations and then send it back to UI. This function can also be used to request a new data from the server.
     */
    private fun fetchFromNetwork(): Single<NetworkResponseType> {
        return createCall()
            .subscribeOn(Schedulers.io())
            .doOnSuccess {
                saveCallResult(it)
            }
            //.map { processResult(it?.data) }
            .doOnSuccess {
                firstCallFinished = true
                // setValue(it)
            }
    }

    /**
     * Send the data back to UI.
     */
    private fun setValue(newValue: DatabaseResponseType) {
        if (result.value != newValue) {
            newValue?.let { result.onNext(newValue) }
        }
    }

    /**
     * A method exposed send a new stream of data to the UI as well as to persist into DB. The callback returns the last state as argument.
     */
    /*    fun setValueCallback(callback: (DatabaseResponseType?) -> BaseResponse<DatabaseResponseType>) {
            val item = callback(value?.data)
            val newValue = processResult(item?.data)
            saveCallResult(item.data)
            setValue(newValue)
        }*/

    abstract fun processResult(it: NetworkResponseType): DatabaseResponseType

    /**
     * Called to save the result of the API response into the database
     */
    protected abstract fun saveCallResult(item: NetworkResponseType)

    /**
     * Called with the data in the database to decide whether to fetch potentially updated data from the network.
     */
    abstract fun shouldFetch(/*data: NetworkResponseType?*/): Boolean /*{
        return data == null
    }*/

    /**
     * Called to get the cached data from the database.
     */
    protected abstract fun loadFromDb(): Single<DatabaseResponseType>

    /**
     * Called to create the API call.
     */
    protected abstract fun createCall(): Single<NetworkResponseType>

    /**
     * Called when the fetch fails
     */
    protected open fun onFetchFailed(error: Throwable) {
        //
    }

    /**
     * Called when the first value is returned to the UI.
     */
    protected open fun onFirstSet(newValue: DatabaseResponseType) {
    }

    /**
     * Handle all the exceptions what would require you to fetch the data from the server. Room returns {@link EmptyResultSetException} when the record isn't found
     */
    protected open fun fetchOnError(error: Throwable) = error is EmptyResultSetException

    /**
     * Returns a Observable object that represents the resource that's implemented in the base class.
     * @param force Call with true to force running the code through {@code shouldFetch} condition.
     */
    fun asSingle(force: Boolean = false): Single<DatabaseResponseType> = result
        .doOnSubscribe {
            if (firstCallFinished && !force) return@doOnSubscribe
            getInitialData()
        }.filter { state == ON }.singleOrError()

    /**
     * Get the initial data, and debouce this method call to avoid fetching on two simultaneous subscriptions.
     */
    @SuppressLint("CheckResult")
    private fun getInitialData() {
        debouncer.debounce(Void::class.java, {
            asSingle()
                .subscribe({
                }, {
                    onFetchFailed(it)
                })
        }, 300, TimeUnit.MILLISECONDS)
    }


    /**
     * Returns a Single object that can be used to get the required data depending on {@code shouldFetch} condition. To be used when the
     */
    fun asSingle(): Single<DatabaseResponseType> = loadFromDb()
        .subscribeOn(Schedulers.io())
        /*        .map {
                    processResult(it)
                }*/
        .doOnSubscribe {
            state = ON
        }
        // Watch for a particular error, that can be used to trigger the network fetch.
        .onErrorResumeNext { error ->
            if (fetchOnError(error)) {
                return@onErrorResumeNext fetchFromNetwork().map { processResult(it) }
            }
            throw error
        }
        // Switch case depending on whether we should fetch new data or not.
        .flatMap {
            // setValue(it)
            return@flatMap if (shouldFetch(/*it*/) && !firstCallFinished) fetchFromNetwork().map {
                processResult(it)
            }
            else Single.just(it)
        }
        // .map { processResult(it as BaseResponse<NetworkResponseType>) }
        .doOnSuccess {
            setValue(it)
            it?.let { onFirstSet(it) }
            //onFirstSet(it.data)
            firstCallFinished = true
        }
        .flatMap { loadFromDb() }
        .doOnError {
            onFetchFailed(it)
        }

    /**
     * Clear the state of the database, so that it re-fetches on subscribe. Used for when the data source has to be flushed.
     */
    fun clear() {
        firstCallFinished = false
        state = OFF
    }

    companion object {
        @StringDef(ON, OFF)
        private annotation class State

        private const val OFF = "off"
        private const val ON = "on"
    }
}
