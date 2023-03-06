package gil.sample.mvvm.service.data

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData

/**
 * A wrapper to replace a common pairing of [LiveData] and [MutableLiveData].
 * Intended to be exposed to observers as [LiveData] with [asLiveData] while internally
 * being mutable.
 */
class AliveData<T : Any>(value: T? = null) {

    private val mValueMutable: MutableLiveData<T> =
        value?.let { MutableLiveData(value) } ?: MutableLiveData()

    private val mValueLiveData: LiveData<T> = mValueMutable

    fun asLiveData(): LiveData<T> {
        return mValueLiveData
    }

    var value: T
        get() {
            return mValueLiveData.value as T
        }
        set(value) {
            mValueMutable.value = value
        }
}