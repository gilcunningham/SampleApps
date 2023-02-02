package com.gil.sample.service

import java.util.concurrent.TimeUnit
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava3.RxJava3CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitHelper {

    val baseUrl = "https://gorest.co.in/"

    val interceptor = HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY)

    val okHttp = OkHttpClient.Builder()
        .callTimeout(10, TimeUnit.SECONDS)
        .connectTimeout(10, TimeUnit.SECONDS)
        .addNetworkInterceptor(interceptor)
        .addInterceptor(interceptor)
        .build()

    fun getInstance() : Retrofit {
        return Retrofit.Builder()
            .addCallAdapterFactory(
                RxJava3CallAdapterFactory.create()
            )
            //.addConverterFactory(
            //    MoshiConverterFactory.create()
            //)
            .addConverterFactory(GsonConverterFactory.create())
            .client(okHttp)
            .baseUrl(baseUrl)
            .build()
    }
}