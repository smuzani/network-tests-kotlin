package com.demo.movielist.network

class Calls {
    val retrofitSearch: MovieSearch by lazy {
        NConfig().retrofit.create(MovieSearch::class.java)
    }
}
