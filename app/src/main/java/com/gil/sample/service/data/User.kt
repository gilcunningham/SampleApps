package com.gil.sample.service.data

import com.google.gson.annotations.SerializedName

data class User(
    @SerializedName("id") val id: Int,
    @SerializedName("name") val name: String,
    @SerializedName("email") val email: String,
    @SerializedName("gender") val gender: String,
    @SerializedName("status") val status: String) {

    override fun toString(): String {
        return "id: $id name: $name email: $email gender: $gender status: $status"
    }
}

