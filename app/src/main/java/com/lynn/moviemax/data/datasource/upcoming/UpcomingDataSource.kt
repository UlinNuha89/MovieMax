package com.lynn.moviemax.data.datasource.upcoming

import com.lynn.moviemax.data.source.network.model.UpcomingResponse

interface UpcomingDataSource {
    suspend fun getUpcoming(): UpcomingResponse
}