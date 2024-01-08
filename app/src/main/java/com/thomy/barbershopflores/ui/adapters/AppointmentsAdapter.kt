package com.thomy.barbershopflores.ui.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.thomy.barbershopflores.R
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
        binding.textClient.text = binding.root.context.getString(R.string.client_label, appointment.client)
        binding.textBarber.text = binding.root.context.getString(R.string.barber_label, appointment.barber)
        binding.textDate.text = binding.root.context.getString(R.string.date_label, appointment.date)
        binding.textHour.text = binding.root.context.getString(R.string.hour_label, appointment.hour)
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
