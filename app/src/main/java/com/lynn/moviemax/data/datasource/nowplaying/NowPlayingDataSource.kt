package com.lynn.moviemax.data.datasource.nowplaying

import com.lynn.moviemax.data.source.network.model.MovieResponse

interface NowPlayingDataSource {
    suspend fun getNowPlaying(): MovieResponse
}
