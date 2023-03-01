package com.gil.sample.service.data

import androidx.annotation.MainThread
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer

/**
 * A wrapper to replace a common pairing of [LiveData] and [MutableLiveData].
 * Intended to be exposed to observers as [LiveData] with [asLiveData] while internally
 * being mutable.
 */
class AliveData<T: Any>(value: T? = null) {

    private val mValueMutable: MutableLiveData<T> =
        value?.let { MutableLiveData(value) } ?: MutableLiveData()

    private val mValueLiveData : LiveData<T> = mValueMutable

    fun asLiveData() : LiveData<T> {
        return mValueLiveData
    }

    var value: T
        get() {
            return mValueLiveData.value as T
        }
        set(value) {
            mValueMutable.value = value
        }

    //TODO - can we inherit docs from LiveData
    /**
     * @see LiveData.observe
     * @param owner    The LifecycleOwner which controls the observer
     * @param observer The observer that will receive the events
     */
    @MainThread
    fun observe(
        owner: LifecycleOwner,
        observer : Observer<T>) {

        mValueLiveData.observe(owner, observer)
    }

    /**
     * @see LiveData.observeForever
     * @param observer The observer that will receive the events
     */
    @MainThread
    fun observeForever(observer: Observer<T>) {
        mValueLiveData.observeForever(observer)
    }

    /**
     * @see LiveData.removeObserver
     * @param observer The Observer to receive events.
     */
    @MainThread
    fun removeObserver(observer: Observer<T>) {
        mValueLiveData.removeObserver(observer)
    }

    /**
     * @see LiveData.removeObservers
     * @param owner The {@code LifecycleOwner} scope for the observers to be removed.
     */
    @SuppressWarnings("WeakerAccess")
    @MainThread
    fun removeObservers(owner: LifecycleOwner) {
        mValueLiveData.removeObservers(owner)
    }
}