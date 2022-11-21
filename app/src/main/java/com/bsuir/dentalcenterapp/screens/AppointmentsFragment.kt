package com.bsuir.dentalcenterapp.screens

import android.os.Bundle
import android.view.*
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bsuir.dentalcenterapp.models.AppointmentModel
import com.bsuir.dentalcenterapp.services.AppointmentsAdapter
import com.itexus.dentalcenterapp.R

class AppointmentsFragment : Fragment() {

    private val viewModel: MainViewModel = MainViewModel()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val root = inflater.inflate(R.layout.fragment_appointments, container, false) as ViewGroup
        val toolbar: Toolbar = root.findViewById(R.id.toolbar) as Toolbar
        toolbar.title = "Журнал приёмов"
        (activity as MainActivity).setSupportActionBar(toolbar)

        val recyclerView: RecyclerView = root.findViewById(R.id.appointments_recycler_view)
        recyclerView.layoutManager = LinearLayoutManager(activity)
        val appointments = viewModel.getAppointments()
        println(appointments)
        val appointmentsList = mutableListOf<AppointmentModel>()
        appointments.forEach { response ->
            appointmentsList.addAll(response.data.map {
                with(it) {
                    AppointmentModel(
                        id,
                        patient.id,
                        patient.fullName,
                        diagnosis,
                        response.title,
                        time
                    )
                }
            })
        }
        recyclerView.adapter = AppointmentsAdapter(appointmentsList)

        return root
    }
}