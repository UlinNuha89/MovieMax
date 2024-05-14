package com.lynn.moviemax.data.model

data class Movie(
    var id: Int,
    var title: String,
    var backdropPath: String,
    var overview: String,
    var posterPath: String,
    var releaseDate: String,
    var voteAverage: Double,
    var voteCount: Int
)
