package com.lynn.moviemax.data.repository

import com.lynn.moviemax.data.datasource.upcoming.UpcomingDataSource
import com.lynn.moviemax.data.mapper.toUpcomings
import com.lynn.moviemax.data.model.Movie
import com.lynn.moviemax.utils.ResultWrapper
import com.lynn.moviemax.utils.proceedFlow
import kotlinx.coroutines.flow.Flow

interface UpcomingRepository {
    fun getUpcoming(): Flow<ResultWrapper<List<Movie>>>
}

class UpcomingRepositoryImpl(private val dataSource: UpcomingDataSource) : UpcomingRepository {
    override fun getUpcoming(): Flow<ResultWrapper<List<Movie>>> {
        return proceedFlow {
            dataSource.getUpcoming().results.toUpcomings()
        }
    }
}