package com.thomy.barbershopflores.ui.barbersView.info

import androidx.lifecycle.ViewModel
import com.thomy.barbershopflores.repository.auth.AuthRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class InfoViewModel @Inject constructor(
    private val repository: AuthRepository
) : ViewModel(){

    fun logout(result: () -> Unit) {
        repository.logout(result)
    }
}