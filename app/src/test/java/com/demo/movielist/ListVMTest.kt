package com.demo.movielist

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.demo.movielist.model.Movie
import com.nhaarman.mockitokotlin2.eq
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.verify
import org.junit.Assert
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class ListVMTest {
    @get:Rule
    val taskExecutorRule = InstantTaskExecutorRule()

    private lateinit var viewModel: ListVM

    private val data = listOf(
        Movie(1, "Title", "lorem ipsum whatever overview", "/2DtPSyODKWXluIRV7PVru0SSzja.jpg", 6.9f, "12/20/2020"),
        Movie(2, "Title 2", "lorem ipsum whatever overview 2", "", 1.0f, "13/20/2020"),
        Movie(3, "Title 3", "lorem ipsum whatever overview 3", "/2DtPSyODKWXluIRV7PVru0SSzja.jpg", 0f, "14/20/2020"),
        Movie(4, "Title 4", "lorem ipsum whatever overview 4", "/2DtPSyODKWXluIRV7PVru0SSzja.jpg", 3.0f, "15/20/2020")
    )

    @Before
    fun setup() {
        viewModel = ListVM()
    }

    @Test
    fun `refresh movies should reset page`(){
        viewModel.page = 3
        viewModel.refreshMovies()
        Assert.assertEquals(viewModel.page, 1)
    }
    
    @Test
    fun `refresh movies should clear movies`() {
        viewModel.addMoreMovies(data)
        viewModel.refreshMovies()
        Assert.assertEquals(viewModel.movies.value, listOf<Movie>())
    }

    fun `getMovies will get first page`(){}
    fun `getMovies done a second time will get second page`(){}
    fun `getMovies pages done multiple times should not show duplicate pages`(){}
    fun `getMovies pages should not exceed page limit`(){}
    fun `getMovies null data should not crash app`(){}
}