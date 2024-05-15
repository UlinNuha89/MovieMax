package com.lynn.moviemax.di

import com.lynn.moviemax.data.datasource.nowplaying.NowPlayingApiDataSource
import com.lynn.moviemax.data.datasource.nowplaying.NowPlayingDataSource
import com.lynn.moviemax.data.datasource.popular.PopularApiDataSource
import com.lynn.moviemax.data.datasource.popular.PopularDataSource
import com.lynn.moviemax.data.datasource.toprated.TopRatedApiDataSource
import com.lynn.moviemax.data.datasource.toprated.TopRatedDataSource
import com.lynn.moviemax.data.datasource.upcoming.UpcomingApiDataSource
import com.lynn.moviemax.data.datasource.upcoming.UpcomingDataSource
import com.lynn.moviemax.data.repository.NowPlayingRepository
import com.lynn.moviemax.data.repository.NowPlayingRepositoryImpl
import com.lynn.moviemax.data.repository.PopularRepository
import com.lynn.moviemax.data.repository.PopularRepositoryImpl
import com.lynn.moviemax.data.repository.TopRatedRepository
import com.lynn.moviemax.data.repository.TopRatedRepositoryImpl
import com.lynn.moviemax.data.repository.UpcomingRepository
import com.lynn.moviemax.data.repository.UpcomingRepositoryImpl
import com.lynn.moviemax.data.source.network.service.MovieMaxApiService
import com.lynn.moviemax.presentation.home.HomeViewModel
import org.koin.androidx.viewmodel.dsl.viewModelOf
import org.koin.core.module.Module
import org.koin.dsl.module

object AppModules {
    private val networkModule = module {
        single<MovieMaxApiService> { MovieMaxApiService.invoke() }
    }
    private val localModule = module {
    }

    private val datasource = module {
        single<NowPlayingDataSource> { NowPlayingApiDataSource(get()) }
        single<PopularDataSource> { PopularApiDataSource(get()) }
        single<UpcomingDataSource> { UpcomingApiDataSource(get()) }
        single<TopRatedDataSource> { TopRatedApiDataSource(get()) }
    }

    private val repository = module {
        single<NowPlayingRepository> { NowPlayingRepositoryImpl(get()) }
        single<PopularRepository> { PopularRepositoryImpl(get()) }
        single<UpcomingRepository> { UpcomingRepositoryImpl(get()) }
        single<TopRatedRepository> { TopRatedRepositoryImpl(get()) }
    }

    private val viewModelModule = module {
        viewModelOf(::HomeViewModel)

    }

    val modules = listOf<Module>(
        networkModule,
        localModule,
        datasource,
        repository,
        viewModelModule
    )
}