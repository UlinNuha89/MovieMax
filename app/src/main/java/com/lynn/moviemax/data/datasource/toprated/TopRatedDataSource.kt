package com.lynn.moviemax.data.datasource.toprated

import com.lynn.moviemax.data.source.network.model.MovieResponse

interface TopRatedDataSource {
    suspend fun getTopRated(): MovieResponse
}
