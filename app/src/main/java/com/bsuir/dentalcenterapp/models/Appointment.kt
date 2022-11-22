package com.bsuir.dentalcenterapp.models

import com.google.gson.annotations.SerializedName

data class Appointment(
    @SerializedName("_id")
    val id: String,
    @SerializedName("patient")
    val patient: Patient,
    @SerializedName("dentNumber")
    val dentNumber: Int,
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
