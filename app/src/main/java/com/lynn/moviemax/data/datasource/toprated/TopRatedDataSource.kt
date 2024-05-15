package com.lynn.moviemax.data.datasource.toprated

import com.lynn.moviemax.data.source.network.model.TopRatedResponse

interface TopRatedDataSource {
    suspend fun getTopRated(): TopRatedResponse
}