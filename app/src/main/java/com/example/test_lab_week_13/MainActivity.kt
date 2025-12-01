package com.example.test_lab_week_13

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.RecyclerView
import com.example.test_lab_week_13.model.Movie
import com.example.test_lab_week_13.databinding.ActivityMainBinding
import kotlinx.coroutines.launch


@Suppress("UNCHECKED_CAST")
class MainActivity : AppCompatActivity() {

    private val movieAdapter = MovieAdapter(object : MovieAdapter.MovieClickListener {
        override fun onMovieClick(movie: Movie) {
            val intent = Intent(this@MainActivity, DetailsActivity::class.java).apply {
                putExtra(DetailsActivity.EXTRA_TITLE, movie.title)
                putExtra(DetailsActivity.EXTRA_RELEASE, movie.releaseDate)
                putExtra(DetailsActivity.EXTRA_OVERVIEW, movie.overview)
                putExtra(DetailsActivity.EXTRA_POSTER, movie.posterPath)
            }
            startActivity(intent)
        }
    })

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding: ActivityMainBinding = DataBindingUtil
            .setContentView(this, R.layout.activity_main)

        val recyclerView: RecyclerView = findViewById(R.id.movie_list)
        recyclerView.adapter = movieAdapter

        val movieRepository = (application as MovieApplication).movieRepository

        val movieViewModel = ViewModelProvider(
            this, object : ViewModelProvider.Factory {
                override fun <T : ViewModel> create(modelClass: Class<T>): T {
                    return MovieViewModel(movieRepository) as T
                }
            })[MovieViewModel::class.java]

        binding.viewModel = movieViewModel
        binding.lifecycleOwner = this

        lifecycleScope.launch{
//            repeatOnLifecycle(Lifecycle.State.STARTED){
//                launch {
//                    movieViewModel.popularMovies.collect { movies ->
//                        movieAdapter.addMovies(movies)
//                    }
//                }
//                launch{
//                    movieViewModel.error.collect{ error ->
//                        if (error.isNotEmpty()) {
//                            Snackbar.make(
//                                recyclerView, error, Snackbar.LENGTH_LONG
//                            ).show()
//                        }
//                    }
//                }
//            }
        }
    }
}