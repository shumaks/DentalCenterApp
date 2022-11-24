package com.bsuir.dentalcenterapp.models.doctor

import com.google.gson.annotations.SerializedName

data class DoctorAddRequest(
    @SerializedName("email")
    val email: String,
    @SerializedName("fullname")
    val fullname: String,
    @SerializedName("password")
    val password: String
)
