package com.acoria.cleanarchtictureexampleapp.nature.buildNature

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.acoria.cleanarchtictureexampleapp.R
import com.acoria.cleanarchtictureexampleapp.nature.model.IPlant

class FavoritesAdapter(
    private val deleteFavoriteButtonClickListener: (IPlant) -> Unit
) : ListAdapter<Pair<IPlant, IPlantItemWrapper>, FavoritesAdapter.FavoriteViewHolder>(
    FavoritesDiffCallback()
) {

    class FavoriteViewHolder(layout: View) : RecyclerView.ViewHolder(layout) {
        private var textView: TextView = layout.findViewById(R.id.txt_favorite)
        private var deleteFavoriteButton: ImageButton =
            layout.findViewById(R.id.btn_delete_favorite)

        fun bind(
            plantEntry: Pair<IPlant, IPlantItemWrapper>,
            deleteFavoriteButtonClickListener: (IPlant) -> Unit
        ) {
            val plant = plantEntry.first
            textView.text = if (!plantEntry.second.isBeingDeleted) {
                plant.name
            } else {
                "deleting < ${plant.name}>"
            }
            deleteFavoriteButton.setOnClickListener {
                deleteFavoriteButtonClickListener.invoke(plant)
            }
        }
    }

    class FavoritesDiffCallback : DiffUtil.ItemCallback<Pair<IPlant, IPlantItemWrapper>>() {
        override fun areItemsTheSame(
            oldItem: Pair<IPlant, IPlantItemWrapper>,
            newItem: Pair<IPlant, IPlantItemWrapper>
        ): Boolean {
            return oldItem.first.id.equals(newItem.first.id)
        }

        override fun areContentsTheSame(
            oldItem: Pair<IPlant, IPlantItemWrapper>,
            newItem: Pair<IPlant, IPlantItemWrapper>
        ): Boolean {
            val oldPlant = oldItem.first
            val newPlant = newItem.first
            return oldPlant.equals(newPlant) && oldItem.second.isBeingDeleted == newItem.second.isBeingDeleted
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FavoriteViewHolder {
        val layout =
            LayoutInflater.from(parent.context).inflate(R.layout.item_favorite, parent, false)
        return FavoriteViewHolder(layout)
    }

    override fun onBindViewHolder(holder: FavoriteViewHolder, position: Int) {
        holder.bind(getItem(position), deleteFavoriteButtonClickListener)
    }
}