package com.bsuir.dentalcenterapp.screens

import android.os.Bundle
import android.util.Log
import android.view.*
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import com.bsuir.dentalcenterapp.models.PatientRequest
import com.itexus.dentalcenterapp.R
import io.reactivex.schedulers.Schedulers
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.IOException

class AddPatientActivity : AppCompatActivity() {

    private val viewModel = MainViewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_patient)

        val toolbar: Toolbar = findViewById(R.id.toolbar)
        toolbar.title = "Добавить пациента"
        setSupportActionBar(toolbar)

        val fullName: EditText = findViewById(R.id.fullname)
        val phone: EditText = findViewById(R.id.phone)
        val buttonAdd: Button = findViewById(R.id.button_add)

        buttonAdd.setOnClickListener {
            viewModel.addPatient(
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
                            "Некорректные ФИО или номер телефона!",
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
