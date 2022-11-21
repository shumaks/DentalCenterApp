package com.bsuir.dentalcenterapp.screens

import android.os.Bundle
import android.view.*
import androidx.appcompat.widget.Toolbar
import androidx.core.app.ActivityCompat.invalidateOptionsMenu
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bsuir.dentalcenterapp.services.AppointmentsAdapter
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.itexus.dentalcenterapp.R

class AppointmentsFragment : Fragment() {

    private val viewModel: MainViewModel = MainViewModel()
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
        val appointments = viewModel.getAppointments()
        recyclerView.adapter = AppointmentsAdapter(appointments)
    }
}