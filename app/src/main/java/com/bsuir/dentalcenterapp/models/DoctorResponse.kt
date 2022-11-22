package com.bsuir.dentalcenterapp.models

import com.google.gson.annotations.SerializedName

data class DoctorResponse(
    @SerializedName("status")
    val status: String,
    @SerializedName("data")
    val data: List<Doctor>
)
