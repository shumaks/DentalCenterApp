package com.bsuir.dentalcenterapp.models

import com.google.gson.annotations.SerializedName

data class DoctorLoginRequest(
    @SerializedName("email")
    val email: String,
    @SerializedName("password")
    val password: String
)
