package com.bsuir.dentalcenterapp.repo

import com.bsuir.dentalcenterapp.models.AppointmentResponseData
import com.bsuir.dentalcenterapp.screens.MainViewModel
import com.bsuir.dentalcenterapp.services.RetrofitClient
import com.bsuir.dentalcenterapp.services.RetrofitServices
import io.reactivex.schedulers.Schedulers

class AppointmentsRepo {
    private var appointmentsList = listOf<AppointmentResponseData>()
    private val retrofitService = RetrofitClient.getClient(MainViewModel.BASE_URL).create(RetrofitServices::class.java)

    fun getAppointments(): List<AppointmentResponseData> {
        retrofitService.getAppointmentsList().subscribeOn(Schedulers.io()).subscribe {
            appointmentsList = it.data
        }

        Thread.sleep(1000L)

        return appointmentsList
    }
}