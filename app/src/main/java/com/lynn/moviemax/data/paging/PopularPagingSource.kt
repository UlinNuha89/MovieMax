package com.lynn.moviemax.data.paging

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.lynn.moviemax.data.mapper.toMovies
import com.lynn.moviemax.data.model.Movie
import com.lynn.moviemax.data.source.network.service.MovieMaxApiService

class PopularPagingSource(private val service: MovieMaxApiService) :
    PagingSource<Int, Movie>() {
    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Movie> {
        return try {
            val position = params.key ?: 1
            val response = service.getPopular(page = position)

            LoadResult.Page(
                data = response.results.toMovies(),
                prevKey = if (position == 1) null else (position - 1),
                nextKey = if (position == response.totalPages) null else (position + 1),
            )
        } catch (e: Exception) {
            LoadResult.Error(e)
        }
    }

    override fun getRefreshKey(state: PagingState<Int, Movie>): Int? {
        return state.anchorPosition?.let {
            state.closestPageToPosition(it)?.prevKey?.plus(1)
                ?: state.closestPageToPosition(it)?.nextKey?.minus(1)
        }
    }
}
