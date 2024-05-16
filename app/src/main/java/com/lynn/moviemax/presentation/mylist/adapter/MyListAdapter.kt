package com.lynn.moviemax.presentation.mylist.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView.Adapter
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import coil.load
import com.lynn.moviemax.data.model.Movie
import com.lynn.moviemax.data.model.MyList
import com.lynn.moviemax.databinding.ItemMylistBinding
import kotlin.math.roundToInt

class MyListAdapter : Adapter<MyListAdapter.ItemMyListViewHolder>()
{
    private var asyncDataDiffer =
        AsyncListDiffer(
            this,
            object : DiffUtil.ItemCallback<MyList>() {
                override fun areItemsTheSame(
                    oldItem: MyList,
                    newItem: MyList,
                ): Boolean {
                    return oldItem.id == newItem.id
                }

                override fun areContentsTheSame(
                    oldItem: MyList,
                    newItem: MyList,
                ): Boolean {
                    return oldItem.hashCode() == oldItem.hashCode()
                }
            },
        )

    fun submitMovies(movies : List<MyList>){
        asyncDataDiffer.submitList(movies)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemMyListViewHolder {
        val binding = ItemMylistBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return ItemMyListViewHolder(binding)
    }

    override fun getItemCount(): Int = asyncDataDiffer.currentList.size

    override fun onBindViewHolder(holder: ItemMyListViewHolder, position: Int) {
        holder.bindView(asyncDataDiffer.currentList[position])
    }

    class ItemMyListViewHolder(
        private val binding: ItemMylistBinding,
    ) : ViewHolder(binding.root) {
        fun bindView(item: MyList) {
            with(item) {
                binding.ivMovieImage.load(item.posterPath) {
                    crossfade(true)
                }
                binding.tvListTitle.text = item.title
                binding.tvListRelease.text = item.releaseDate
                binding.tvListRate.text = item.voteAverage.roundToInt().toString()
            }
        }
    }
}