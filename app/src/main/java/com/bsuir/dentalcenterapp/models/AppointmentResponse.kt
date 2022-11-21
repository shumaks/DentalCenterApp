package com.bsuir.dentalcenterapp.models

import com.google.gson.annotations.SerializedName

data class AppointmentResponse(
    @SerializedName("status")
    val status: String,
    @SerializedName("data")
    val data: List<AppointmentResponseData>
)

data class AppointmentResponseData(
    @SerializedName("title")
    val title: String,
    @SerializedName("data")
    val data: List<Appointment>
)
