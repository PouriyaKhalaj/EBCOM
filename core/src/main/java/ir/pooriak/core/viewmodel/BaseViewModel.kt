package ir.pooriak.core.viewmodel


import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.core.Observer
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.disposables.Disposable
import io.reactivex.rxjava3.subjects.PublishSubject

/**
 * Created by POORIAK on 13,September,2023
 */

interface BaseState
interface BaseViewModelEvent


abstract class BaseViewModel<STATE : BaseState, VME : BaseViewModelEvent>
    : ViewModel(), Observer<Unit> {
    private val disposables = CompositeDisposable()
    private val events = PublishSubject.create<VME>()

    protected val state = MultipleLiveEvent<STATE>()
    fun getState(): LiveData<STATE> = state

    init {
        events.flatMap { Observable.just(onEvent(it)) }.observeOn(AndroidSchedulers.mainThread())
            .subscribe(this)
    }

    override fun onSubscribe(d: Disposable) {
        disposables.add(d)
    }

    override fun onCleared() {
        super.onCleared()
        disposables.dispose()
    }

    override fun onComplete() {

    }

    override fun onNext(t: Unit) {

    }

    fun event(event: VME) {
        events.onNext(event)
    }

    override fun onError(e: Throwable) {
        e.printStackTrace()
        disposables.clear()
        events.flatMap { Observable.just(onEvent(it)) }.observeOn(AndroidSchedulers.mainThread())
            .subscribe(this)
    }

    protected abstract fun onEvent(event: VME)

}