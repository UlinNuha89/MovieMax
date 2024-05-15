package com.lynn.moviemax.data.datasource

import com.lynn.moviemax.data.source.network.model.NowPlayingResponse

interface NowPlayingDataSource {
    suspend fun getNowPlaying(): NowPlayingResponse
}