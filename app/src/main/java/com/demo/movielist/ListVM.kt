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

    private val _test = MutableLiveData<String>()
    val test: LiveData<String>
        get() = _test

    private var viewModelJob = Job()
    private val coroutineScope = CoroutineScope(viewModelJob + Dispatchers.Main)
    private var page = 1

    override fun onCleared() {
        super.onCleared()
        viewModelJob.cancel()
    }

    fun getMovies(search: String) {
        Timber.v("getting movie $search ($page)")
        coroutineScope.launch {
            try {
                val result = Calls().retrofitSearch
                    .find(search, page)
                _movies.value = result.results
                page++
            } catch (e: Exception) {
                Timber.v("Error: %s", e.message)
            }
        }
    }

    fun refreshMovies() {
        _movies.value = mutableListOf()
        page = 1
    }
}