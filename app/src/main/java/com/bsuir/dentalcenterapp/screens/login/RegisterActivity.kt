package com.bsuir.dentalcenterapp.screens.login

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.bsuir.dentalcenterapp.App
import com.bsuir.dentalcenterapp.models.doctor.DoctorAddRequest
import com.bsuir.dentalcenterapp.models.doctor.DoctorLoginResponse
import com.bsuir.dentalcenterapp.screens.appointment.AppointmentActivity
import com.bsuir.dentalcenterapp.screens.MainViewModel
import com.bsuir.dentalcenterapp.utils.isLoginCorrect
import com.itexus.dentalcenterapp.R
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class RegisterActivity : AppCompatActivity() {

    private val viewModel = MainViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        val login: EditText = findViewById(R.id.login)
        val fullname: EditText = findViewById(R.id.fullname)
        val password: EditText = findViewById(R.id.password)
        val buttonRegister: Button = findViewById(R.id.buttonRegister)

        buttonRegister.setOnClickListener {
            if (!login.text.toString().isLoginCorrect()) {
                Toast.makeText(
                    applicationContext,
                    getString(R.string.incorrect_login),
                    Toast.LENGTH_SHORT
                ).show()
            } else if (fullname.text.toString().isEmpty()) {
                Toast.makeText(
                    applicationContext,
                    getString(R.string.incorrect_fullname),
                    Toast.LENGTH_SHORT
                ).show()
            } else {
                MainViewModel.addDoctor(
                    DoctorAddRequest(
                        login.text.toString(),
                        fullname.text.toString(),
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
                                val intent = Intent(this@RegisterActivity, AppointmentActivity::class.java)
                                startActivity(intent, null)
                                finish()
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
        }
    }
}
