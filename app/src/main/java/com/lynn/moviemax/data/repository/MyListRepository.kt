package com.lynn.moviemax.data.repository

import com.lynn.moviemax.data.datasource.MyListDataSource
import com.lynn.moviemax.data.mapper.toMovieEntity
import com.lynn.moviemax.data.mapper.toMovieList
import com.lynn.moviemax.data.model.Movie
import com.lynn.moviemax.data.model.MyList
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
    fun getMovieList():  Flow<ResultWrapper<List<MyList>>>
    fun createList(movie: MyList): Flow<ResultWrapper<Boolean>>
    fun deleteMovie(item: MyList): Flow<ResultWrapper<Boolean>>
    suspend fun deleteAll(): Flow<ResultWrapper<Boolean>>
}

class MyListRepositoryImpl(
    private val dataSource: MyListDataSource
): MyListRepository{
    override fun getMovieList(): Flow<ResultWrapper<List<MyList>>> {
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
                delay(2000)
            }
    }

    override fun createList(movie: MyList): Flow<ResultWrapper<Boolean>> {
        return movie.id.let {  id ->
            proceedFlow {
                val affectedRow =
                    dataSource.insertMovie(
                        MovieEntity(
                            movieId = id,
                            backdropPath = movie.backdropPath,
                            overview = movie.overview,
                            posterPath = movie.posterPath,
                            releaseDate = movie.releaseDate,
                            title = movie.title,
                            voteAverage = movie.voteAverage,
                            voteCount = movie.voteCount
                        ),
                    )
                delay(2000)
                affectedRow > 0
            }
        }
    }

    override fun deleteMovie(item: MyList): Flow<ResultWrapper<Boolean>> {
        return proceedFlow { dataSource.deleteMovie(item.toMovieEntity()) > 0 }
    }

    override suspend fun deleteAll(): Flow<ResultWrapper<Boolean>> {
        return proceedFlow {
            dataSource.deleteAll()
            true
        }
    }

}