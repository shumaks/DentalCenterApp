package com.bsuir.dentalcenterapp.screens

import android.os.Bundle
import android.view.*
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bsuir.dentalcenterapp.models.AppointmentResponseData
import com.bsuir.dentalcenterapp.services.AppointmentsAdapter
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
        toolbar.title = "Журнал приёмов"
        (activity as MainActivity).setSupportActionBar(toolbar)

        recyclerView = root.findViewById(R.id.appointments_recycler_view)

        return root
    }

    override fun onResume() {
        super.onResume()

        recyclerView.layoutManager = LinearLayoutManager(activity)
        viewModel.observeAppointments()
        viewModel.appointmentsLiveData.observeForever {
            val appointments = it.sortByDate()
            recyclerView.adapter = AppointmentsAdapter(appointments)
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
            "01" -> "января"
            "02" -> "февраля"
            "03" -> "марта"
            "04" -> "апреля"
            "05" -> "мая"
            "06" -> "июня"
            "07" -> "июля"
            "08" -> "августа"
            "09" -> "сентября"
            "10" -> "октября"
            "11" -> "ноября"
            else -> "декабря"
        }
        return "$day $month"
    }
}