package com.lynn.moviemax.data.mapper

import com.lynn.moviemax.data.model.Movie
import com.lynn.moviemax.data.source.network.model.itemresponse.MovieItemResponse

fun MovieItemResponse?.toMovie() =
    Movie(
        id = this?.id ?: 0,
        title = this?.title.orEmpty(),
        backdropPath = "https://image.tmdb.org/t/p/w500" + this?.backdropPath.orEmpty(),
        overview = this?.overview.orEmpty(),
        posterPath = "https://image.tmdb.org/t/p/w500" + this?.posterPath.orEmpty(),
        releaseDate = this?.releaseDate.orEmpty(),
        voteAverage = this?.voteAverage ?: 0.0,
        voteCount = this?.voteCount ?: 0,
    )

fun Collection<MovieItemResponse>?.toMovies() =
    this?.map {
        it.toMovie()
    } ?: listOf()
