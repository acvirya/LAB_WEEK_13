package com.example.test_lab_week_13

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.test_lab_week_13.model.Movie
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch

class MovieViewModel(private val movieRepository: MovieRepository) : ViewModel() {

    private val _popularMovies = MutableStateFlow(emptyList<Movie>())
    val popularMovies: StateFlow<List<Movie>> = _popularMovies

    private val _error = MutableStateFlow("")
    val error: StateFlow<String> = _error

    init {
        fetchPopularMovies()
    }

    private fun fetchPopularMovies(){
        viewModelScope.launch(Dispatchers.IO){
            movieRepository.fetchMovies().catch {
                _error.value = "An exception occured: ${it.message}"
            }.collect{ movies ->
                val currentYear = java.util.Calendar.getInstance().get(java.util.Calendar.YEAR).toString()
                val filteredMovies = movies
                    .filter { movie -> movie.releaseDate?.startsWith(currentYear) == true }
                    .sortedByDescending { it.popularity }
                _popularMovies.value = filteredMovies
            }
        }
    }
}