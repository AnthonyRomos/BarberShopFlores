package com.thomy.barbershopflores.core.di

import android.content.Context
import android.content.SharedPreferences
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.FirebaseFirestore
import com.google.gson.Gson
import com.thomy.barbershopflores.core.data.model.utils.SharedPrefConstants
import com.thomy.barbershopflores.repository.barber.BarberRepository
import com.thomy.barbershopflores.repository.barber.BarberRepositoryImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideFirebaseFirestore(): FirebaseFirestore {
        return FirebaseFirestore.getInstance()
    }

    @Provides
    @Singleton
    fun provideAnotherRepository(): BarberRepository {
        return BarberRepositoryImpl()
    }


    @Provides
    @Singleton
    fun provideAppointmentsCollection(): CollectionReference {
        return FirebaseFirestore.getInstance().collection("appointment")
    }


    @Provides
    @Singleton
    fun provideSharedPref(@ApplicationContext context: Context): SharedPreferences {
        return context.getSharedPreferences(
            SharedPrefConstants.LOCAL_SHARED_PREF,
            Context.MODE_PRIVATE
        )
    }

    @Provides
    @Singleton
    fun provideGson(): Gson {
        return Gson()
    }
}