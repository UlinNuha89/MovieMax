package com.lynn.moviemax.data.datasource.upcoming

import com.lynn.moviemax.data.source.network.model.MovieResponse
import com.lynn.moviemax.data.source.network.service.MovieMaxApiService

class UpcomingApiDataSource(private val service: MovieMaxApiService) : UpcomingDataSource {
    override suspend fun getUpcoming(): MovieResponse {
        return service.getUpcoming()
    }
}
