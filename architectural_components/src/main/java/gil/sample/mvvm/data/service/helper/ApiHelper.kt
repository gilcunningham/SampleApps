package gil.sample.mvvm.data.service.helper

import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import gil.sample.mvvm.data.service.api.BaseApi
import gil.sample.mvvm.data.service.api.UserApiCr
import gil.sample.mvvm.data.service.api.UserApiRx
import java.util.concurrent.TimeUnit
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava3.RxJava3CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.moshi.MoshiConverterFactory
import timber.log.Timber

/*
 * Helper to retrieve a Retrofit instance based on Api.
 */
object ApiHelper {

    // TODO: move to BaseA[i
    private val baseUrl = "https://gorest.co.in/"

    private val interceptor = HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY)

    private val okHttp = OkHttpClient.Builder()
        .callTimeout(10, TimeUnit.SECONDS)
        .connectTimeout(10, TimeUnit.SECONDS)
        .addNetworkInterceptor(interceptor)
        //.addInterceptor(interceptor)
        .build()

    private val okInstance by lazy {
        Retrofit.Builder()
            .addCallAdapterFactory(RxJava3CallAdapterFactory.create())
            .addConverterFactory(GsonConverterFactory.create())
            .client(okHttp)
            .baseUrl(baseUrl)
            .build()
    }

    private fun okInstance(url: String): Retrofit {
        val instance by lazy {
            Retrofit.Builder()
                .addCallAdapterFactory(RxJava3CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .client(okHttp)
                .baseUrl(url)
                .build()
        }
        return instance
    }

    private val moshi by lazy {
        Moshi.Builder()
            .addLast(KotlinJsonAdapterFactory())
            .build()
    }

    private val moshiInstance: Retrofit by lazy {
        Retrofit.Builder()
            .addCallAdapterFactory(RxJava3CallAdapterFactory.create())
            .addConverterFactory(MoshiConverterFactory.create(moshi))
            .baseUrl(baseUrl)
            .build()
    }

    private fun moshiInstance(url: String): Retrofit {
        val instance by lazy {
            Retrofit.Builder()
                .addConverterFactory(MoshiConverterFactory.create(moshi))
                .baseUrl(url)
                .build()
        }
        return instance
    }

    // just an exercise to provide multiple implementations of Retrofit.
    // mostly interested to see how Moshi works
    fun <T : BaseApi> instance(api: Class<T>): T {
        if (api.isAssignableFrom(UserApiCr::class.java)) {
            Timber.d("instance UserApiCr")
            return moshiInstance.create(api)
        } else if (api.isAssignableFrom(UserApiRx::class.java)) {
            Timber.d("instance UserApiRx")
            return okInstance.create(api)
        }

        throw NotImplementedError("$api is not supported, please add")
    }
}