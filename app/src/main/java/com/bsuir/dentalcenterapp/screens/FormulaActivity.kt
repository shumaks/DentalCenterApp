package com.bsuir.dentalcenterapp.screens

import android.graphics.Color
import android.os.Bundle
import android.view.*
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import com.itexus.dentalcenterapp.R
import java.text.SimpleDateFormat
import java.util.*

class FormulaActivity : AppCompatActivity() {

    private val viewModel = MainViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_formula)

        val toolbar: Toolbar = findViewById(R.id.toolbar)
        toolbar.title = "Формула зубов"
        setSupportActionBar(toolbar)

        val textViewList = listOf<TextView>(
            findViewById(R.id.textView11),
            findViewById(R.id.textView12),
            findViewById(R.id.textView13),
            findViewById(R.id.textView14),
            findViewById(R.id.textView15),
            findViewById(R.id.textView16),
            findViewById(R.id.textView17),
            findViewById(R.id.textView18),
            findViewById(R.id.textView21),
            findViewById(R.id.textView22),
            findViewById(R.id.textView23),
            findViewById(R.id.textView24),
            findViewById(R.id.textView25),
            findViewById(R.id.textView26),
            findViewById(R.id.textView27),
            findViewById(R.id.textView28),
            findViewById(R.id.textView31),
            findViewById(R.id.textView32),
            findViewById(R.id.textView33),
            findViewById(R.id.textView34),
            findViewById(R.id.textView35),
            findViewById(R.id.textView36),
            findViewById(R.id.textView37),
            findViewById(R.id.textView38),
            findViewById(R.id.textView41),
            findViewById(R.id.textView42),
            findViewById(R.id.textView43),
            findViewById(R.id.textView44),
            findViewById(R.id.textView45),
            findViewById(R.id.textView46),
            findViewById(R.id.textView47),
            findViewById(R.id.textView48)
        )

        val id = intent.getStringExtra("id")!!
        val pattern = "MM.dd"
        val simpleDateFormat = SimpleDateFormat(pattern)
        val calendar = Calendar.getInstance()
        val date = simpleDateFormat.format(calendar.time)

        viewModel.getAppointments().forEach {
            it.data.forEach { appointment ->
                if (appointment.patient.id == id && date > appointment.date) {
                    textViewList.forEach { textView ->
                        if (textView.text.toString().toInt() == appointment.dentNumber) {
                            textView.setTextColor(Color.RED)
                        }
                    }
                }
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
