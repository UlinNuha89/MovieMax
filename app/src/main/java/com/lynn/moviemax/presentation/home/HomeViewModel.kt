package com.lynn.moviemax.presentation.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.lynn.moviemax.data.repository.NowPlayingRepository
import com.lynn.moviemax.data.repository.PopularRepository
import com.lynn.moviemax.data.repository.TopRatedRepository
import com.lynn.moviemax.data.repository.UpcomingRepository
import kotlinx.coroutines.Dispatchers

class HomeViewModel(
    private val nowPlayingRepository: NowPlayingRepository,
    private val popularRepository: PopularRepository,
    private val upcomingRepository: UpcomingRepository,
    private val topRatedRepository: TopRatedRepository,
) : ViewModel() {
    fun getDataNowPlaying() = nowPlayingRepository.getNowPlaying().asLiveData(Dispatchers.IO)
    fun getDataPopular() = popularRepository.getPopular().asLiveData(Dispatchers.IO)
    fun getDataUpcoming() = upcomingRepository.getUpcoming().asLiveData(Dispatchers.IO)
    fun getDataTopRated() = topRatedRepository.getTopRated().asLiveData(Dispatchers.IO)
}