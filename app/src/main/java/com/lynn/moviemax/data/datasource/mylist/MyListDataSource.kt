package com.lynn.moviemax.data.datasource.mylist

import com.lynn.moviemax.data.source.local.database.dao.MyListDao
import com.lynn.moviemax.data.source.local.database.entity.MovieEntity
import kotlinx.coroutines.flow.Flow

interface MyListDataSource {
    fun getAllMovie(): Flow<List<MovieEntity>>

    fun checkListById(movieId: Int?): Flow<List<MovieEntity>>

    suspend fun insertMovie(movie: MovieEntity): Long

    suspend fun deleteMovie(movie: MovieEntity): Int
}

class MyListDataSourceImpl(private val dao: MyListDao) : MyListDataSource {
    override fun getAllMovie(): Flow<List<MovieEntity>> = dao.getAllMovie()

    override fun checkListById(movieId: Int?): Flow<List<MovieEntity>> = dao.checkListById(movieId)

    override suspend fun insertMovie(movie: MovieEntity): Long = dao.insertMovie(movie)

    override suspend fun deleteMovie(movie: MovieEntity): Int = dao.deleteMovie(movie)
}
