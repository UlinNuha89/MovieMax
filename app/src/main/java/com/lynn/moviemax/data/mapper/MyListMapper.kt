package com.lynn.moviemax.data.mapper

import com.lynn.moviemax.data.model.Movie
import com.lynn.moviemax.data.source.local.database.entity.MovieEntity

fun Movie?.toMovieEntity() =
    MovieEntity(
        movieId = this?.id ?: 0,
        title = this?.title.orEmpty(),
        backdropPath = "https://image.tmdb.org/t/p/w500" + this?.backdropPath.orEmpty(),
        overview = this?.overview.orEmpty(),
        posterPath = "https://image.tmdb.org/t/p/w500" + this?.posterPath.orEmpty(),
        releaseDate = this?.releaseDate.orEmpty(),
        voteAverage = this?.voteAverage ?: 0.0,
        voteCount = this?.voteCount ?: 0,
    )

fun MovieEntity?.toMovie() =
    Movie(
        id = this?.movieId ?: 0,
        title = this?.title.orEmpty(),
        backdropPath = "https://image.tmdb.org/t/p/w500" + this?.backdropPath.orEmpty(),
        overview = this?.overview.orEmpty(),
        posterPath = "https://image.tmdb.org/t/p/w500" + this?.posterPath.orEmpty(),
        releaseDate = this?.releaseDate.orEmpty(),
        voteAverage = this?.voteAverage ?: 0.0,
        voteCount = this?.voteCount ?: 0,
    )

fun List<MovieEntity?>.toMovieList() = this.map { it.toMovie() }
