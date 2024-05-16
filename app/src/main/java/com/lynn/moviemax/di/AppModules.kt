package com.lynn.moviemax.di

import com.lynn.moviemax.data.datasource.NowPlayingApiDataSource
import com.lynn.moviemax.data.datasource.NowPlayingDataSource
import com.lynn.moviemax.data.repository.MyListRepository
import com.lynn.moviemax.data.repository.MyListRepositoryImpl
import com.lynn.moviemax.data.repository.NowPlayingRepository
import com.lynn.moviemax.data.repository.NowPlayingRepositoryImpl
import com.lynn.moviemax.data.datasource.mylist.MyListDataSource
import com.lynn.moviemax.data.datasource.mylist.MyListDataSourceImpl
import com.lynn.moviemax.data.source.local.database.AppDatabase
import com.lynn.moviemax.data.source.local.database.dao.MyListDao
import com.lynn.moviemax.data.source.network.service.MovieMaxApiService
import com.lynn.moviemax.presentation.home.HomeViewModel
import com.lynn.moviemax.presentation.mylist.MyListViewModel
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModelOf
import org.koin.core.module.Module
import org.koin.dsl.module

object AppModules {
    private val networkModule = module {
        single<MovieMaxApiService> { MovieMaxApiService.invoke() }
    }
    private val localModule = module {
        single<AppDatabase> { AppDatabase.createInstance(androidContext()) }
        single<MyListDao> { get<AppDatabase>().movieDao() }
    }

    private val datasource = module{
        single<NowPlayingDataSource> { NowPlayingApiDataSource(get()) }
        single<MyListDataSource> { MyListDataSourceImpl(get()) }
    }

    private val repository = module {
        single<NowPlayingRepository> { NowPlayingRepositoryImpl(get()) }
        single<MyListRepository> { MyListRepositoryImpl(get()) }
    }

    private val viewModelModule = module {
        viewModelOf(::HomeViewModel)
        viewModelOf(::MyListViewModel)
    }

    val modules = listOf<Module>(
        networkModule,
        localModule,
        datasource,
        repository,
        viewModelModule
    )
}