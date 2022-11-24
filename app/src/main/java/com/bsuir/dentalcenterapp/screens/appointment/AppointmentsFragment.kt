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
            recyclerView.adapter = AppointmentsAdapter(
                filteredAppointments,
                onEditClickListener,
                onDeleteClickListener
            )
        }
    }

    private fun List<AppointmentResponseData>.sortByDate(): List<AppointmentResponseData> =
        this.sortedByDescending {
            it.title.length
        }
}