package com.bsuir.dentalcenterapp.models

import com.google.gson.annotations.SerializedName

data class ResponsePatientModel(
    @SerializedName("status")
    val status: String,
    @SerializedName("data")
    val patients: List<Patient>
)
