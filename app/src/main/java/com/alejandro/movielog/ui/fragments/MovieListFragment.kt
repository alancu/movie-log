package com.alejandro.movielog.ui.fragments

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.alejandro.movielog.R
import com.alejandro.movielog.ui.components.MovieAdapter
import com.alejandro.movielog.viewmodel.MovieViewModel
import dagger.hilt.android.AndroidEntryPoint

/**
 * Fragment que mostra la llista de pel·lícules populars a la pantalla principal.
 * Utilitza MovieAdapter per mostrar les pel·lícules de TMDb rebudes del ViewModel.
 * Este fragment es carrega dins de MainActivity.
 */
@SuppressLint("NotifyDataSetChanged")
@AndroidEntryPoint
class MovieListFragment : Fragment(R.layout.fragment_movie_list) {

    private val viewModel: MovieViewModel by viewModels()
    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: MovieAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        recyclerView = view.findViewById(R.id.rv_movies)
        recyclerView.layoutManager = LinearLayoutManager(requireContext())
        adapter = MovieAdapter()
        recyclerView.adapter = adapter

        observeViewModel()
        // Carrega pel·lícules populars en iniciar
        viewModel.loadPopularMovies()
    }

    /**
     * Observa els LiveData del ViewModel i actualitza la UI.
     */
    // LiveData és una classe que notifica automàticament a la UI quan les dades canvien.
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
