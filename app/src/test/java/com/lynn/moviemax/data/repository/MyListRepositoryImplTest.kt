package com.lynn.moviemax.data.repository

import app.cash.turbine.test
import com.lynn.moviemax.data.datasource.mylist.MyListDataSource
import com.lynn.moviemax.data.source.local.database.entity.MovieEntity
import com.lynn.moviemax.utils.ResultWrapper
import io.mockk.MockKAnnotations
import io.mockk.every
import io.mockk.impl.annotations.MockK
import io.mockk.verify
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.test.runTest
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test

class MyListRepositoryImplTest {
    @MockK
    lateinit var ds: MyListDataSource

    private lateinit var repo: MyListRepository

    @Before
    fun setUp() {
        MockKAnnotations.init(this)
        repo = MyListRepositoryImpl(ds)
    }

    @Test
    fun getMovieList_success() {
        val entity1 =
            MovieEntity(
                1,
                "Title 1",
                "Overview 1",
                "Poster 1",
                "Backdrop 1",
                "2024-05-17",
                7.5,
                100,
            )
        val entity2 =
            MovieEntity(
                2,
                "Title 2",
                "Overview 2",
                "Poster 2",
                "Backdrop 2",
                "2024-05-18",
                8.0,
                150,
            )
        val mockList = listOf(entity1, entity2)
        val mockFlow = flow { emit(mockList) }
        every { ds.getAllMovie() } returns mockFlow
        runTest {
            repo.getMovieList().map {
                delay(100)
                it
            }.test {
                delay(1201)
                val actualData = expectMostRecentItem()
                assertTrue(actualData is ResultWrapper.Success)
                assertEquals(mockList.size, actualData.payload?.size)
                verify { ds.getAllMovie() }
            }
        }
    }

    @Test
    fun getMovieList_loading() {
        val entity1 =
            MovieEntity(
                1,
                "Title 1",
                "Overview 1",
                "Poster 1",
                "Backdrop 1",
                "2024-05-17",
                7.5,
                100,
            )
        val entity2 =
            MovieEntity(
                2,
                "Title 2",
                "Overview 2",
                "Poster 2",
                "Backdrop 2",
                "2024-05-18",
                8.0,
                150,
            )
        val mockList = listOf(entity1, entity2)
        val mockFlow = flow { emit(mockList) }
        every { ds.getAllMovie() } returns mockFlow
        runTest {
            repo.getMovieList().map {
                delay(100)
                it
            }.test {
                delay(210)
                val actualData = expectMostRecentItem()
                assertTrue(actualData is ResultWrapper.Loading)
                verify { ds.getAllMovie() }
            }
        }
    }

    @Test
    fun insertMovieList() {
    }

    @Test
    fun checkListById() {
    }

    @Test
    fun deleteMovie() {
    }
}
