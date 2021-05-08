package com.demo.movielist.views

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.demo.movielist.R
import com.demo.movielist.model.Movie
import com.google.gson.Gson

class MovieDetailsActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.movie_details)
        initLayout()
    }

    private fun initLayout() {
        val dataString = intent.getStringExtra(DATA)
        val movie = Gson().fromJson(dataString, Movie::class.java)
        val ivPoster: ImageView = findViewById(R.id.poster)
        val tvTitle: TextView = findViewById(R.id.title)
        val tvYear: TextView = findViewById(R.id.year)
        val tvDescription: TextView = findViewById(R.id.description)
        val tvRating: TextView = findViewById(R.id.rating)

        if (!movie.poster_path.isNullOrBlank()) {
            val path = "https://image.tmdb.org/t/p/w185${movie.poster_path}"
            Glide.with(this)
                .load(path)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(ivPoster)
        }
        tvTitle.text = movie.title
        tvYear.text = movie.release_date
        tvDescription.text = movie.overview
        val ratingText = "${movie.vote_average}/10"
        tvRating.text = ratingText
    }

    companion object {
        const val DATA = "data"
    }
}