package com.lynn.moviemax.presentation.seemore.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.lynn.moviemax.data.model.Movie
import com.lynn.moviemax.databinding.ItemSeeMoreBinding

class SeeMoreAdapter(val itemClick: (Movie) -> Unit) :
    PagingDataAdapter<Movie, SeeMoreAdapter.ItemSeeMoreViewHolder>(COMPARATOR) {
    companion object {
        private val COMPARATOR =
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
            }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int,
    ): ItemSeeMoreViewHolder {
        val binding =
            ItemSeeMoreBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false,
            )
        return ItemSeeMoreViewHolder(binding, itemClick)
    }

    override fun onBindViewHolder(
        holder: ItemSeeMoreViewHolder,
        position: Int,
    ) {
        val movie = getItem(position)
        if (movie != null) {
            holder.bindView(movie)
        }
    }

    class ItemSeeMoreViewHolder(
        private val binding: ItemSeeMoreBinding,
        val itemClick: (Movie) -> Unit,
    ) : RecyclerView.ViewHolder(binding.root) {
        fun bindView(item: Movie) {
            with(item) {
                binding.ivMovieImg.load(item.posterPath) {
                    crossfade(true)
                }
                itemView.setOnClickListener {
                    itemClick(this)
                }
            }
        }
    }
}
