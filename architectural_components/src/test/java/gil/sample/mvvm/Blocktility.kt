package gil.sample.mvvm

import android.util.Log
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.schedulers.Schedulers
import java.util.concurrent.Callable
import java.util.concurrent.TimeUnit
import java.util.concurrent.atomic.AtomicBoolean

class Blocktility private constructor(
    private val condition: Callable<Boolean>,
    private val blockData: Builder
) {
    private val LOG_TAG = Blocktility::class.java.simpleName

    private val completedCondition = AtomicBoolean(!condition.call())

    companion object {
        val DEFAULT_INTERVAL = 100L
        val DEFAULT_INTERVAL_TIMEUNIT = TimeUnit.MILLISECONDS
        val DEFAULT_TIMEOUT = Long.MAX_VALUE
        val DEFAULT_TIMEOUT_TIMEUNIT = TimeUnit.MILLISECONDS

        fun block(): Builder {
            return Builder()
        }
    }

    data class Builder(
        var interval: Long = DEFAULT_INTERVAL,
        var intervalTimeUnit: TimeUnit = DEFAULT_INTERVAL_TIMEUNIT,
        var timeout: Long = DEFAULT_TIMEOUT,
        var timeoutTimeUnit: TimeUnit = DEFAULT_TIMEOUT_TIMEUNIT
    ) {
        fun withInterval(interval: Long): Builder = apply { this.interval = interval }

        fun withInterval(interval: Long, timeUnit: TimeUnit): Builder = apply {
            this.interval = interval
            intervalTimeUnit = timeUnit
        }

        fun withIntervalTimeUnit(timeUnit: TimeUnit): Builder = apply {
            intervalTimeUnit = timeUnit
        }

        // TODO
        //fun withTimeout(timeout: Long) : Builder = apply { this.timeout = timeout }

        //fun withTimeout(timeout: Long, timeUnit: TimeUnit): Builder = apply {
        //    this.timeout = timeout
        //    timeoutTimeUnit = timeUnit
        //}

        //fun withTimeoutTimeUnit(timeoutTimeUnit: TimeUnit) : Builder = apply {
        //    this.timeoutTimeUnit = timeoutTimeUnit
        //}

        fun until(callable: Callable<Boolean>): Blocktility {
            val blocktility = Blocktility(callable, this)
            blocktility.execute()

            return blocktility
        }
    }

    private fun execute() {
        Observable
            .interval(blockData.interval, blockData.intervalTimeUnit)
            .subscribeOn(Schedulers.trampoline())
            .observeOn(Schedulers.trampoline())
            .takeWhile { !completedCondition() }
            .subscribe()
    }

    private fun completedCondition(): Boolean {
        return condition.call() == completedCondition.get()
    }

    private fun logTimeout(t: Throwable) {
        Log.w(
            LOG_TAG,
            "Blocktility [${blockData.timeout}, ${blockData.timeoutTimeUnit}] timed out: ${t.message}"
        )
    }
}