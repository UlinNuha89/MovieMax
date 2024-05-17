package com.lynn.moviemax.data.datasource.upcoming

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

class UpcomingApiDataSourceTest {
    @MockK
    lateinit var service: MovieMaxApiService

    private lateinit var uds: UpcomingDataSource

    @Before
    fun setUp() {
        MockKAnnotations.init(this)
        uds = UpcomingApiDataSource(service)
    }

    @Test
    fun getUpcoming() {
        runTest {
            val mockResponse = mockk<MovieResponse>(relaxed = true)
            coEvery { service.getUpcoming(any()) } returns mockResponse
            val actualResult = uds.getUpcoming()
            coVerify { service.getUpcoming(any()) }
            assertEquals(mockResponse, actualResult)
        }
    }
}
