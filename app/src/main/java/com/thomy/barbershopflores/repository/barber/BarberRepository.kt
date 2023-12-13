package com.thomy.barbershopflores.repository.barber

interface  BarberRepository {

    suspend fun saveAppointment(client: String, barber: String, date: String, hour: String, appointmentId: String): Result<Unit>
}