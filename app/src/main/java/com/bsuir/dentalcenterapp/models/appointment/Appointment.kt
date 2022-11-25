package com.bsuir.dentalcenterapp.models.appointment

import com.bsuir.dentalcenterapp.models.patient.Patient
import com.google.gson.annotations.SerializedName

data class Appointment(
    @SerializedName("_id")
    val id: String,
    @SerializedName("patient")
    val patient: Patient,
    @SerializedName("dents")
    val dentNumber: List<Tooth>,
    @SerializedName("diagnosis")
    val diagnosis: String,
    @SerializedName("price")
    val price: Int,
    @SerializedName("date")
    val date: String,
    @SerializedName("time")
    val time: String,
    @SerializedName("doctor")
    val doctor: String,
    @SerializedName("createdAt")
    val createdAt: String,
    @SerializedName("updatedAt")
    val updatedAt: String,
    @SerializedName("__v")
    val v: Int
)
