package com.lynn.moviemax.data.datasource.mylist

import app.cash.turbine.test
import com.lynn.moviemax.data.source.local.database.dao.MyListDao
import com.lynn.moviemax.data.source.local.database.entity.MovieEntity
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.every
import io.mockk.impl.annotations.MockK
import io.mockk.mockk
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

class MyListDataSourceImplTest {
    @MockK
    lateinit var dao: MyListDao

    private lateinit var myListDataSource: MyListDataSource

    @Before
    fun setUp() {
        MockKAnnotations.init(this)
        myListDataSource = MyListDataSourceImpl(dao)
    }

    @Test
    fun getAllMovie() {
        val entity1 = mockk<MovieEntity>()
        val entity2 = mockk<MovieEntity>()
        val listEntity = listOf(entity1, entity2)
        val mockFlow =
            flow {
                emit(listEntity)
            }
        every { dao.getAllMovie() } returns mockFlow
        runTest {
            myListDataSource.getAllMovie().test {
                val result = awaitItem()
                assertEquals(listEntity.size, result.size)
                assertEquals(entity1, result[0])
                assertEquals(entity2, result[1])
                awaitComplete()
            }
        }
    }

    @Test
    fun checkListById() {
        val movieId = 123
        val entity1 = mockk<MovieEntity>()
        val entity2 = mockk<MovieEntity>()
        val listEntity = listOf(entity1, entity2)
        val mockFlow =
            flow {
                emit(listEntity)
            }
        every { dao.checkListById(movieId) } returns mockFlow
        runTest {
            myListDataSource.checkListById(movieId).test {
                val result = awaitItem()
                assertEquals(listEntity.size, result.size)
                assertEquals(entity1, result[0])
                assertEquals(entity2, result[1])
                awaitComplete()
            }
        }
    }

    @Test
    fun insertMovie() {
        runTest {
            val movieEntity = mockk<MovieEntity>()
            coEvery { dao.insertMovie(movieEntity) } returns 1
            val result = myListDataSource.insertMovie(movieEntity)
            assertEquals(1, result)
        }
    }

    @Test
    fun deleteMovie() {
        runTest {
            val movieEntity = mockk<MovieEntity>()
            coEvery { dao.deleteMovie(movieEntity) } returns 1
            val result = myListDataSource.deleteMovie(movieEntity)
            assertEquals(1, result)
        }
    }
}
