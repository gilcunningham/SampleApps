package gil.sample.mvvm.data.service.api

interface BaseApi {

    //TODO: incorporate this into ApiHelper
    val baseUrl: String
        get() = baseUrlImpl()

    fun baseUrlImpl(): String {
        return "https://gorest.co.in/"
    }
}