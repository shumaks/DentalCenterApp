package com.bsuir.dentalcenterapp.repo

import com.bsuir.dentalcenterapp.models.ResponsePatientModel
import com.bsuir.dentalcenterapp.screens.MainViewModel
import com.bsuir.dentalcenterapp.services.RetrofitClient
import com.bsuir.dentalcenterapp.services.RetrofitServices
import io.reactivex.Observable
import io.reactivex.schedulers.Schedulers

class PatientsRepo {
    private val retrofitService = RetrofitClient.getClient(MainViewModel.BASE_URL).create(RetrofitServices::class.java)
    private lateinit var response: ResponsePatientModel

    fun observePatients(): Observable<ResponsePatientModel> = retrofitService.getPatientsList().subscribeOn(Schedulers.io()).doOnNext {
        response = it
    }

    fun getPatients() = response
}