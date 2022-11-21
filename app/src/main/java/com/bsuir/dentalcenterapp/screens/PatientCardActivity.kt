package com.bsuir.dentalcenterapp.screens

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.*
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bsuir.dentalcenterapp.models.Appointment
import com.bsuir.dentalcenterapp.services.PatientsAppointmentsAdapter
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.itexus.dentalcenterapp.R


class PatientCardActivity : AppCompatActivity() {

    private val viewModel = MainViewModel()
    private lateinit var recyclerView: RecyclerView
    private var id: String? = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_patient_card)

        val toolbar: Toolbar = findViewById(R.id.toolbar)
        toolbar.title = "Карта пациента"
        setSupportActionBar(toolbar)

        id = intent.getStringExtra("id")
        val patient = viewModel.getPatients().first { it.id == id }

        val fullNameTextView: TextView = findViewById(R.id.fullname)
        val phone: TextView = findViewById(R.id.phone)
        val buttonCall: ImageView = findViewById(R.id.button_call)
        recyclerView = findViewById(R.id.recyclerViewPatientAppointment)
        val buttonAddAppointment: FloatingActionButton = findViewById(R.id.add_appointment_button)
        val buttonFormula: Button = findViewById(R.id.buttonFormula)

        fullNameTextView.text = patient.fullName
        phone.text = patient.phone
        buttonCall.setOnClickListener {
            val intent = Intent(Intent.ACTION_DIAL, Uri.parse("tel:${patient.phone}"))
            startActivity(intent)
        }
        buttonAddAppointment.setOnClickListener {
            val intent = Intent(this, AddAppointmentActivity::class.java)
            intent.putExtra("id", id)
            startActivity(intent, null)
        }
        buttonFormula.setOnClickListener {
            val intent = Intent(this, FormulaActivity::class.java)
            intent.putExtra("id", id)
            startActivity(intent, null)
        }
    }

    override fun onResume() {
        super.onResume()

        recyclerView.layoutManager = LinearLayoutManager(this)
        val appointments = mutableListOf<Appointment>()
        viewModel.getAppointments().forEach {
            it.data.forEach {
                if (it.patient.id == id) {
                    appointments.add(it)
                }
            }
        }

        recyclerView.adapter = PatientsAppointmentsAdapter(appointments)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.toolbar_patients_items, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem) = when (item.itemId) {
        R.id.action_back -> {
            onBackPressed()
            true
        }

        else -> {
            super.onOptionsItemSelected(item)
        }
    }
}
