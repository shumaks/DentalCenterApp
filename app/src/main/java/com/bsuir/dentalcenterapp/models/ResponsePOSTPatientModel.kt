package com.bsuir.dentalcenterapp.models

import com.google.gson.annotations.SerializedName

data class ResponsePOSTPatientModel(
    @SerializedName("status")
    val status: String,
    @SerializedName("data")
    val patient: Patient
)
