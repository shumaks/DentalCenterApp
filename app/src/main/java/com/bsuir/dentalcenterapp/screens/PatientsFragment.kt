package com.bsuir.dentalcenterapp.screens

import android.content.Intent
import android.os.Bundle
import android.view.*
import android.widget.SearchView
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bsuir.dentalcenterapp.models.Patient
import com.bsuir.dentalcenterapp.services.PatientsAdapter
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.itexus.dentalcenterapp.R


class PatientsFragment : Fragment() {

    private val viewModel: MainViewModel = MainViewModel
    private lateinit var recyclerView: RecyclerView
    private lateinit var searchView: SearchView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        val root = inflater.inflate(R.layout.fragment_patients, container, false) as ViewGroup
        val toolbar: Toolbar = root.findViewById(R.id.toolbar) as Toolbar
        toolbar.title = "Пациенты"
        (activity as PatientsActivity).setSupportActionBar(toolbar)

        val addAppointmentButton = root.findViewById<FloatingActionButton>(R.id.add_patient_button)
        addAppointmentButton.setOnClickListener {
            startActivity(Intent(activity, AddPatientActivity::class.java), null)
        }

        recyclerView = root.findViewById(R.id.patients_recycler_view)
        searchView = root.findViewById(R.id.search_view)

        return root
    }

    override fun onResume() {
        super.onResume()

        recyclerView.layoutManager = LinearLayoutManager(activity)
        viewModel.observePatients()
        val onPatientClickListener: PatientsAdapter.OnPatientClickListener = object : PatientsAdapter.OnPatientClickListener {
            override fun onClick(patient: Patient) {
                val intent = Intent(activity, PatientCardActivity::class.java)
                intent.putExtra("id", patient.id)
                startActivity(intent, null)
            }
        }

        viewModel.patientsLiveData.observeForever {
            val adapter = PatientsAdapter(it.toMutableList(), onPatientClickListener)

            searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
                override fun onQueryTextSubmit(query: String?): Boolean {
                    adapter.getFilter().filter(query)
                    return true
                }

                override fun onQueryTextChange(newText: String?): Boolean {
                    adapter.getFilter().filter(newText);
                    return true
                }

            })

            recyclerView.adapter = adapter
        }
    }
}