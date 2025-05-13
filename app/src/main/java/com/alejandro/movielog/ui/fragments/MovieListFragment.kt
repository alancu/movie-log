package com.alejandro.movielog.ui.fragments

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.alejandro.movielog.R
import com.alejandro.movielog.ui.components.MovieAdapter
import com.alejandro.movielog.viewmodel.MovieViewModel
import com.alejandro.movielog.viewmodel.ViewModelProviderUtil

/**
 * Fragment que mostra la llista de pel·lícules populars i gestiona la cerca.
 */
@SuppressLint("NotifyDataSetChanged")
class MovieListFragment : Fragment(R.layout.fragment_movie_list) {

    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: MovieAdapter
    private lateinit var viewModel: MovieViewModel

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        recyclerView = view.findViewById(R.id.rv_movies)
        recyclerView.layoutManager = LinearLayoutManager(requireContext())
        adapter = MovieAdapter()
        recyclerView.adapter = adapter

        viewModel = ViewModelProviderUtil.provideMovieViewModel(this, requireContext())

        observeViewModel()
        viewModel.loadPopularMovies()
    }

    /**
     * Observa els LiveData del ViewModel i actualitza la UI.
     */
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
}
