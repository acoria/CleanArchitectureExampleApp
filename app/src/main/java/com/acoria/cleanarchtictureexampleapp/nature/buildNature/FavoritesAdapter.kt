package com.acoria.cleanarchtictureexampleapp.nature.buildNature

import android.text.Layout
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import com.acoria.cleanarchtictureexampleapp.R
import com.acoria.cleanarchtictureexampleapp.nature.model.IPlant

class FavoritesAdapter(private val myDataset: MutableList<IPlant>) : RecyclerView.Adapter<FavoritesAdapter.FavoriteViewHolder>() {

    class FavoriteViewHolder(val layout: View): RecyclerView.ViewHolder(layout){
        var textView: TextView = layout.findViewById(R.id.txt_favorite)
    }

    fun updateData(newData: List<IPlant>){
        myDataset.clear()
        myDataset.addAll(newData)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FavoriteViewHolder {
        val layout = LayoutInflater.from(parent.context).inflate(R.layout.item_favorite, parent, false)
        return FavoriteViewHolder(layout)
    }

    override fun getItemCount(): Int {
        return myDataset.size
    }

    override fun onBindViewHolder(holder: FavoriteViewHolder, position: Int) {
        holder.textView.text = myDataset[position].name
    }

}