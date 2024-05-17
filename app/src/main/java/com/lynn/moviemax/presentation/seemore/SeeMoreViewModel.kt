package com.lynn.moviemax.presentation.seemore

import android.os.Bundle
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import androidx.paging.*
import com.lynn.moviemax.data.model.Movie
import com.lynn.moviemax.data.repository.MyListRepository
import com.lynn.moviemax.data.repository.SeeMoreRepository
import com.lynn.moviemax.utils.ResultWrapper
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class SeeMoreViewModel(
    private val extras: Bundle?,
    private val seeMoreRepository: SeeMoreRepository,
    private val myListrepository: MyListRepository,
) : ViewModel() {
    val header = extras?.getString(SeeMoreActivity.EXTRAS_ITEM) ?: ""

    fun nowPlaying(): Flow<PagingData<Movie>> = seeMoreRepository.getNowPlayingList().cachedIn(viewModelScope)

    fun popular(): Flow<PagingData<Movie>> = seeMoreRepository.getPopularList().cachedIn(viewModelScope)

    fun topRated(): Flow<PagingData<Movie>> = seeMoreRepository.getTopRatedList().cachedIn(viewModelScope)

    fun upComing(): Flow<PagingData<Movie>> = seeMoreRepository.getUpComingList().cachedIn(viewModelScope)

    fun removeMovie(item: Movie) {
        viewModelScope.launch(Dispatchers.IO) {
            myListrepository.deleteMovie(item).collect()
        }
    }

    fun checkMovieById(movie: Movie): LiveData<ResultWrapper<List<Movie>>> {
        return movie.let {
            myListrepository.checkListById(it.id).asLiveData(Dispatchers.IO)
        }
    }

    fun addToMyList(movie: Movie): LiveData<ResultWrapper<Boolean>> {
        return movie.let {
            myListrepository.insertMovieList(it).asLiveData(Dispatchers.IO)
        }
    }
}
