package com.bsuir.dentalcenterapp.screens.appointment

import android.os.Bundle
import android.text.InputType
import android.view.*
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import com.bsuir.dentalcenterapp.models.appointment.Appointment
import com.bsuir.dentalcenterapp.models.appointment.AppointmentRequest
import com.bsuir.dentalcenterapp.screens.MainViewModel
import com.bsuir.dentalcenterapp.utils.isDateCorrect
import com.bsuir.dentalcenterapp.utils.isTimeCorrect
import com.bsuir.dentalcenterapp.utils.isToothNumberCorrect
import com.bsuir.dentalcenterapp.utils.toUpdateDate
import com.itexus.dentalcenterapp.R
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class EditAppointmentActivity : AppCompatActivity() {

    private val viewModel = MainViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_appointment)

        val toolbar: Toolbar = findViewById(R.id.toolbar)
        toolbar.title = getString(R.string.edit_appointment)
        setSupportActionBar(toolbar)

        val id = intent.getStringExtra("id")!!
        val toothNumber: EditText = findViewById(R.id.tooth_number)
        val diagnosis: EditText = findViewById(R.id.diagnosis)
        val price: EditText = findViewById(R.id.price)
        val date: EditText = findViewById(R.id.date)
        val time: EditText = findViewById(R.id.time)
        val buttonSave: Button = findViewById(R.id.buttonSaveAppointment)

        lateinit var appointment: Appointment
        MainViewModel.getAppointments().forEach {
            it.data.forEach {
                if (it.id == id) {
                    appointment = it
                }
            }
        }
        toothNumber.setText(appointment.dentNumber.toString())
        diagnosis.setText(appointment.diagnosis)
        price.setText(appointment.price.toString())
        date.setText(appointment.date.take(10).toUpdateDate())
        time.setText(appointment.time)
        val calendarView: CalendarView = findViewById(R.id.calendarView)
        val timePicker: TimePicker = findViewById(R.id.timePicker)
        val closeTime: ImageView = findViewById(R.id.closeTime)

        calendarView.setOnDateChangeListener { _, year, month, dayOfMonth ->
            val day = if (dayOfMonth < 10) {
                "0$dayOfMonth"
            } else {
                dayOfMonth
            }
            val newMonth = if (month.inc() < 10) {
                "0${month.inc()}"
            } else {
                month.inc()
            }
            date.setText("$day/$newMonth/$year")
            calendarView.visibility = View.GONE
            buttonSave.visibility = View.VISIBLE
        }

        date.inputType = InputType.TYPE_NULL
        date.focusable = View.NOT_FOCUSABLE
        time.inputType = InputType.TYPE_NULL
        time.focusable = View.NOT_FOCUSABLE
        calendarView.visibility = View.GONE
        timePicker.visibility = View.GONE
        closeTime.visibility = View.GONE

        date.setOnClickListener {
            calendarView.visibility = View.VISIBLE
            buttonSave.visibility = View.INVISIBLE
            closeTime.visibility = View.INVISIBLE
            timePicker.visibility = View.INVISIBLE
        }

        time.setOnClickListener {
            timePicker.visibility = View.VISIBLE
            closeTime.visibility = View.VISIBLE
            buttonSave.visibility = View.INVISIBLE
            calendarView.visibility = View.INVISIBLE
        }

        timePicker.setOnTimeChangedListener { _, hourOfDay, minute ->
            val hour = if (hourOfDay < 10) {
                "0$hourOfDay"
            } else {
                hourOfDay
            }
            val min = if (minute < 10) {
                "0$minute"
            } else {
                minute
            }
            time.setText("$hour:$min")
        }

        closeTime.setOnClickListener {
            timePicker.visibility = View.INVISIBLE
            closeTime.visibility = View.INVISIBLE
            buttonSave.visibility = View.VISIBLE
        }

        buttonSave.setOnClickListener {
            if (!toothNumber.text.toString().isToothNumberCorrect()) {
                Toast.makeText(this, getString(R.string.incorrect_tooth_number), Toast.LENGTH_SHORT)
                    .show()
            } else if (!date.text.toString().isDateCorrect()) {
                Toast.makeText(this, getString(R.string.incorrect_date), Toast.LENGTH_SHORT).show()
            } else if (!time.text.toString().isTimeCorrect()) {
                Toast.makeText(this, getString(R.string.incorrect_time), Toast.LENGTH_SHORT).show()
            } else {
                MainViewModel.updateAppointment(
                    appointment.id,
                    AppointmentRequest(
                        appointment.patient.id,
                        appointment.doctor,
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
