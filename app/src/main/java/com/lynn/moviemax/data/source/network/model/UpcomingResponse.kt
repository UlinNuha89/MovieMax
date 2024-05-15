package com.lynn.moviemax.data.source.network.model

import com.google.gson.annotations.SerializedName
import com.lynn.moviemax.data.source.network.model.itemresponse.UpcomingItemResponse

data class UpcomingResponse(
    @SerializedName("page")
    var page: Int? = null,
    @SerializedName("results")
    var results: List<UpcomingItemResponse>?,
    @SerializedName("total_pages")
    var totalPages: Int? = null,
    @SerializedName("total_results")
    var totalResults: Int? = null
)