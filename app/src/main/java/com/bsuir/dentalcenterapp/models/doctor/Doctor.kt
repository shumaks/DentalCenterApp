package com.bsuir.dentalcenterapp.models.doctor

import com.google.gson.annotations.SerializedName

data class Doctor(
    @SerializedName("_id")
    val id: String,
    @SerializedName("email")
    val email: String,
    @SerializedName("fullname")
    val fullname: String,
    @SerializedName("password")
    val password: String,
    @SerializedName("__v")
    val v: Int
)

