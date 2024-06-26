package com.lynn.moviemax.presentation.mylist.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView.Adapter
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import coil.load
import com.lynn.moviemax.data.model.Movie
import com.lynn.moviemax.databinding.ItemMylistAltBinding

class MyListAdapter(val itemClick: (Movie) -> Unit) : Adapter<MyListAdapter.ItemMyListViewHolder>() {
    private var asyncDataDiffer =
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
                    return oldItem.hashCode() == oldItem.hashCode()
                }
            },
        )

    fun submitMovies(movies: List<Movie>) {
        asyncDataDiffer.submitList(movies)
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int,
    ): ItemMyListViewHolder {
        val binding = ItemMylistAltBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ItemMyListViewHolder(binding, itemClick)
    }

    override fun getItemCount(): Int = asyncDataDiffer.currentList.size

    override fun onBindViewHolder(
        holder: ItemMyListViewHolder,
        position: Int,
    ) {
        holder.bindView(asyncDataDiffer.currentList[position])
    }

    class ItemMyListViewHolder(
        private val binding: ItemMylistAltBinding,
        val itemClick: (Movie) -> Unit,
    ) : ViewHolder(binding.root) {
        fun bindView(item: Movie) {
            with(item) {
                binding.ivMovieImage.load(item.posterPath) {
                    crossfade(true)
                }
                binding.ivBackdropMovie.load(item.backdropPath) {
                    crossfade(true)
                }
                binding.tvListRate.text = item.voteAverage.toString()
                binding.tvListRelease.text = item.releaseDate
                binding.tvListTitle.text = item.title
                itemView.setOnClickListener {
                    itemClick(this)
                }
            }
        }
    }
}
