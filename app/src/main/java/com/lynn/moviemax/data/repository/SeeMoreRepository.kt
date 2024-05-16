package com.lynn.moviemax.data.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.lynn.moviemax.data.model.Movie
import com.lynn.moviemax.data.paging.NowPlayingPagingSource
import com.lynn.moviemax.data.paging.PopularPagingSource
import com.lynn.moviemax.data.paging.TopRatedPagingSource
import com.lynn.moviemax.data.paging.UpComingPagingSource
import com.lynn.moviemax.data.source.network.service.MovieMaxApiService
import kotlinx.coroutines.flow.Flow


interface SeeMoreRepository {
    fun getNowPlayingList(): Flow<PagingData<Movie>>
    fun getUpComingList(): Flow<PagingData<Movie>>
    fun getTopRatedList(): Flow<PagingData<Movie>>
    fun getPopularList(): Flow<PagingData<Movie>>
}

class SeeMoreRepositoryImpl(private val service: MovieMaxApiService) : SeeMoreRepository {
    override fun getNowPlayingList(): Flow<PagingData<Movie>> {
        return Pager(
            pagingSourceFactory = { NowPlayingPagingSource(service) },
            config = PagingConfig(pageSize = 5)
        ).flow
    }

    override fun getUpComingList(): Flow<PagingData<Movie>> {
        return Pager(
                pagingSourceFactory = { UpComingPagingSource(service) },
        config = PagingConfig(pageSize = 5)
        ).flow
    }

    override fun getTopRatedList(): Flow<PagingData<Movie>> {
       return Pager(
           pagingSourceFactory = { TopRatedPagingSource(service) },
           config = PagingConfig(pageSize = 5)
       ).flow
    }

    override fun getPopularList(): Flow<PagingData<Movie>> {
        return Pager(
            pagingSourceFactory = { PopularPagingSource(service) },
            config = PagingConfig(pageSize = 5)
        ).flow
    }
}