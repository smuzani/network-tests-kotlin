package com.demo.movielist.network

import com.demo.movielist.model.MovieSearchApi
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.POST
import retrofit2.http.Query

interface MovieSearch {
    @Headers("Content-Type: application/json")
    @GET("search/movie")
    suspend fun find(
        @Query("query") query: String,
        @Query("page") page: Int,
        @Query("api_key") apiKey: String = "6753d9119b9627493ae129f3c3c99151"
    ): MovieSearchApi
}