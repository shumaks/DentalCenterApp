package com.bsuir.dentalcenterapp.screens

import androidx.lifecycle.ViewModel
import com.bsuir.dentalcenterapp.models.AppointmentRequest
import com.bsuir.dentalcenterapp.models.PatientRequest
import com.bsuir.dentalcenterapp.repo.AppointmentsRepo
import com.bsuir.dentalcenterapp.services.RetrofitClient
import com.bsuir.dentalcenterapp.services.RetrofitServices

class MainViewModel : ViewModel() {
    private val retrofitService = RetrofitClient.getClient(BASE_URL).create(RetrofitServices::class.java)
    private val appointmentsRepo = AppointmentsRepo()

    fun getPatients() = retrofitService.getPatientsList()

    fun addPatient(body: PatientRequest) = retrofitService.addPatient(body)

    fun deletePatient(id: String) = retrofitService.deletePatient("patients/$id")

    fun updatePatient(id: String, body: PatientRequest) = retrofitService.updatePatient("patients/$id", body)

    fun getAppointments() = appointmentsRepo.getAppointments()

    fun addAppointment(body: AppointmentRequest) = retrofitService.addAppointment(body)

    companion object {
        const val BASE_URL = "http://localhost:6666/"
    }
}
