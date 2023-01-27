package com.example.sampleapplication.service

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitHelper {

    val baseUrl = "https://gorest.co.in/"

    val interceptor = HttpLoggingInterceptor()
        .setLevel(HttpLoggingInterceptor.Level.BODY)

    val okHttp = OkHttpClient.Builder()
        .addInterceptor(interceptor).build();

    fun getInstance() : Retrofit {
        return Retrofit.Builder()
            .client(okHttp)
            .baseUrl(baseUrl)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }
}