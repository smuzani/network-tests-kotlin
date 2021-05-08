package com.demo.movielist.model

data class MovieSearchApi(
    val page: Int,
    val results: List<Movie>,
    val total_pages: Int
)
