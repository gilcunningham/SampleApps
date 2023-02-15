package com.gil.sample.service.api

interface BaseApi {

    val baseUrl: String
        get() = baseUrlImpl()

    fun baseUrlImpl(): String {
        return "https://gorest.co.in/"
    }
}