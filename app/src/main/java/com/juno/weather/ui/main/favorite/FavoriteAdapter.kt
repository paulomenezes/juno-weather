package com.juno.weather.ui.main.favorite

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.juno.weather.data.local.models.Favorite
import com.juno.weather.databinding.ItemFavoriteBinding

class FavoriteAdapter(private val deleteAction: (id: Long) -> Unit) : ListAdapter<Favorite, FavoriteAdapter.ViewHolder>(SearchDiff()) {
    inner class ViewHolder(private val binding: ItemFavoriteBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(favorite: Favorite) {
            binding.textViewFavoriteName.text = favorite.cityName
            binding.textViewFavoriteCountry.text = favorite.country

            binding.buttonDelete.setOnClickListener {
                deleteAction(favorite.id)
            }
        }
    }

    class SearchDiff : DiffUtil.ItemCallback<Favorite>() {
        override fun areItemsTheSame(oldItem: Favorite, newItem: Favorite): Boolean = oldItem == newItem
        override fun areContentsTheSame(oldItem: Favorite, newItem: Favorite): Boolean = oldItem == newItem
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemFavoriteBinding.inflate(LayoutInflater.from(parent.context), parent, false)

        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(getItem(position))
    }
}