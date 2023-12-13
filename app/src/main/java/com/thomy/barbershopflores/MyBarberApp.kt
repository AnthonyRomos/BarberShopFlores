package com.thomy.barbershopflores

import android.app.Application
import com.google.firebase.FirebaseApp
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class MyBarberApp : Application(){
    override fun onCreate() {
        super.onCreate()
        FirebaseApp.initializeApp(this)
    }
}