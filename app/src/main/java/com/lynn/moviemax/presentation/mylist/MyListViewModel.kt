package com.lynn.moviemax.presentation.mylist

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.lynn.moviemax.data.model.Movie
import com.lynn.moviemax.data.repository.MyListRepository
import com.lynn.moviemax.utils.ResultWrapper
import kotlinx.coroutines.Dispatchers

class MyListViewModel(private val repository: MyListRepository) : ViewModel() {
    fun getMovieList() = repository.getMovieList().asLiveData(Dispatchers.IO)

    fun removeFromMyList(movie: Movie): LiveData<ResultWrapper<Boolean>> {
        return movie.let {
            repository.deleteMovie(it).asLiveData(Dispatchers.IO)
        }
    }

    fun checkMovieById(movie: Movie): LiveData<ResultWrapper<List<Movie>>> {
        return movie.let {
            repository.checkListById(it.id).asLiveData(Dispatchers.IO)
        }
    }

    fun addToMyList(movie: Movie): LiveData<ResultWrapper<Boolean>> {
        return movie.let {
            repository.insertMovieList(it).asLiveData(Dispatchers.IO)
        }
    }
}
