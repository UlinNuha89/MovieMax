package com.lynn.moviemax.data.repository

import com.lynn.moviemax.data.datasource.NowPlayingDataSource
import com.lynn.moviemax.data.mapper.toNowPlayings
import com.lynn.moviemax.data.model.Movie
import com.lynn.moviemax.utils.ResultWrapper
import com.lynn.moviemax.utils.proceedFlow
import kotlinx.coroutines.flow.Flow

interface NowPlayingRepository {
    fun getNowPlaying(): Flow<ResultWrapper<List<Movie>>>
}
class NowPlayingRepositoryImpl(private val dataSource: NowPlayingDataSource) :NowPlayingRepository{
    override fun getNowPlaying(): Flow<ResultWrapper<List<Movie>>> {
        return proceedFlow {
            dataSource.getNowPlaying().results.toNowPlayings()
        }
    }

}