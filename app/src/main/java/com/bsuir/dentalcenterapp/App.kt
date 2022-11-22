package com.bsuir.dentalcenterapp

import android.annotation.SuppressLint
import android.app.Application
import android.content.Context
import com.bsuir.dentalcenterapp.models.Doctor

class App : Application() {

    override fun onCreate() {
        super.onCreate()
        context = applicationContext
    }

    companion object {
        @SuppressLint("StaticFieldLeak")
        lateinit var context: Context

        lateinit var currentDoctor: Doctor
    }
}