package com.lynn.moviemax.data.datasource.upcoming

import com.lynn.moviemax.data.source.network.model.UpcomingResponse
import com.lynn.moviemax.data.source.network.service.MovieMaxApiService

class UpcomingApiDataSource(private val service: MovieMaxApiService) : UpcomingDataSource {
    override suspend fun getUpcoming(): UpcomingResponse {
        return service.getUpcoming()
    }
}