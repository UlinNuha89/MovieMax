package com.lynn.moviemax.data.repository

import com.lynn.moviemax.data.datasource.toprated.TopRatedDataSource
import com.lynn.moviemax.data.mapper.toMovies
import com.lynn.moviemax.data.model.Movie
import com.lynn.moviemax.utils.ResultWrapper
import com.lynn.moviemax.utils.proceedFlow
import kotlinx.coroutines.flow.Flow

interface TopRatedRepository {
    fun getTopRated(): Flow<ResultWrapper<List<Movie>>>
}

class TopRatedRepositoryImpl(private val dataSource: TopRatedDataSource) : TopRatedRepository {
    override fun getTopRated(): Flow<ResultWrapper<List<Movie>>> {
        return proceedFlow {
            dataSource.getTopRated().results.toMovies()
        }
    }
}
