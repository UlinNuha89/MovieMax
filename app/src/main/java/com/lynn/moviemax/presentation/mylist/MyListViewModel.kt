package com.lynn.moviemax.presentation.mylist

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.liveData
import androidx.lifecycle.viewModelScope
import com.lynn.moviemax.data.model.Movie
import com.lynn.moviemax.data.model.MyList
import com.lynn.moviemax.data.repository.MyListRepository
import com.lynn.moviemax.utils.ResultWrapper
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import java.lang.IllegalStateException

class MyListViewModel(private val repository: MyListRepository) : ViewModel() {
    fun getMovieList() = repository.getMovieList().asLiveData(Dispatchers.IO)

    fun removeMovie(item: MyList) {
       viewModelScope.launch(Dispatchers.IO) {
//            repository.deleteMovie(item).collect()
           repository.deleteAll().collect()
        }

    }

    fun mockMovie(): MyList {
        return MyList(
            id = 2,
            movieId = 823464,
            title = "Godzilla x Kong: The New Empire",
            backdropPath = "/lLh39Th5plbrQgbQ4zyIULsd0Pp.jpg",
            overview = "Following their explosive showdown, Godzilla and Kong must reunite against a colossal undiscovered threat hidden within our world, challenging their very existence – and our own.",
            posterPath = "/tMefBSflR6PGQLv7WvFPpKLZkyk.jpg",
            releaseDate = "2024-03-27",
            voteAverage = 6.941,
            voteCount = 1353
        )
    }

    fun addToMylist(): LiveData<ResultWrapper<Boolean>> {
        return mockMovie().let {
            repository.createList(it).asLiveData(Dispatchers.IO)
        } ?: liveData { emit(ResultWrapper.Error(IllegalStateException("Product not found"))) }
    }
}