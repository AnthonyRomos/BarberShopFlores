package com.thomy.barbershopflores.ui.barbersView.home

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import com.thomy.barbershopflores.core.data.model.model.UserProfile
import com.thomy.barbershopflores.repository.auth.AuthRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
) : ViewModel(){


    private val _userProfileLiveData = MutableLiveData<UserProfile?>()
    val userProfileLiveData: MutableLiveData<UserProfile?> = _userProfileLiveData


    fun loadUserProfile() {
        val currentUser = FirebaseAuth.getInstance().currentUser
        if (currentUser != null) {
            val userProfile: UserProfile? = if (currentUser.providerData.any { it.providerId == GoogleAuthProvider.PROVIDER_ID }) {
                currentUser.displayName?.let { UserProfile(it) }
            } else {
                currentUser.email?.let { UserProfile(it) }
            }
            _userProfileLiveData.postValue(userProfile)
        }
    }
}