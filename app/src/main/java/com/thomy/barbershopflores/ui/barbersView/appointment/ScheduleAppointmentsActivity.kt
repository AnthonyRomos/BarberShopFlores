package com.thomy.barbershopflores.ui.barbersView.appointment

import android.graphics.Color
import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.GridLayoutManager
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.android.material.snackbar.Snackbar
import com.thomy.barbershopflores.R
import com.thomy.barbershopflores.core.data.model.model.Barbers
import com.thomy.barbershopflores.core.data.model.utils.DialogUtils
import com.thomy.barbershopflores.databinding.ActivityScheduleAppointmentsBinding
import com.thomy.barbershopflores.ui.adapters.BarbersAdapter
import dagger.hilt.android.AndroidEntryPoint
import java.util.Calendar

@AndroidEntryPoint
class ScheduleAppointmentsActivity : AppCompatActivity() {


    val TAG: String = "ScheduleAppointmentsActivity"
    private lateinit var binding: ActivityScheduleAppointmentsBinding
    private val viewModel: ScheduleAppointmentsViewModel by viewModels()
    private lateinit var barbersAdapter: BarbersAdapter
    private val listBarbers: MutableList<Barbers> = mutableListOf()
    private val calendar: Calendar = Calendar.getInstance()
    private var date: String = ""
    private var hour: String = ""


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityScheduleAppointmentsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setUpBarbersRecyclerView()
        setUpDatePicker()
        setUpTimePicker()
        saveScheduling()
        getService()
        supportActionBar?.hide()
    }

    private fun setUpBarbersRecyclerView() {
        val recyclerViewBarbers = binding.rcvBarbers
        recyclerViewBarbers.layoutManager = GridLayoutManager(this, 3)
        barbersAdapter = BarbersAdapter(this, listBarbers) { barberName ->
            viewModel.selectBarber(barberName)
        }
        recyclerViewBarbers.setHasFixedSize(true)
        recyclerViewBarbers.adapter = barbersAdapter
        recyclerViewBarbers.itemAnimator = DefaultItemAnimator()
    }

    private fun setUpDatePicker() {
        binding.datePicker.setOnDateChangedListener { _, year, monthOfYear, dayOfMonth ->
            calendar.set(Calendar.YEAR, year)
            calendar.set(Calendar.MONTH, monthOfYear)
            calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth)
            date = "%02d / %02d / %04d".format(dayOfMonth, monthOfYear + 1, year)
        }
    }

    private fun setUpTimePicker() {
        binding.timePicker.setOnTimeChangedListener { _, hourOfDay, minute ->
            val minutes: String = if (minute < 10) "0$minute" else minute.toString()
            hour = "%02d:$minutes".format(hourOfDay)
        }
        binding.timePicker.setIs24HourView(true)
    }

    private fun saveScheduling() {
        binding.btnAgendar.setOnClickListener {
            val isBarberSelected = barbersAdapter.getSelectedBarber()
            if (isBarberSelected == null) {
                message("Seleccione un Barbero", "#FF0000")
                return@setOnClickListener
            }
            if (date.isEmpty() || hour.isEmpty()) {
                message("Seleccione una fecha y hora", "#FF0000")
                return@setOnClickListener
            }
            binding.progressBar.visibility = View.VISIBLE
           val userEmail = viewModel.userProfileLiveData.value?.userName ?: ""
            viewModel.saveAppointment(userEmail, barbersAdapter.getSelectedBarber()?.name ?: "", date, hour, "")
        }
        viewModel.messageLiveData.observe(this) { message ->
            binding.progressBar.visibility = View.GONE
            if (message != null) {
                showSuccessDialog(message)
            } else {
                DialogUtils.showErrorDialog(this, "Error al crear cita!")
            }
        }
    }

    private fun showSuccessDialog(message: String) {
        MaterialAlertDialogBuilder(this)
            .setTitle(getString(R.string.Estadodelacita))
            .setMessage(message)
            .setPositiveButton(getString(R.string.confirm)) { _, _, ->
            }
            .setCancelable(false)
            .create()
            .show()
    }

    private fun message(message: String, color: String) {
        val view = findViewById<View>(android.R.id.content)
        val snackbar = Snackbar.make(view, message, Snackbar.LENGTH_SHORT)
        snackbar.setBackgroundTint(Color.parseColor(color))
        snackbar.setTextColor(Color.parseColor("#FFFFFF"))
        snackbar.show()
    }

    private fun getService() {
        listBarbers.add(Barbers(R.drawable.barb1, "Jesus"))
        listBarbers.add(Barbers(R.drawable.barb2, "Jefferson"))
        listBarbers.add(Barbers(R.drawable.barb3, "J Carlos"))
    }
}
