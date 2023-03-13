package gil.sample.mvvm.data.model

import com.google.gson.annotations.SerializedName
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class User(

    @Json(name = "id") // moshi
    @SerializedName("id") // okhttp
    val id: Int,

    @Json(name = "name")
    @SerializedName("name")
    val name: String,

    @Json(name = "email")
    @SerializedName("email")
    val email: String,

    @Json(name = "gender")
    @SerializedName("gender")
    val gender: String,

    @Json(name = "status")
    @SerializedName("status")
    val status: String) {
}

