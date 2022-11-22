package com.bsuir.dentalcenterapp.repo

import com.bsuir.dentalcenterapp.models.AppointmentResponse
import com.bsuir.dentalcenterapp.models.AppointmentResponseData
import com.bsuir.dentalcenterapp.screens.MainViewModel
import com.bsuir.dentalcenterapp.services.RetrofitClient
import com.bsuir.dentalcenterapp.services.RetrofitServices
import io.reactivex.Completable
import io.reactivex.Observable
import io.reactivex.schedulers.Schedulers

class AppointmentsRepo {
    private lateinit var response: AppointmentResponse
    private val retrofitService =
        RetrofitClient.getClient(MainViewModel.BASE_URL).create(RetrofitServices::class.java)

    fun observeAppointments(): Observable<AppointmentResponse> =
        retrofitService.getAppointmentsList().subscribeOn(Schedulers.io()).doOnNext {
            response = it
        }

    fun getAppointments() = response
}