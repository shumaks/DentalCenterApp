package com.bsuir.dentalcenterapp

import android.annotation.SuppressLint
import android.app.Application
import android.content.Context
import android.util.Log
import com.bsuir.dentalcenterapp.models.doctor.Doctor
import io.reactivex.plugins.RxJavaPlugins

class App : Application() {

    override fun onCreate() {
        super.onCreate()
        context = applicationContext
        RxJavaPlugins.setErrorHandler { Log.d("App","error is not delivered") }
    }

    companion object {
        @SuppressLint("StaticFieldLeak")
        lateinit var context: Context

        lateinit var currentDoctor: Doctor
    }
}