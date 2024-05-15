package com.lynn.moviemax.data.source.network.model

import com.google.gson.annotations.SerializedName
import com.lynn.moviemax.data.source.network.model.itemresponse.TopRatedItemResponse

data class TopRatedResponse(
    @SerializedName("page")
    var page: Int? = null,
    @SerializedName("results")
    var results: List<TopRatedItemResponse>?,
    @SerializedName("total_pages")
    var totalPages: Int? = null,
    @SerializedName("total_results")
    var totalResults: Int? = null
)