package com.lynn.moviemax.data.datasource.upcoming

import com.lynn.moviemax.data.source.network.model.MovieResponse

interface UpcomingDataSource {
    suspend fun getUpcoming(): MovieResponse
}