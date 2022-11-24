package com.bsuir.dentalcenterapp.models.doctor

import com.google.gson.annotations.SerializedName

data class DoctorLoginResponse(
    @SerializedName("status")
    val status: String,
    @SerializedName("data")
    val data: Doctor
)
