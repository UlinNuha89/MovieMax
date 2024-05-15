package com.lynn.moviemax.presentation.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.lynn.moviemax.data.repository.NowPlayingRepository
import com.lynn.moviemax.data.repository.PopularRepository
import kotlinx.coroutines.Dispatchers

class HomeViewModel(private val nowPlayingRepository: NowPlayingRepository,) : ViewModel() {
    fun getDataNowPlaying() = nowPlayingRepository.getNowPlaying().asLiveData(Dispatchers.IO)
}

class PopularViewModel(private val popularRepository: PopularRepository,) : ViewModel() {
    fun getDataPopular() = popularRepository.getPopular().asLiveData(Dispatchers.IO)
}