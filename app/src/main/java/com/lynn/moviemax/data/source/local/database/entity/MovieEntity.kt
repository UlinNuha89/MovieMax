package com.lynn.moviemax.data.source.local.database.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "myLists")
data class MovieEntity(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo("movie_id")
    var movieId: Int? = null,
    @ColumnInfo("backdrop_path")
    var backdropPath: String? = null,
    @ColumnInfo("overview")
    var overview: String? = null,
    @ColumnInfo("poster_path")
    var posterPath: String? = null,
    @ColumnInfo("release_date")
    var releaseDate: String? = null,
    @ColumnInfo("title")
    var title: String? = null,
    @ColumnInfo("vote_average")
    var voteAverage: Double? = null,
    @ColumnInfo("vote_count")
    var voteCount: Int? = null,
)
