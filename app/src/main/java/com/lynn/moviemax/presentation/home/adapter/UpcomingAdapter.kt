package com.lynn.moviemax.presentation.home.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.lynn.moviemax.data.model.Movie
import com.lynn.moviemax.databinding.ItemUpcomingBinding

class UpcomingAdapter :
    RecyclerView.Adapter<UpcomingAdapter.ItemUpcomingViewHolder>() {
    private val dataDiffer =
        AsyncListDiffer(
            this,
            object : DiffUtil.ItemCallback<Movie>() {
                override fun areItemsTheSame(
                    oldItem: Movie,
                    newItem: Movie,
                ): Boolean {
                    return oldItem.id == newItem.id
                }

                override fun areContentsTheSame(
                    oldItem: Movie,
                    newItem: Movie,
                ): Boolean {
                    return oldItem.hashCode() == newItem.hashCode()
                }
            },
        )

    fun submitData(data: List<Movie>) {
        dataDiffer.submitList(data)
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int,
    ): ItemUpcomingViewHolder {
        val binding =
            ItemUpcomingBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ItemUpcomingViewHolder(binding)
    }

    override fun onBindViewHolder(
        holder: ItemUpcomingViewHolder,
        position: Int,
    ) {
        holder.bindView(dataDiffer.currentList[position])
    }

    override fun getItemCount(): Int = dataDiffer.currentList.size

    class ItemUpcomingViewHolder(
        private val binding: ItemUpcomingBinding,
    ) : RecyclerView.ViewHolder(binding.root) {
        fun bindView(item: Movie) {
            with(item) {
                binding.ivUpcoming.load(item.posterPath) {
                    crossfade(true)
                }

            }
        }
    }
}