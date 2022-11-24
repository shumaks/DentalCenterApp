package com.bsuir.dentalcenterapp.services

import com.bsuir.dentalcenterapp.models.*
import com.bsuir.dentalcenterapp.models.appointment.AppointmentRequest
import com.bsuir.dentalcenterapp.models.appointment.AppointmentResponse
import com.bsuir.dentalcenterapp.models.doctor.DoctorAddRequest
import com.bsuir.dentalcenterapp.models.doctor.DoctorLoginRequest
import com.bsuir.dentalcenterapp.models.doctor.DoctorLoginResponse
import com.bsuir.dentalcenterapp.models.patient.PatientRequest
import com.bsuir.dentalcenterapp.models.patient.ResponsePatientModel
import io.reactivex.Observable
import retrofit2.Call
import retrofit2.http.*

interface RetrofitServices {

    @GET("patients")
    fun getPatientsList(): Observable<ResponsePatientModel>

    @POST("patients")
    fun addPatient(@Body patient: PatientRequest): Call<Any>

    @DELETE
    fun deletePatient(@Url url: String): Call<Any>

    @PATCH
    fun updatePatient(@Url url: String, @Body patient: PatientRequest): Call<Any>

    @GET("appointments")
    fun getAppointmentsList(): Observable<AppointmentResponse>

    @POST("appointments")
    fun addAppointment(@Body appointment: AppointmentRequest): Call<Any>

    @PATCH
    fun updateAppointment(@Url url: String, @Body appointment: AppointmentRequest): Call<Any>

    @DELETE
    fun deleteAppointment(@Url url: String): Observable<Any>

    @POST("doctors")
    fun addDoctor(@Body doctor: DoctorAddRequest): Call<DoctorLoginResponse>

    @POST("doctors/login")
    fun loginDoctor(@Body doctor: DoctorLoginRequest): Call<DoctorLoginResponse>
}
