package com.bsuir.dentalcenterapp.repo

import com.bsuir.dentalcenterapp.models.AppointmentResponseData
import com.bsuir.dentalcenterapp.models.Patient
import com.bsuir.dentalcenterapp.screens.MainViewModel
import com.bsuir.dentalcenterapp.services.RetrofitClient
import com.bsuir.dentalcenterapp.services.RetrofitServices
import io.reactivex.schedulers.Schedulers

class PatientsRepo {
    private var patientsList = listOf<Patient>()
    private val retrofitService = RetrofitClient.getClient(MainViewModel.BASE_URL).create(RetrofitServices::class.java)

    fun getPatients(): List<Patient> {
        retrofitService.getPatientsList().subscribeOn(Schedulers.io()).subscribe {
            patientsList = it.patients
        }

        Thread.sleep(1000L)

        return patientsList
    }
}