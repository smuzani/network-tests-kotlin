package com.demo.movielist.views

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.demo.movielist.ListVM
import com.demo.movielist.R
import com.demo.movielist.utils.InfiniteScrollListener
import timber.log.Timber

class ListActivity : AppCompatActivity() {
    private lateinit var rv: RecyclerView
    private lateinit var swipeRefresh: SwipeRefreshLayout
    private val viewModel: ListVM by viewModels()
    private val pha = ListAdapter()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        initObservers()
        initLayout()
        viewModel.getMovies(QUERY)
    }

    /**
     * Initializes the observers for responses from viewModel
     */
    private fun initObservers() {
        viewModel.movies.observe(this, { result ->
            Timber.v("results: $result")
            swipeRefresh.isRefreshing = false // clears refresh UI, if any
            pha.set(result)
        })
    }

    /**
     * Initializes the UI
     */
    private fun initLayout() {
        rv = findViewById(R.id.rv)
        swipeRefresh = findViewById(R.id.swiperefresh)

        val groupLayoutManager = LinearLayoutManager(this)
        val orientation = LinearLayoutManager.VERTICAL
        val dividerItemDecoration = DividerItemDecoration(this, orientation)
        rv.apply {
            layoutManager = groupLayoutManager
            adapter = pha
            itemAnimator = null
            addItemDecoration(dividerItemDecoration)
            addOnScrollListener(object : InfiniteScrollListener(groupLayoutManager) {
                override fun onLoadMore(current_page: Int) {
                    viewModel.getMovies(QUERY)
                }
            })
        }

        swipeRefresh.setOnRefreshListener {
            viewModel.refreshMovies()
        }
    }

    companion object {
        const val QUERY = "superman"
    }
}