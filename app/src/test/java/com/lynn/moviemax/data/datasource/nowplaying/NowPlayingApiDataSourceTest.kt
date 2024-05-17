package com.lynn.moviemax.data.datasource.nowplaying

import com.lynn.moviemax.data.source.network.model.MovieResponse
import com.lynn.moviemax.data.source.network.service.MovieMaxApiService
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.impl.annotations.MockK
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test

class NowPlayingApiDataSourceTest {
    @MockK
    lateinit var service: MovieMaxApiService

    private lateinit var npds: NowPlayingDataSource

    @Before
    fun setUp() {
        MockKAnnotations.init(this)
        npds = NowPlayingApiDataSource(service)
    }

    @Test
    fun getNowPlaying() {
        runTest {
            val mockResponse = mockk<MovieResponse>(relaxed = true)
            coEvery { service.getNowPlaying(any()) } returns mockResponse
            val actualResult = npds.getNowPlaying()
            coVerify { service.getNowPlaying(any()) }
            assertEquals(mockResponse, actualResult)
        }
    }
}
