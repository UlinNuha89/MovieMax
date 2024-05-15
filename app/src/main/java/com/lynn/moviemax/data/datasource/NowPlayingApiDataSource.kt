package com.lynn.moviemax.data.datasource

import com.lynn.moviemax.data.source.network.model.NowPlayingResponse
import com.lynn.moviemax.data.source.network.service.MovieMaxApiService

class NowPlayingApiDataSource(private val service : MovieMaxApiService) :NowPlayingDataSource{
    override suspend fun getNowPlaying(): NowPlayingResponse {
        return service.getNowPlaying()
    }
}