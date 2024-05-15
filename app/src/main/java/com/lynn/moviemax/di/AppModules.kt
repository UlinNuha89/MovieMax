package com.lynn.moviemax.di

import com.lynn.moviemax.data.datasource.NowPlayingApiDataSource
import com.lynn.moviemax.data.datasource.NowPlayingDataSource
import com.lynn.moviemax.data.datasource.popular.PopularApiDataSource
import com.lynn.moviemax.data.datasource.popular.PopularDataSource
import com.lynn.moviemax.data.repository.NowPlayingRepository
import com.lynn.moviemax.data.repository.NowPlayingRepositoryImpl
import com.lynn.moviemax.data.repository.PopularRepository
import com.lynn.moviemax.data.repository.PopularRepositoryImpl
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
    }

    private val repository = module {
        single<NowPlayingRepository> { NowPlayingRepositoryImpl(get()) }
        single<PopularRepository> { PopularRepositoryImpl(get()) }
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