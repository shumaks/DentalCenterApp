package com.bsuir.dentalcenterapp.services

import com.bsuir.dentalcenterapp.models.*
import io.reactivex.Observable
import retrofit2.Call
import retrofit2.http.*

interface RetrofitServices {

    @GET("patients")
    fun getPatientsList(): Observable<ResponsePatientModel>

    @POST("patients")
    fun addPatient(@Body patient: PatientRequest): Call<Any>

    @DELETE
    fun deletePatient(@Url url: String): Observable<ResponseStatusPatientModel>

    @PATCH
    fun updatePatient(@Url url: String, @Body patient: PatientRequest): Observable<ResponseStatusPatientModel>

    @GET("appointments")
    fun getAppointmentsList(): Observable<AppointmentResponse>

    @POST("appointments")
    fun addAppointment(@Body appointment: AppointmentRequest): Call<Any>

    @PATCH
    fun updateAppointment(@Url url: String, @Body appointment: AppointmentRequest): Call<Any>

    @DELETE
    fun deleteAppointment(@Url url: String): Observable<Any>
}
