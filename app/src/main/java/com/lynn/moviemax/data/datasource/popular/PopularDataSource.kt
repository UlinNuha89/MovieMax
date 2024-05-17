package com.lynn.moviemax.data.datasource.popular

import com.lynn.moviemax.data.source.network.model.MovieResponse

interface PopularDataSource {
    suspend fun getPopular(): MovieResponse
}
