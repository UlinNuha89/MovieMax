package com.lynn.moviemax.data.datasource.popular

import com.lynn.moviemax.data.source.network.model.PopularResponse
import com.lynn.moviemax.data.source.network.service.MovieMaxApiService

class PopularApiDataSource(private val service: MovieMaxApiService) : PopularDataSource {
    override suspend fun getPopular(): PopularResponse {
        return service.getPopular()
    }
}