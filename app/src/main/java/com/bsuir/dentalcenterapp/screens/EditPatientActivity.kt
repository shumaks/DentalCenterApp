package com.bsuir.dentalcenterapp.screens

import android.os.Bundle
import android.view.*
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import com.bsuir.dentalcenterapp.models.Appointment
import com.bsuir.dentalcenterapp.models.AppointmentRequest
import com.bsuir.dentalcenterapp.models.Patient
import com.bsuir.dentalcenterapp.models.PatientRequest
import com.itexus.dentalcenterapp.R
import io.reactivex.schedulers.Schedulers
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class EditPatientActivity : AppCompatActivity() {

    private val viewModel = MainViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_patient)

        val toolbar: Toolbar = findViewById(R.id.toolbar)
        toolbar.title = getString(R.string.edit_patient)
        setSupportActionBar(toolbar)

        val id = intent.getStringExtra("id")!!
        val fullName: EditText = findViewById(R.id.fullname)
        val phone: EditText = findViewById(R.id.phone)
        val buttonSave: Button = findViewById(R.id.button_save)

        lateinit var patient: Patient
        viewModel.getPatients().forEach {
            if (it.id == id) {
                patient = it
            }
        }
        fullName.setText(patient.fullName)
        phone.setText(patient.phone)

        buttonSave.setOnClickListener {
            viewModel.updatePatient(
                patient.id,
                PatientRequest(
                    fullName.text.toString(),
                    phone.text.toString()
                )
            ).enqueue(object : Callback<Any> {

                override fun onResponse(call: Call<Any>, response: Response<Any>) {
                    if (response.isSuccessful) {
                        onBackPressed()
                    } else {
                        Toast.makeText(
                            applicationContext,
                            getString(R.string.incorrect_data),
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }

                override fun onFailure(call: Call<Any>, t: Throwable) {
                }
            })
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
