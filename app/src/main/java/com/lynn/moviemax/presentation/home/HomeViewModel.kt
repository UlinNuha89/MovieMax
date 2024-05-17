package com.lynn.moviemax.presentation.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.lynn.moviemax.data.model.Movie
import com.lynn.moviemax.data.repository.MyListRepository
import com.lynn.moviemax.data.repository.NowPlayingRepository
import com.lynn.moviemax.data.repository.PopularRepository
import com.lynn.moviemax.data.repository.TopRatedRepository
import com.lynn.moviemax.data.repository.UpcomingRepository
import com.lynn.moviemax.utils.ResultWrapper
import kotlinx.coroutines.Dispatchers

class HomeViewModel(
    private val nowPlayingRepository: NowPlayingRepository,
    private val popularRepository: PopularRepository,
    private val upcomingRepository: UpcomingRepository,
    private val topRatedRepository: TopRatedRepository,
    private val myListRepository: MyListRepository,
) : ViewModel() {
    fun getDataNowPlaying() = nowPlayingRepository.getNowPlaying().asLiveData(Dispatchers.IO)

    fun getDataPopular() = popularRepository.getPopular().asLiveData(Dispatchers.IO)

    fun getDataUpcoming() = upcomingRepository.getUpcoming().asLiveData(Dispatchers.IO)

    fun getDataTopRated() = topRatedRepository.getTopRated().asLiveData(Dispatchers.IO)

    fun addToMyList(movie: Movie): LiveData<ResultWrapper<Boolean>> {
        return movie.let {
            myListRepository.insertMovieList(it).asLiveData(Dispatchers.IO)
        }
    }

    fun checkMovieById(movie: Movie): LiveData<ResultWrapper<List<Movie>>> {
        return movie.let {
            myListRepository.checkListById(it.id).asLiveData(Dispatchers.IO)
        }
    }

    fun removeFromMyList(movie: Movie): LiveData<ResultWrapper<Boolean>> {
        return movie.let {
            myListRepository.deleteMovie(it).asLiveData(Dispatchers.IO)
        }
    }
}
