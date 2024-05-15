package com.lynn.moviemax.data.datasource.toprated

import com.lynn.moviemax.data.source.network.model.TopRatedResponse
import com.lynn.moviemax.data.source.network.service.MovieMaxApiService

class TopRatedApiDataSource(private val service: MovieMaxApiService) : TopRatedDataSource {
    override suspend fun getTopRated(): TopRatedResponse {
        return service.getTopRated()
    }
}