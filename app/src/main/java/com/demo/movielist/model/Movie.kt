package com.demo.movielist.model

data class Movie(
//    val backdrop_path: String,
    val id: Int,
    val title: String,
    val overview: String,
    val poster_path: String,
    val vote_average: Float,
    val release_date: String
)
