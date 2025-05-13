package com.alejandro.movielog

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.alejandro.movielog.repository.MovieRepository
import com.alejandro.movielog.ui.MovieAdapter
import com.alejandro.movielog.viewmodel.MovieViewModel
import com.alejandro.movielog.viewmodel.MovieViewModelFactory

@SuppressLint("NotifyDataSetChanged")
class MovieListFragment : Fragment(R.layout.fragment_movie_list) {

    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: MovieAdapter

    private lateinit var viewModel: MovieViewModel

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        recyclerView = view.findViewById(R.id.rv_movies)
        recyclerView.layoutManager = LinearLayoutManager(requireContext())
        adapter = MovieAdapter(mutableListOf())
        recyclerView.adapter = adapter

        val apiKey = getString(R.string.tmdb_api_key)
        val repository = MovieRepository(apiKey)
        val factory = MovieViewModelFactory(repository)
        viewModel = ViewModelProvider(this, factory)[MovieViewModel::class.java]

        observeViewModel()
        viewModel.loadPopularMovies()
    }

    private fun observeViewModel() {
        viewModel.movies.observe(viewLifecycleOwner) { movies ->
            adapter.updateMovies(movies)
        }

        viewModel.errorMessage.observe(viewLifecycleOwner) { message ->
            message?.let {
                Toast.makeText(requireContext(), it, Toast.LENGTH_SHORT).show()
            }
        }
    }

    fun searchMovies(query: String) {
        viewModel.searchMovies(query)
    }
}
