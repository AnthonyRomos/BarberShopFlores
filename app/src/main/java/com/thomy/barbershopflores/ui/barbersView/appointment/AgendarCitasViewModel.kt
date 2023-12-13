package com.thomy.barbershopflores.ui.barbersView.appointment

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.FirebaseAuth
import com.thomy.barbershopflores.core.data.model.model.Barbers
import com.thomy.barbershopflores.core.data.model.model.UserProfile
import com.thomy.barbershopflores.repository.barber.BarberRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AgendarCitasViewModel @Inject constructor(
    private val barberRepository: BarberRepository,
    ) : ViewModel() {

    private val _selectedBarber = MutableLiveData<String>()
    val selectedBarber: LiveData<String> get() = _selectedBarber

    fun selectBarber(barberName: Barbers) {
        _selectedBarber.value = barberName.toString()
    }

    private val _userProfileLiveData = MutableLiveData<UserProfile?>()
    val userProfileLiveData: MutableLiveData<UserProfile?> = _userProfileLiveData

    private val _messageLiveData = MutableLiveData<String>()
    val messageLiveData: LiveData<String> get() = _messageLiveData

    fun saveAppointment(client: String, barber: String, date: String, hour: String, appointmentId: String) {
        viewModelScope.launch {
            try {
                val currentUsers = FirebaseAuth.getInstance().currentUser
                val clientEmail = currentUsers?.email ?: ""
                barberRepository.saveAppointment(clientEmail, barber, date, hour, appointmentId)
                _messageLiveData.postValue("Cita creada exitosamente! " + "\nverifica el apartado Mis Citas")
            } catch (e: Exception) {
                _messageLiveData.postValue("Error al crear cita: ${e.message}")
            }
        }
    }
}
