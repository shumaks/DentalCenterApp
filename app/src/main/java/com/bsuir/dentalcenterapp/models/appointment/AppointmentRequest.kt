package com.bsuir.dentalcenterapp.models.appointment

import com.google.gson.annotations.SerializedName

data class AppointmentRequest(
    @SerializedName("patient")
    val patientId: String,
    @SerializedName("doctor")
    val doctorId: String,
    @SerializedName("dents")
    val dentNumber: List<Tooth>,
    @SerializedName("diagnosis")
    val diagnosis: String,
    @SerializedName("price")
    val price: Int,
    @SerializedName("date")
    val date: String,
    @SerializedName("time")
    val time: String
)
