package com.demo.movielist

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.demo.movielist.model.Movie
import com.demo.movielist.network.Calls
import com.demo.movielist.network.NConfig
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import timber.log.Timber

class ListVM: ViewModel() {

    private val _movies = MutableLiveData<List<Movie>>()
    val movies: LiveData<List<Movie>>
        get() = _movies

    // track the page here
    // Note: pagination is a bit hacky but suits our purposes.
    // For production, consider https://developer.android.com/topic/libraries/architecture/paging/v3-overview
    private var page = 1
    private var lastPage = 1

    // Handles coroutines (async) logic
    private var viewModelJob = Job()
    private val coroutineScope = CoroutineScope(viewModelJob + Dispatchers.Main)

    /**
     * Cancels all jobs if app is destroyed
     */
    override fun onCleared() {
        super.onCleared()
        viewModelJob.cancel()
    }

    init {
        _movies.value = mutableListOf() // initializes it so it can append
    }

    /**
     * Searches for the movies
     */
    fun getMovies(search: String) {
        // don't go past the last page
        if (page > lastPage) return

        Timber.v("getting movie $search ($page)")
        coroutineScope.launch {
            try {
                val result = Calls().retrofitSearch
                    .find(search, page)
                page++
                _movies.value = _movies.value?.plus(result.results)
                lastPage = result.total_pages
            } catch (e: Exception) {
                Timber.v("Error: %s", e.message)
            }
        }
    }

    /**
     * Refreshes page data
     */
    fun refreshMovies(search: String) {
        _movies.value = mutableListOf()
        page = 1
//        getMovies(search)
    }
}