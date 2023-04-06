package gil.sample.mvvm

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Scheduler
import io.reactivex.rxjava3.schedulers.Schedulers
import io.reactivex.rxjava3.schedulers.TestScheduler

/**
 * A [Schedulers] provider interface.
 * Useful for injecting a use case dependent Scheduler.
 */
interface SchedulersProvider {
    fun io(): Scheduler
    fun ui(): Scheduler
    fun computation(): Scheduler
}

/**
 * Module provider.
 */
@Module
@InstallIn(SingletonComponent::class)
abstract class SchedulersProviderModule {
    @Binds
    abstract fun bindsSchedulersProvider(
        schedulersProvider: SchedulersProviderImpl
    ) : SchedulersProvider
}

//TODO: details for passing test param
class SchedulersProviderImpl(isTest: Boolean = false) : SchedulersProvider {

    var scheduler: SchedulersProvider = if (isTest) {
        println("*** IS TEST")
        object : SchedulersProvider {
            val scheduler = TestScheduler()
            override fun io() = scheduler
            override fun ui() = scheduler
            override fun computation() = scheduler
        }
    } else {
        println("*** IS NOT TEST")
        object : SchedulersProvider {
            override fun io() = Schedulers.io()
            override fun ui() = AndroidSchedulers.mainThread()
            override fun computation() = Schedulers.computation()
        }
    }

    override fun io() = scheduler.io()
    override fun ui() = scheduler.ui()
    override fun computation() = scheduler.computation()
}