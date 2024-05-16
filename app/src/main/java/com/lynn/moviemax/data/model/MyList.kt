package com.lynn.moviemax.data.model

data class MyList(
    var id: Int? = null,
    var movieId: Int,
    var title: String,
    var backdropPath: String,
    var overview: String,
    var posterPath: String,
    var releaseDate: String,
    var voteAverage: Double,
    var voteCount: Int
)
