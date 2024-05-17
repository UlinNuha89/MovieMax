package com.lynn.moviemax.data.datasource.toprated

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

class TopRatedApiDataSourceTest {
    @MockK
    lateinit var service: MovieMaxApiService

    private lateinit var trds: TopRatedDataSource

    @Before
    fun setUp() {
        MockKAnnotations.init(this)
        trds = TopRatedApiDataSource(service)
    }

    @Test
    fun getTopRated() {
        runTest {
            val mockResponse = mockk<MovieResponse>(relaxed = true)
            coEvery { service.getTopRated(any()) } returns mockResponse
            val actualResult = trds.getTopRated()
            coVerify { service.getTopRated(any()) }
            assertEquals(mockResponse, actualResult)
        }
    }
}
