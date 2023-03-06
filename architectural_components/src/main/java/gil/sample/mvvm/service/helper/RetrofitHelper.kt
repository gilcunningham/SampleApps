package gil.sample.mvvm.service.helper

import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import gil.sample.mvvm.service.api.BaseApi
import gil.sample.mvvm.service.api.UserApiKt
import gil.sample.mvvm.service.api.UserApiRx
import java.util.concurrent.TimeUnit
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava3.RxJava3CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.moshi.MoshiConverterFactory
import timber.log.Timber

//TODO: combine moshi and okhttp
object RetrofitHelper {

    private val baseUrl = "https://gorest.co.in/"

    private val interceptor = HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY)

    private val okHttp = OkHttpClient.Builder()
        .callTimeout(10, TimeUnit.SECONDS)
        .connectTimeout(10, TimeUnit.SECONDS)
        .addNetworkInterceptor(interceptor)
        .addInterceptor(interceptor)
        .build()

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

    private val okInstance by lazy {
        Retrofit.Builder()
            .addCallAdapterFactory(RxJava3CallAdapterFactory.create())
            .addConverterFactory(GsonConverterFactory.create())
            .client(okHttp)
            .baseUrl(baseUrl)
            .build()
    }

    private val moshi by lazy {
        Moshi.Builder()
            .addLast(KotlinJsonAdapterFactory())
            .build()
    }

    private val moshiInstance: Retrofit by lazy {
        Retrofit.Builder()
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

    //combine retrofit instance
    //https://gist.github.com/Slowhand0309/bdab4bac812ca2aeedcceda268a8f701

    fun <T : BaseApi> instance(api: Class<T>): T {
        //TODO- work on better method
        if (api.isAssignableFrom(UserApiKt::class.java)) {
            Timber.d("instance UserApiKt")
            return moshiInstance.create(api)
        } else if (api.isAssignableFrom(UserApiRx::class.java)) {
            Timber.d("instance UserApiRx")
            return okInstance.create(api)
        }

        throw NotImplementedError("$api is not supported, please add")
    }
}