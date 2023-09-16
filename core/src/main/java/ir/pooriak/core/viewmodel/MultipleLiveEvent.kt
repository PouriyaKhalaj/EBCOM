package ir.pooriak.core.viewmodel

import android.os.Handler
import android.os.Looper
import android.util.Log
import androidx.annotation.MainThread
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import java.util.LinkedList
import java.util.Queue
import java.util.concurrent.atomic.AtomicBoolean

/**
 * Created by POORIAK on 13,September,2023
 */
class MultipleLiveEvent<T> : MutableLiveData<T>() {
    private val mPending = AtomicBoolean(false)
    private val values: Queue<T?> = LinkedList()


    @MainThread
    override fun observe(owner: LifecycleOwner, observer: Observer<in T>) {
        if (hasActiveObservers()) {
            Log.w(
                this::class.java.name,
                "Multiple observers registered but only one will be notified of changes."
            )
        }
        // Observe the internal MutableLiveData
        super.observe(owner) { t: T? ->
            if (mPending.compareAndSet(true, false)) {
                t?.let { observer.onChanged(it) }
                //call next value processing if have such
                if (values.isNotEmpty())
                    pollValue()
            }
        }
    }

    override fun postValue(value: T?) {
        Handler(Looper.getMainLooper()).post {
            setValue(value)
        }
    }

    private fun pollValue() {
        super.postValue(values.poll())
    }

    @MainThread
    override fun setValue(t: T?) {
        mPending.set(true)
        super.setValue(t)
    }

    /**
     * Used for cases where T is Void, to make calls cleaner.
     */
    @Suppress("unused")
    @MainThread
    fun call() {
        value = null
    }
}