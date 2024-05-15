package com.lynn.moviemax.data.datasource.popular

import com.lynn.moviemax.data.source.network.model.PopularResponse

interface PopularDataSource {
    suspend fun getPopular(): PopularResponse
}