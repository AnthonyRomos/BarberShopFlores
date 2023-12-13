package com.thomy.barbershopflores.ui.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.thomy.barbershopflores.core.data.model.model.Barbers
import com.thomy.barbershopflores.databinding.ItemCortesBinding

class CortesAdapter(
    private val context: Context,
    private val listServices: MutableList<Barbers>
):
    RecyclerView.Adapter<CortesAdapter.CortesViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CortesViewHolder {
        val itemCortes = ItemCortesBinding.inflate(LayoutInflater.from(context),parent,false)
        return CortesViewHolder(itemCortes)
    }

    override fun getItemCount() = listServices.size

    override fun onBindViewHolder(holder: CortesViewHolder, position: Int) {
        val service = listServices[position]
        holder.imgService.setImageResource(service.img!!)
    }

    inner class CortesViewHolder(binding: ItemCortesBinding) :
        RecyclerView.ViewHolder(binding.root) {
        val imgService = binding.ivPosterImage
    }
}