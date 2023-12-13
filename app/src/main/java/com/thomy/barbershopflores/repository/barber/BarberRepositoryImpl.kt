package com.thomy.barbershopflores.repository.barber

import com.google.firebase.firestore.FirebaseFirestore
import com.thomy.barbershopflores.core.data.model.utils.FireStoreCollection.APPOINTMENTS
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class BarberRepositoryImpl @Inject constructor() : BarberRepository {

    private val db = FirebaseFirestore.getInstance()

    override suspend fun saveAppointment(client: String, barber: String, date: String, hour: String, appointmentId: String): Result<Unit> {
        return try {
            val dataUser = hashMapOf(
                "client" to client,
                "barber" to barber,
                "data" to date,
                "hora" to hour
            )

            val appointmentDocument = if (appointmentId.isEmpty()) {
                db.collection(APPOINTMENTS)
                    .document(client)
                    .collection("userAppointments")
                    .document()
            } else {
                db.collection(APPOINTMENTS)
                    .document(client)
                    .collection("userAppointments")
                    .document(appointmentId)
            }

            appointmentDocument.set(dataUser).await()
            Result.success(Unit)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}


