package com.bsuir.dentalcenterapp.models

import com.google.gson.annotations.SerializedName

data class ResponseStatusPatientModel(
    @SerializedName("status")
    val status: String
)