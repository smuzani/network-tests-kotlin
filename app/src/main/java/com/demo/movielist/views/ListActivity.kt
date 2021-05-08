package com.demo.movielist.views

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.demo.movielist.ListVM
import com.demo.movielist.R
import com.demo.movielist.models.Movie
import timber.log.Timber

class ListActivity : AppCompatActivity() {
    lateinit var rv: RecyclerView
    private val viewModel: ListVM by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        rv = findViewById(R.id.rv)
        initObservers()
        initLayout()
    }

    private fun initLayout() {
        val groupLayoutManager = LinearLayoutManager(this)
        val pha = ListAdapter()
        val orientation = LinearLayoutManager.VERTICAL
        val dividerItemDecoration = DividerItemDecoration(this, orientation)
        rv.apply {
            layoutManager = groupLayoutManager
            adapter = pha
            itemAnimator = null
            addItemDecoration(dividerItemDecoration)
        }
        val dummy = mutableListOf<Movie>()
        dummy.add(Movie(640810, "DC Showcase Original Shorts Collection", "An anthology of DC Showcase stories consisting of a new Superman/Shazam feature and extended versions of older shorts.", "/pYSkfX3ISUUpkNHbswHC8osWH4P.jpg", 	8f, "2010-11-09"))
        dummy.add(Movie(640810, "DC Showcase Original Shorts Collection", "An anthology of DC Showcase stories consisting of a new Superman/Shazam feature and extended versions of older shorts.", "/pYSkfX3ISUUpkNHbswHC8osWH4P.jpg", 	8f, "2010-11-09"))
        dummy.add(Movie(640810, "DC Showcase Original Shorts Collection", "An anthology of DC Showcase stories consisting of a new Superman/Shazam feature and extended versions of older shorts.", "/pYSkfX3ISUUpkNHbswHC8osWH4P.jpg", 	8f, "2010-11-09"))
        pha.addAll(dummy)
    }

    private fun initObservers() {

    }


}