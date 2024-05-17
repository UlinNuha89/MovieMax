package com.lynn.moviemax.data.repository

import com.lynn.moviemax.data.datasource.mylist.MyListDataSource
import com.lynn.moviemax.data.mapper.toMovieEntity
import com.lynn.moviemax.data.mapper.toMovieList
import com.lynn.moviemax.data.model.Movie
import com.lynn.moviemax.data.source.local.database.entity.MovieEntity
import com.lynn.moviemax.utils.ResultWrapper
import com.lynn.moviemax.utils.proceed
import com.lynn.moviemax.utils.proceedFlow
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onStart

interface MyListRepository {
    fun getMovieList(): Flow<ResultWrapper<List<Movie>>>

    fun insertMovieList(item: Movie): Flow<ResultWrapper<Boolean>>

    fun checkListById(movieId: Int?): Flow<ResultWrapper<List<Movie>>>

    fun deleteMovie(item: Movie): Flow<ResultWrapper<Boolean>>
}

class MyListRepositoryImpl(
    private val dataSource: MyListDataSource,
) : MyListRepository {
    override fun getMovieList(): Flow<ResultWrapper<List<Movie>>> {
        return dataSource.getAllMovie()
            .map {
                proceed {
                    it.toMovieList()
                }
            }.map {
                if (it.payload?.isEmpty() == false) return@map it
                ResultWrapper.Empty(it.payload)
            }.catch {
                emit(ResultWrapper.Error(Exception(it)))
            }.onStart {
                emit(ResultWrapper.Loading())
                delay(500)
            }
    }

    override fun insertMovieList(item: Movie): Flow<ResultWrapper<Boolean>> {
        return item.id.let { id ->
            proceedFlow {
                val affectedRow =
                    dataSource.insertMovie(
                        MovieEntity(
                            movieId = id,
                            backdropPath = item.backdropPath,
                            overview = item.overview,
                            posterPath = item.posterPath,
                            releaseDate = item.releaseDate,
                            title = item.title,
                            voteAverage = item.voteAverage,
                            voteCount = item.voteCount,
                        ),
                    )
                affectedRow > 0
            }.onStart {
                emit(ResultWrapper.Loading())
                delay(1000)
            }
        }
    }

    override fun checkListById(movieId: Int?): Flow<ResultWrapper<List<Movie>>> {
        return dataSource.checkListById(movieId)
            .map {
                proceed {
                    it.toMovieList()
                }
            }.map {
                if (it.payload?.isEmpty() == false) return@map it
                ResultWrapper.Empty(it.payload)
            }.catch {
                emit(ResultWrapper.Error(Exception(it)))
            }
    }

    override fun deleteMovie(item: Movie): Flow<ResultWrapper<Boolean>> {
        return item.id.let { id ->
            proceedFlow {
                val affectedRow = dataSource.deleteMovie(item.toMovieEntity())
                affectedRow > 0
            }.onStart {
                emit(ResultWrapper.Loading())
                delay(1000)
            }
        }
    }
}
