package com.bsuir.dentalcenterapp.models

import com.google.gson.annotations.SerializedName

data class Patient(
    @SerializedName("_id")
    val id: String,
    @SerializedName("fullname")
    val fullName: String,
    @SerializedName("phone")
    val phone: String,
    @SerializedName("createdAt")
    val createdAt: String,
    @SerializedName("updatedAt")
    val updatedAt: String,
    @SerializedName("__v")
    val v: Int
)
