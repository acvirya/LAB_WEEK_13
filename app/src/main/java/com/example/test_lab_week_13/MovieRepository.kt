package com.example.test_lab_week_13

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.test_lab_week_13.api.MovieService
import com.example.test_lab_week_13.model.Movie
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
class MovieRepository(private val movieService: MovieService) {
    val apiKey = "9bc121a9d5212801be606ee6dfa54ff9"

    private val movieLiveData = MutableLiveData<List<Movie>>()
    val movies: LiveData<List<Movie>>
        get() = movieLiveData

    private val errorLiveData = MutableLiveData<String>()
    val error: LiveData<String>
        get() = errorLiveData

    fun fetchMovies(): Flow<List<Movie>> {
        return flow{
            emit(movieService.getPopularMovies(apiKey).results)
        }.flowOn(Dispatchers.IO)
    }
}