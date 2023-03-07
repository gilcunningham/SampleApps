package gil.sample.mvvm

import android.app.Application
import timber.log.Timber
import timber.log.Timber.DebugTree

class SampleMvvmApp: Application() {

    override fun onCreate() {
        super.onCreate()

        if (BuildConfig.DEBUG) {
            Timber.plant(DebugTree())
        }
    }
}