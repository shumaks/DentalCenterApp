package com.bsuir.dentalcenterapp.models

import com.google.gson.annotations.SerializedName

data class PatientRequest(
    @SerializedName("fullname")
    val fullname: String,
    @SerializedName("phone")
    val phone: String
)
