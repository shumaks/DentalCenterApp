package com.bsuir.dentalcenterapp.screens

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.*
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bsuir.dentalcenterapp.App
import com.bsuir.dentalcenterapp.models.Appointment
import com.bsuir.dentalcenterapp.models.Patient
import com.bsuir.dentalcenterapp.services.PatientsAppointmentsAdapter
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.itexus.dentalcenterapp.R
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class PatientCardActivity : AppCompatActivity() {

    private val viewModel = MainViewModel
    private lateinit var recyclerView: RecyclerView
    private lateinit var fullNameTextView: TextView
    private lateinit var phone: TextView
    private lateinit var buttonCall: ImageView
    private var id: String? = ""
    private lateinit var patient: Patient

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_patient_card)

        val toolbar: Toolbar = findViewById(R.id.toolbar)
        toolbar.title = "Карта пациента"
        setSupportActionBar(toolbar)

        id = intent.getStringExtra("id")

        fullNameTextView = findViewById(R.id.fullname)
        phone = findViewById(R.id.phone)
        recyclerView = findViewById(R.id.recyclerViewPatientAppointment)
        buttonCall = findViewById(R.id.button_call)
        val buttonAddAppointment: FloatingActionButton = findViewById(R.id.add_appointment_button)
        val buttonFormula: Button = findViewById(R.id.buttonFormula)
        val buttonEdit: Button = findViewById(R.id.buttonEdit)
        val buttonDelete: Button = findViewById(R.id.buttonDelete)

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
        buttonEdit.setOnClickListener {
            val intent = Intent(this, EditPatientActivity::class.java)
            intent.putExtra("id", id)
            startActivity(intent, null)
        }
        buttonDelete.setOnClickListener {
            id?.let { it ->
                viewModel.deletePatient(it).enqueue(object : Callback<Any> {

                    override fun onResponse(call: Call<Any>, response: Response<Any>) {
                        onBackPressed()
                    }

                    override fun onFailure(call: Call<Any>, t: Throwable) {
                    }
                })
            }
            viewModel.getAppointments().forEach {
                it.data.forEach {
                    if (it.patient.id == id) {
                        viewModel.deleteAppointment(it.id)
                    }
                }
            }
        }
    }

    override fun onResume() {
        super.onResume()
        viewModel.observePatients()
        viewModel.patientsLiveData.observe(this) {
            try {
                patient = viewModel.getPatients().first { it.id == id }

                fullNameTextView.text = patient.fullName
                phone.text = patient.phone
                buttonCall.setOnClickListener {
                    val intent = Intent(Intent.ACTION_DIAL, Uri.parse("tel:${patient.phone}"))
                    startActivity(intent)
                }
            } catch (e: NoSuchElementException) {

            }
        }

        recyclerView.layoutManager = LinearLayoutManager(this)
        viewModel.observeAppointments()

        viewModel.appointmentsLiveData.observe(this) {
            val appointments = mutableListOf<Appointment>()
            viewModel.getAppointments().forEach {
                it.data.forEach {
                    if (it.patient.id == id && it.doctor == App.currentDoctor.id) {
                        appointments.add(it)
                    }
                }
            }
            recyclerView.adapter = PatientsAppointmentsAdapter(appointments)
        }
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
