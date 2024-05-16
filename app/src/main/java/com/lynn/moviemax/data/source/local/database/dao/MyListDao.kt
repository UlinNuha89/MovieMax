package com.lynn.moviemax.data.source.local.database.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.lynn.moviemax.data.source.local.database.entity.MovieEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface MyListDao{
    @Query("SELECT * FROM myLists")
    fun getAllMovie(): Flow<List<MovieEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertMovie(myList: MovieEntity): Long

    @Delete
    suspend fun deleteMovie(myList: MovieEntity): Int

    @Query("Delete FROM myLists")
    suspend fun deleteAll()
}