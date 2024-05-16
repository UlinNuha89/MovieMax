package com.lynn.moviemax.data.datasource.mylist

import com.lynn.moviemax.data.source.local.database.dao.MyListDao
import com.lynn.moviemax.data.source.local.database.entity.MovieEntity
import kotlinx.coroutines.flow.Flow

interface MyListDataSource {
    fun getAllMovie(): Flow<List<MovieEntity>>
    suspend fun insertMovie(movie : MovieEntity): Long
    suspend fun deleteMovie(movie : MovieEntity): Int
    suspend fun deleteAll()
}

class MyListDataSourceImpl(private val dao : MyListDao) : MyListDataSource {
    override fun getAllMovie(): Flow<List<MovieEntity>> = dao.getAllMovie()
    override suspend fun insertMovie(movie: MovieEntity): Long = dao.insertMovie(movie)
    override suspend fun deleteMovie(movie: MovieEntity): Int = dao.deleteMovie(movie)
    override suspend fun deleteAll() = dao.deleteAll()

}