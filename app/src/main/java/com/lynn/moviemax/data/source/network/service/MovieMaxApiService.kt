package com.lynn.moviemax.data.source.network.service

import com.lynn.moviemax.BuildConfig
import com.lynn.moviemax.data.source.network.model.NowPlayingResponse
import com.lynn.moviemax.data.source.network.model.PopularResponse
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Query
import java.util.concurrent.TimeUnit

interface MovieMaxApiService {

    @Headers(
        "Accept: application/json",
        "Authorization: ${BuildConfig.AUTHORIZATION}",
    )
    @GET("now_playing")
    suspend fun getNowPlaying(
        @Query("language") language: String = "id",
        @Query("page") page: Int = 1
    ): NowPlayingResponse

    @GET("popular")
    suspend fun getPopular(
        @Query("language") language: String = "id",
        @Query("page") page: Int = 1
    ): PopularResponse

    companion object {
        @JvmStatic
        operator fun invoke(): MovieMaxApiService {
            val okHttpClient =
                OkHttpClient.Builder()
                    .connectTimeout(120, TimeUnit.SECONDS)
                    .readTimeout(120, TimeUnit.SECONDS)
                    .build()
            val retrofit =
                Retrofit.Builder()
                    .baseUrl(BuildConfig.BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .client(okHttpClient)
                    .build()
            return retrofit.create(MovieMaxApiService::class.java)
        }
    }
}