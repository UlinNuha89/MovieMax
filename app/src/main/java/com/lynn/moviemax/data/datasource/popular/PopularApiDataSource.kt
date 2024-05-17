package com.lynn.moviemax.data.datasource.popular

import com.lynn.moviemax.data.source.network.model.MovieResponse
import com.lynn.moviemax.data.source.network.service.MovieMaxApiService

class PopularApiDataSource(private val service: MovieMaxApiService) : PopularDataSource {
    override suspend fun getPopular(): MovieResponse {
        return service.getPopular()
    }
}
