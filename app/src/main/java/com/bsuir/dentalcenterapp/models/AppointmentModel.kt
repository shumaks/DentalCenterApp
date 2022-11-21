package com.bsuir.dentalcenterapp.models

data class AppointmentModel(
    val id: String,
    val patientId: String,
    val fullname: String,
    val diagnosis: String,
    val date: String,
    val time: String
)