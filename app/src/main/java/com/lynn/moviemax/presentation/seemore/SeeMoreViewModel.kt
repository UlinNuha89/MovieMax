package com.lynn.moviemax.presentation.seemore

import android.os.Bundle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.*
import com.lynn.moviemax.data.model.Movie
import com.lynn.moviemax.data.repository.SeeMoreRepository
import kotlinx.coroutines.flow.Flow

class SeeMoreViewModel(
    private val extras: Bundle?,
    private val repository: SeeMoreRepository
) : ViewModel() {
    val header = extras?.getString(SeeMoreActivity.EXTRAS_ITEM) ?: ""
    fun nowPlaying(): Flow<PagingData<Movie>> = repository.getNowPlayingList().cachedIn(viewModelScope)
    fun popular(): Flow<PagingData<Movie>> = repository.getPopularList().cachedIn(viewModelScope)
    fun topRated(): Flow<PagingData<Movie>> = repository.getTopRatedList().cachedIn(viewModelScope)
    fun upComing(): Flow<PagingData<Movie>> = repository.getUpComingList().cachedIn(viewModelScope)

}