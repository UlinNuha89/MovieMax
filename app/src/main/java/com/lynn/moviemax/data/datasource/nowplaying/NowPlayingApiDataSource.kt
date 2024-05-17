package com.lynn.moviemax.data.datasource.nowplaying

import com.lynn.moviemax.data.source.network.model.MovieResponse
import com.lynn.moviemax.data.source.network.service.MovieMaxApiService

class NowPlayingApiDataSource(private val service: MovieMaxApiService) : NowPlayingDataSource {
    override suspend fun getNowPlaying(): MovieResponse {
        return service.getNowPlaying()
    }
}
