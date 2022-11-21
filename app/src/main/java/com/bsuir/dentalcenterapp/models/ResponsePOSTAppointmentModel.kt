package com.bsuir.dentalcenterapp.models

import com.google.gson.annotations.SerializedName

data class ResponsePOSTAppointmentModel(
    @SerializedName("success")
    val success: Boolean,
    @SerializedName("data")
    val data: Appointment
)
