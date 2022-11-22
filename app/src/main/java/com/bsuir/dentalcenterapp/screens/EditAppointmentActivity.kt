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
import com.itexus.dentalcenterapp.R
import io.reactivex.schedulers.Schedulers
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class EditAppointmentActivity : AppCompatActivity() {

    private val viewModel = MainViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_appointment)

        val toolbar: Toolbar = findViewById(R.id.toolbar)
        toolbar.title = "Изменить прием"
        setSupportActionBar(toolbar)

        val id = intent.getStringExtra("id")!!
        val toothNumber: EditText = findViewById(R.id.tooth_number)
        val diagnosis: EditText = findViewById(R.id.diagnosis)
        val price: EditText = findViewById(R.id.price)
        val date: EditText = findViewById(R.id.date)
        val time: EditText = findViewById(R.id.time)
        val buttonSave: Button = findViewById(R.id.buttonSaveAppointment)

        lateinit var appointment: Appointment
        viewModel.getAppointments().forEach {
            it.data.forEach {
                if (it.id == id) {
                    appointment = it
                }
            }
        }
        toothNumber.setText(appointment.dentNumber.toString())
        diagnosis.setText(appointment.diagnosis)
        price.setText(appointment.price.toString())
        date.setText(appointment.date)
        time.setText(appointment.time)

        buttonSave.setOnClickListener {
            viewModel.updateAppointment(
                appointment.id,
                AppointmentRequest(
                    appointment.patient.id,
                    toothNumber.text.toString().toInt(),
                    diagnosis.text.toString(),
                    price.text.toString().toInt(),
                    date.text.toString(),
                    time.text.toString()
                )
            ).enqueue(object : Callback<Any> {

                override fun onResponse(call: Call<Any>, response: Response<Any>) {
                    if (response.isSuccessful) {
                        onBackPressed()
                    } else {
                        Toast.makeText(
                            applicationContext,
                            "Некорректные данные!",
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
