package com.thomy.barbershopflores.ui.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.thomy.barbershopflores.core.data.model.model.Appointment
import com.thomy.barbershopflores.databinding.ListItemAppointmentBinding

class AppointmentsAdapter : ListAdapter<Appointment, AppointmentViewHolder>(AppointmentDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AppointmentViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ListItemAppointmentBinding.inflate(inflater, parent, false)
        return AppointmentViewHolder(binding)
    }

    override fun onBindViewHolder(holder: AppointmentViewHolder, position: Int) {
        val appointment = getItem(position)
        holder.bind(appointment)
    }
}

class AppointmentViewHolder(private val binding: ListItemAppointmentBinding) : RecyclerView.ViewHolder(binding.root) {

    fun bind(appointment: Appointment) {
        binding.textClient.text = "Cliente: ${appointment.client}"
        binding.textBarber.text = "Barbero: ${appointment.barber}"
        binding.textDate.text = "Fecha: ${appointment.date}"
        binding.textHour.text = "Hora: ${appointment.hour}"
    }
}

class AppointmentDiffCallback : DiffUtil.ItemCallback<Appointment>() {
    override fun areItemsTheSame(oldItem: Appointment, newItem: Appointment): Boolean {
        return oldItem == newItem
    }

    override fun areContentsTheSame(oldItem: Appointment, newItem: Appointment): Boolean {
        return oldItem == newItem
    }
}
