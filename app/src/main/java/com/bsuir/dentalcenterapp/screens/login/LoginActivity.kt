package com.bsuir.dentalcenterapp.screens.login

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.widget.Toolbar
import com.bsuir.dentalcenterapp.App
import com.bsuir.dentalcenterapp.models.doctor.DoctorLoginRequest
import com.bsuir.dentalcenterapp.models.doctor.DoctorLoginResponse
import com.bsuir.dentalcenterapp.screens.appointment.AppointmentActivity
import com.bsuir.dentalcenterapp.screens.MainViewModel
import com.bsuir.dentalcenterapp.utils.LocaleHelper
import com.itexus.dentalcenterapp.R
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class LoginActivity : AppCompatActivity() {

    private lateinit var login: EditText
    private lateinit var password: EditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        val toolbar: Toolbar = findViewById(R.id.toolbar)
        toolbar.title = getString(R.string.app_name)
        setSupportActionBar(toolbar)

        login = findViewById(R.id.login)
        password = findViewById(R.id.password)
        val buttonLogin: Button = findViewById(R.id.buttonLogin)
        val buttonRegister: Button = findViewById(R.id.buttonRegister)

        buttonLogin.setOnClickListener {
            MainViewModel.loginDoctor(
                DoctorLoginRequest(
                    login.text.toString(),
                    password.text.toString()
                )
            ).enqueue(object : Callback<DoctorLoginResponse> {

                override fun onResponse(
                    call: Call<DoctorLoginResponse>,
                    response: Response<DoctorLoginResponse>
                ) {
                    if (response.isSuccessful) {
                        if (response.body()?.data != null) {
                            App.currentDoctor = response.body()?.data!!
                            val intent = Intent(this@LoginActivity, AppointmentActivity::class.java)
                            startActivity(intent, null)
                        } else {
                            Toast.makeText(
                                applicationContext,
                                getString(R.string.incorrect_data),
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                    } else {
                        Toast.makeText(
                            applicationContext,
                            getString(R.string.incorrect_data),
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }

                override fun onFailure(call: Call<DoctorLoginResponse>, t: Throwable) {
                }
            })
        }

        buttonRegister.setOnClickListener {
            val intent = Intent(this@LoginActivity, RegisterActivity::class.java)
            startActivity(intent, null)
        }
    }

    override fun onResume() {
        super.onResume()
        login.setText("")
        password.setText("")
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.toolbar_locale, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem) = when (item.itemId) {
        R.id.action_lang -> {
            if (item.title == "RU") {
                item.title = "EN"
                LocaleHelper.setLocale(this,"en")
            } else {
                item.title = "RU"
                LocaleHelper.setLocale(this,"ru")
            }

            finish()
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent, null)

            true
        }

        else -> {
            super.onOptionsItemSelected(item)
        }
    }
}
