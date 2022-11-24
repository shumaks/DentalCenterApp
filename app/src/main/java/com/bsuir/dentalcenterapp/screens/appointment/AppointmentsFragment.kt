package com.bsuir.dentalcenterapp.screens.appointment

import android.content.Intent
import android.os.Bundle
import android.view.*
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bsuir.dentalcenterapp.App
import com.bsuir.dentalcenterapp.models.appointment.Appointment
import com.bsuir.dentalcenterapp.models.appointment.AppointmentResponseData
import com.bsuir.dentalcenterapp.screens.MainViewModel
import com.bsuir.dentalcenterapp.adapters.AppointmentsAdapter
import com.bsuir.dentalcenterapp.adapters.AppointmentsByDateAdapter
import com.itexus.dentalcenterapp.R
import java.time.LocalDate
import java.time.format.DateTimeFormatter

class AppointmentsFragment : Fragment() {

    private val viewModel: MainViewModel = MainViewModel
    private lateinit var recyclerView: RecyclerView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val root = inflater.inflate(R.layout.fragment_appointments, container, false) as ViewGroup
        val toolbar: Toolbar = root.findViewById(R.id.toolbar) as Toolbar
        toolbar.title = getString(R.string.appointments_log)
        (activity as AppointmentActivity).setSupportActionBar(toolbar)

        recyclerView = root.findViewById(R.id.appointments_recycler_view)

        return root
    }

    override fun onResume() {
        super.onResume()

        recyclerView.layoutManager = LinearLayoutManager(activity)
        MainViewModel.observeAppointments()

        val onEditClickListener: AppointmentsByDateAdapter.OnEditClickListener =
            object : AppointmentsByDateAdapter.OnEditClickListener {
                override fun onClick(appointment: Appointment) {
                    val intent = Intent(activity, EditAppointmentActivity::class.java)
                    intent.putExtra("id", appointment.id)
                    startActivity(intent, null)
                }
            }
        val onDeleteClickListener: AppointmentsByDateAdapter.OnDeleteClickListener =
            object : AppointmentsByDateAdapter.OnDeleteClickListener {
                override fun onClick(appointment: Appointment) {
                    MainViewModel.deleteAppointment(appointment.id)
                    MainViewModel.observeAppointments()
                }
            }

        MainViewModel.appointmentsLiveData.observe(this) {
            val filteredAppointments = mutableListOf<AppointmentResponseData>()
            it.sortByDate().forEach { response ->
                response.copy(data = response.data.filter {
                    it.doctor == App.currentDoctor.id
                }).let {
                    if (it.data.isNotEmpty()) {
                        filteredAppointments.add(response)
                    }
                }
            }
            recyclerView.adapter = AppointmentsAdapter(filteredAppointments, onEditClickListener, onDeleteClickListener)
        }
    }

    private fun List<AppointmentResponseData>.sortByDate(): List<AppointmentResponseData> {
        val dateTimeFormatter: DateTimeFormatter = DateTimeFormatter.ofPattern("dd-MM-yyyy")
        return this.map { it.copy(title = it.title.toDate()) }.sortedByDescending {
            LocalDate.parse(it.title, dateTimeFormatter)
        }.map { it.copy(it.title.toStringDate()) }
    }

    private fun String.toDate(): String {
        val splittedString = this.split(" ")
        val day = if (splittedString.first().length == 1) {
            "0${splittedString.first()}"
        } else {
            splittedString.first()
        }
        val month = when (splittedString[1]) {
            "января" -> "01"
            "февраля" -> "02"
            "марта" -> "03"
            "апреля" -> "04"
            "мая" -> "05"
            "июня" -> "06"
            "июля" -> "07"
            "августа" -> "08"
            "сентября" -> "09"
            "октября" -> "10"
            "ноября" -> "11"
            else -> "12"
        }
        return "$day-$month-2022"
    }

    private fun String.toStringDate(): String {
        val splittedString = this.dropLast(5).split("-")
        val day = if (splittedString.first().first() == '0') {
            splittedString.first().drop(1)
        } else {
            splittedString.first()
        }
        val month = when (splittedString[1]) {
            "01" -> getString(R.string.january)
            "02" -> getString(R.string.february)
            "03" -> getString(R.string.march)
            "04" -> getString(R.string.april)
            "05" -> getString(R.string.may)
            "06" -> getString(R.string.june)
            "07" -> getString(R.string.july)
            "08" -> getString(R.string.august)
            "09" -> getString(R.string.september)
            "10" -> getString(R.string.october)
            "11" -> getString(R.string.november)
            else -> getString(R.string.december)
        }
        return "$day $month"
    }
}