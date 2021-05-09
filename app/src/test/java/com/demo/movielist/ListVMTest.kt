package com.demo.movielist

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.demo.movielist.model.Movie
import com.demo.movielist.network.MovieSearch
import com.haroldadmin.cnradapter.NetworkResponseAdapterFactory
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import okhttp3.OkHttpClient
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import okhttp3.mockwebserver.RecordedRequest
import org.junit.Assert
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory


class ListVMUnitTests {
    @get:Rule
    val taskExecutorRule = InstantTaskExecutorRule()

    private lateinit var viewModel: ListVM

    private val data = listOf(
        Movie(
            1,
            "Title",
            "lorem ipsum whatever overview",
            "/2DtPSyODKWXluIRV7PVru0SSzja.jpg",
            6.9f,
            "12/20/2020"
        ),
        Movie(2, "Title 2", "lorem ipsum whatever overview 2", "", 1.0f, "13/20/2020"),
        Movie(
            3,
            "Title 3",
            "lorem ipsum whatever overview 3",
            "/2DtPSyODKWXluIRV7PVru0SSzja.jpg",
            0f,
            "14/20/2020"
        ),
        Movie(
            4,
            "Title 4",
            "lorem ipsum whatever overview 4",
            "/2DtPSyODKWXluIRV7PVru0SSzja.jpg",
            3.0f,
            "15/20/2020"
        )
    )

    @Before
    fun setup() {
        viewModel = ListVM()
    }

    @Test
    fun `refresh movies should reset page`() {
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

    @Test
    fun `onCleared should clean up jobs`(){
        viewModel.onCleared()
        Assert.assertEquals(viewModel.viewModelJob.isCancelled, true)
    }
}

class ListVMMockWebServerTests {
    @get:Rule
    var server: MockWebServer = MockWebServer()
    private val testJson = "{\"page\": 1, \"results\": [{\"adult\": false, \"backdrop_path\": \"/2l5UHZBcp9cx1PwKLdisJ0gV9jB.jpg\", \"genre_ids\": [16, 35, 14, 12 ], \"id\": 808, \"original_language\": \"en\", \"original_title\": \"Shrek\", \"overview\": \"It ain't easy bein' green -- especially if you're a likable (albeit smelly) ogre named Shrek. On a mission to retrieve a gorgeous princess from the clutches of a fire-breathing dragon, Shrek teams up with an unlikely compatriot -- a wisecracking donkey.\", \"popularity\": 144.199, \"poster_path\": \"/iB64vpL3dIObOtMZgX3RqdVdQDc.jpg\", \"release_date\": \"2001-05-18\", \"title\": \"Shrek\", \"video\": false, \"vote_average\": 7.7, \"vote_count\": 12323 } ], \"total_pages\": 2, \"total_results\": 25 }"

    private val client = OkHttpClient.Builder().build()
    private val moshi = Moshi.Builder().add(KotlinJsonAdapterFactory()).build()
    private val retrofit: Retrofit = Retrofit.Builder()
        .addConverterFactory(MoshiConverterFactory.create(moshi))
        .addCallAdapterFactory(NetworkResponseAdapterFactory())
        .baseUrl(server.url("/"))
        .client(client)
        .build()

    private val movieSearchService by lazy {
        retrofit.create(MovieSearch::class.java)
    }

    @Test
    fun `getMovies will get first page`() {
        server.enqueue(
            MockResponse()
                .setBody(testJson)
                .setResponseCode(200))
        val testObserver = movieSearchService.search("superman", 1, "abc")
        Assert.assertEquals(testObserver, testJson)
    }
    fun `getMovies done a second time will get second page`() {}
    fun `getMovies pages done multiple times should not show duplicate pages`() {}
    fun `getMovies pages should not exceed page limit`() {}
    fun `getMovies null data should not crash app`() {}


}