package com.bsuir.dentalcenterapp.screens

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.bsuir.dentalcenterapp.models.appointment.AppointmentRequest
import com.bsuir.dentalcenterapp.models.appointment.AppointmentResponseData
import com.bsuir.dentalcenterapp.models.doctor.DoctorAddRequest
import com.bsuir.dentalcenterapp.models.doctor.DoctorLoginRequest
import com.bsuir.dentalcenterapp.models.patient.Patient
import com.bsuir.dentalcenterapp.models.patient.PatientRequest
import com.bsuir.dentalcenterapp.repo.AppointmentsRepo
import com.bsuir.dentalcenterapp.repo.PatientsRepo
import com.bsuir.dentalcenterapp.services.RetrofitClient
import com.bsuir.dentalcenterapp.services.RetrofitServices
import io.reactivex.schedulers.Schedulers

object MainViewModel : ViewModel() {
    const val BASE_URL = "http://localhost:6666/"

    private val retrofitService = RetrofitClient.getClient(BASE_URL).create(RetrofitServices::class.java)
    private val appointmentsRepo = AppointmentsRepo()
    private val patientsRepo = PatientsRepo()
    val appointmentsLiveData = MutableLiveData<List<AppointmentResponseData>>()
    val patientsLiveData = MutableLiveData<List<Patient>>()

    fun observePatients() = patientsRepo.observePatients().subscribe {
        patientsLiveData.postValue(it.patients)
    }

    fun getPatients() = patientsRepo.getPatients().patients

    fun addPatient(body: PatientRequest) = retrofitService.addPatient(body)

    fun deletePatient(id: String) = retrofitService.deletePatient("patients/$id")

    fun updatePatient(id: String, body: PatientRequest) = retrofitService.updatePatient("patients/$id", body)

    fun observeAppointments() = appointmentsRepo.observeAppointments().subscribe {
        appointmentsLiveData.postValue(it.data)
    }

    fun getAppointments() = appointmentsRepo.getAppointments().data

    fun addAppointment(body: AppointmentRequest) = retrofitService.addAppointment(body)

    fun updateAppointment(id: String, body: AppointmentRequest) = retrofitService.updateAppointment("appointments/$id", body)

    fun deleteAppointment(id: String) = retrofitService.deleteAppointment("appointments/$id").subscribeOn(Schedulers.io()).subscribe()

    fun addDoctor(doctor: DoctorAddRequest) = retrofitService.addDoctor(doctor)

    fun loginDoctor(doctor: DoctorLoginRequest) = retrofitService.loginDoctor(doctor)
}
