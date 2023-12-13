package com.thomy.barbershopflores.core.data.model.utils


import android.content.Context
import androidx.appcompat.app.AlertDialog

object DialogUtils {

    fun showSuccessDialog(context: Context?, message: String) {
        val builder = context?.let { AlertDialog.Builder(it) }
        builder?.setTitle("Estado de la Cita")
        builder?.setMessage(message)
        builder?.setPositiveButton("OK") { _, _ ->
        }
        val dialog = builder?.create()
        if (dialog != null) {
            dialog.window?.setBackgroundDrawableResource(android.R.color.darker_gray)
        }
        dialog?.show()
    }

    fun showErrorDialog(context: Context, message: String) {
        val builder = AlertDialog.Builder(context)
        builder.setTitle("Error")
        builder.setMessage(message)
        builder.setPositiveButton("OK") { _, _ ->
        }
        val dialog = builder.create()
        dialog.show()
    }
}
