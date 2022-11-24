package com.bsuir.dentalcenterapp.screens.appointment

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import com.bsuir.dentalcenterapp.screens.patient.PatientsActivity
import com.itexus.dentalcenterapp.R

class AppointmentActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.toolbar_items, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem) = when (item.itemId) {
        R.id.action_patients -> {
            startActivity(Intent(this, PatientsActivity::class.java), null)
            true
        }

        else -> {
            super.onOptionsItemSelected(item)
        }
    }
}