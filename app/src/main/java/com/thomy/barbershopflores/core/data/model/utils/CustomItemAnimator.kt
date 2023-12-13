package com.thomy.barbershopflores.core.data.model.utils

import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.view.View
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.RecyclerView

class CustomItemAnimator : DefaultItemAnimator() {

    companion object {
        const val selectedScale = 1.2f
        const val unselectedScale = 1.0f
        const val animationDuration = 300L
    }
    override fun animateChange(
        oldHolder: RecyclerView.ViewHolder,
        newHolder: RecyclerView.ViewHolder?,
        fromX: Int,
        fromY: Int,
        toX: Int,
        toY: Int
    ): Boolean {
        val newHolderView = newHolder?.itemView

        if (newHolderView != null) {
            val isItemSelected = oldHolder.adapterPosition == newHolder.adapterPosition

            if (isItemSelected) {
                val scaleX = ObjectAnimator.ofFloat(newHolderView, View.SCALE_X, unselectedScale, selectedScale)
                val scaleY = ObjectAnimator.ofFloat(newHolderView, View.SCALE_Y, unselectedScale, selectedScale)

                val animatorSet = AnimatorSet()
                animatorSet.playTogether(scaleX, scaleY)
                animatorSet.duration = animationDuration
                animatorSet.start()
            }
        }

        return super.animateChange(oldHolder, newHolder, fromX, fromY, toX, toY)
    }
}
