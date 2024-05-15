package com.lynn.moviemax.data.repository

import com.lynn.moviemax.data.datasource.popular.PopularDataSource
import com.lynn.moviemax.data.mapper.toPopulars
import com.lynn.moviemax.data.model.Movie
import com.lynn.moviemax.utils.ResultWrapper
import com.lynn.moviemax.utils.proceedFlow
import kotlinx.coroutines.flow.Flow

interface PopularRepository {
    fun getPopular(): Flow<ResultWrapper<List<Movie>>>
}

class PopularRepositoryImpl(private val dataSource: PopularDataSource) : PopularRepository {
    override fun getPopular(): Flow<ResultWrapper<List<Movie>>> {
        return proceedFlow {
            dataSource.getPopular().results.toPopulars()
        }
    }

}