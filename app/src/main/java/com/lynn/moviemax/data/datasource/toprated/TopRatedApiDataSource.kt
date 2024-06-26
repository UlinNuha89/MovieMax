package com.lynn.moviemax.data.datasource.toprated

import com.lynn.moviemax.data.source.network.model.MovieResponse
import com.lynn.moviemax.data.source.network.service.MovieMaxApiService

class TopRatedApiDataSource(private val service: MovieMaxApiService) : TopRatedDataSource {
    override suspend fun getTopRated(): MovieResponse {
        return service.getTopRated()
    }
}
