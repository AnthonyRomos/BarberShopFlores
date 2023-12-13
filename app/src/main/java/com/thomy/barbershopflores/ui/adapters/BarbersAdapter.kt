package com.thomy.barbershopflores.ui.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.thomy.barbershopflores.core.data.model.model.Barbers
import com.thomy.barbershopflores.core.data.model.utils.CustomItemAnimator.Companion.animationDuration
import com.thomy.barbershopflores.core.data.model.utils.CustomItemAnimator.Companion.selectedScale
import com.thomy.barbershopflores.core.data.model.utils.CustomItemAnimator.Companion.unselectedScale
import com.thomy.barbershopflores.R
import com.thomy.barbershopflores.databinding.ItemListBinding

class BarbersAdapter(
    private val context: Context,
    private val listServices: MutableList<Barbers>,
    private val onItemClick: (Barbers) -> Unit

) :
    RecyclerView.Adapter<BarbersAdapter.BarbersViewHolder>() {

    private var selectedPosition: Int = RecyclerView.NO_POSITION


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BarbersViewHolder {
        val itemList = ItemListBinding.inflate(LayoutInflater.from(context), parent, false)
        return BarbersViewHolder(itemList)
    }

    override fun getItemCount() = listServices.size

    override fun onBindViewHolder(holder: BarbersViewHolder, position: Int) {
        val service = listServices[position]
        val isSelected = position == selectedPosition

        if (isSelected) {
            holder.textService.setBackgroundColor(ContextCompat.getColor(context, R.color.white))
        } else {
            holder.textService.setBackgroundColor(ContextCompat.getColor(context, R.color.primaryDark))
        }

        holder.imgService.setImageResource(service.img!!)
        holder.textService.text = service.name

        if (position == selectedPosition) {
            holder.itemView.animate()
                .scaleX(selectedScale)
                .scaleY(selectedScale)
                .setDuration(animationDuration)
                .start()
        } else {
            holder.itemView.animate()
                .scaleX(unselectedScale)
                .scaleY(unselectedScale)
                .setDuration(animationDuration)
                .start()
        }
        holder.itemView.setOnClickListener {
            val previousSelected = selectedPosition
            selectedPosition = holder.adapterPosition

            notifyItemChanged(previousSelected)
            notifyItemChanged(selectedPosition)
        }
    }

    inner class BarbersViewHolder(binding: ItemListBinding) :
        RecyclerView.ViewHolder(binding.root) {
        val imgService = binding.ivPoster
        val textService = binding.textService
    }

    fun getSelectedBarber(): Barbers? {
        return if (selectedPosition != RecyclerView.NO_POSITION) {
            listServices[selectedPosition]
        } else {
            null
        }
    }
}