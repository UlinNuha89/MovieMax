package com.lynn.moviemax.data.datasource.popular

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

class PopularApiDataSourceTest {
    @MockK
    lateinit var service: MovieMaxApiService

    private lateinit var pds: PopularDataSource

    @Before
    fun setUp() {
        MockKAnnotations.init(this)
        pds = PopularApiDataSource(service)
    }

    @Test
    fun getPopular() {
        runTest {
            val mockResponse = mockk<MovieResponse>(relaxed = true)
            coEvery { service.getPopular(any()) } returns mockResponse
            val actualResult = pds.getPopular()
            coVerify { service.getPopular(any()) }
            assertEquals(mockResponse, actualResult)
        }
    }
}
