package com.thomy.barbershopflores.ui.barbersView.appointment

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import com.thomy.barbershopflores.core.data.model.model.Appointment
import com.thomy.barbershopflores.core.data.model.utils.FireStoreCollection.APPOINTMENTS
import com.thomy.barbershopflores.databinding.FragmentCitasBinding
import com.thomy.barbershopflores.repository.barber.BarberRepository
import com.thomy.barbershopflores.ui.adapters.AppointmentsAdapter
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class CitasFragment : Fragment() {

    val TAG: String = "CitasFragment"

    @Inject
    lateinit var barberRepository: BarberRepository
    private var _binding: FragmentCitasBinding? = null
    private val binding get() = _binding!!
    private val auth: FirebaseAuth = FirebaseAuth.getInstance()
    val db: FirebaseFirestore = FirebaseFirestore.getInstance()
    private lateinit var appointmentsAdapter: AppointmentsAdapter


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentCitasBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        Log.d("Lifecycle", "onViewCreated")

        appointmentsAdapter = AppointmentsAdapter()

        binding.recyclerViewAppointments.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = appointmentsAdapter
        }

        observer()
    }

    private fun observer() {
        binding.btnConsult.setOnClickListener {
            val currentUser = auth.currentUser
            if (currentUser != null) {
                val userId = currentUser.email
                if (userId != null) {
                    db.collection(APPOINTMENTS)
                        .document(userId)
                        .collection("userAppointments")
                        .orderBy("data", Query.Direction.DESCENDING)
                        .get()
                        .addOnSuccessListener { result ->
                            val appointmentsList = mutableListOf<Appointment>()

                            for (document in result) {
                                val client = document.getString("client") ?: ""
                                val barber = document.getString("barber") ?: ""
                                val date = document.getString("data") ?: ""
                                val hour = document.getString("hora") ?: ""

                                    val appointment = Appointment(client, barber, date, hour)
                                    appointmentsList.add(appointment)
                                }
                            if (appointmentsList.isEmpty()) {
                                showToast("No tiene citas generadas")                            } else {
                            }

                            appointmentsAdapter.submitList(appointmentsList)
                        }

                        .addOnFailureListener {
                            showToast("No se ha podido conectar")
                        }
                }
            }
        }
    }

    private fun showToast(message: String) {
        Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show()
    }
}