package com.demo.movielist.views

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.demo.movielist.R
import com.demo.movielist.model.Movie
import com.google.gson.Gson

class ListAdapter :
    RecyclerView.Adapter<ListAdapter.ListHolder>() {

    private var movies: MutableList<Movie> = ArrayList()

    /**
     * Put the ViewHolder inner for easy reading
     */
    inner class ListHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val ivPoster: ImageView = itemView.findViewById(R.id.poster)
        private val tvTitle: TextView = itemView.findViewById(R.id.title)
        private val tvYear: TextView = itemView.findViewById(R.id.year)
        private val tvDescription: TextView = itemView.findViewById(R.id.description)

        /**
         * Binds data to UI
         */
        fun bind(data: Movie) {
            val ctx = itemView.context
            if (!data.poster_path.isNullOrBlank()) {
                val path = "https://image.tmdb.org/t/p/w92${data.poster_path}"
                Glide.with(ctx)
                    .load(path)
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .into(ivPoster)
            } else {
                Glide.with(ctx)
                    .load(R.drawable.ic_placeholder_poster)
                    .into(ivPoster)
            }
            tvTitle.text = data.title
            tvYear.text = data.release_date
            tvDescription.text = data.overview
            itemView.setOnClickListener {
                // Convert the data class into a string and passes that string instead of doing some weird Parcelable thing
                val dataPassed = Gson().toJson(data)
                val intent = Intent(ctx, MovieDetailsActivity::class.java)
                intent.putExtra(MovieDetailsActivity.DATA, dataPassed)
                ctx.startActivity(intent)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_movie, parent, false)
        return ListHolder(view)
    }

    override fun getItemCount(): Int {
        return movies.size
    }

    override fun onBindViewHolder(holder: ListHolder, position: Int) {
        holder.bind(movies[position])
    }

    fun set(payments: List<Movie>) {
        movies = payments as MutableList<Movie>
        notifyDataSetChanged()
    }
}