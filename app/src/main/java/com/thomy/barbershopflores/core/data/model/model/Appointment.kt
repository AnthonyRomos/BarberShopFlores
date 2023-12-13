package com.thomy.barbershopflores.core.data.model.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Appointment(
    val client: String,
    val barber: String,
    val date: String = "",
    val hour: String = ""
) : Parcelable
