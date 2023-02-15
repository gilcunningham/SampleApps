package com.gil.sample.service.data

import com.google.gson.annotations.SerializedName
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class User(

    @Json(name = "id")
    //@SerializedName("id")
    val id: Int,

    @Json(name = "name")
    //@SerializedName("name")
    val name: String,

    @Json(name = "email")
    //@SerializedName("email")
    val email: String,

    @Json(name = "gender")
    //@SerializedName("gender")
    val gender: String,

    @Json(name = "status")
    //@SerializedName("status")
    val status: String) {

    //override fun toString(): String {
    //    return "id: $id name: $name email: $email gender: $gender status: $status"
    //}
}

