package com.thomy.barbershopflores.core.di

import android.content.SharedPreferences
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.gson.Gson
import com.thomy.barbershopflores.core.data.model.model.UserDao
import com.thomy.barbershopflores.repository.auth.AuthRepository
import com.thomy.barbershopflores.repository.auth.AuthRepositoryImp
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.DelicateCoroutinesApi
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
object RepositoryModule {

    @OptIn(DelicateCoroutinesApi::class)
    @Provides
    @Singleton
    fun provideAuthRepository(
        database: FirebaseFirestore,
        auth: FirebaseAuth,
        appPreferences: SharedPreferences,
        gson: Gson,
        userDao: UserDao,
        googleSignInClient: GoogleSignInClient
    ): AuthRepository {
        return AuthRepositoryImp(auth, database, appPreferences, googleSignInClient, gson, userDao )
    }

    @OptIn(DelicateCoroutinesApi::class)
    @Provides
    fun provideUserDao(): UserDao {
        return UserDao()
    }
}