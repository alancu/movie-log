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
import com.facebook.shimmer.ShimmerFrameLayout
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
    private lateinit var shimmerViewContainer: ShimmerFrameLayout
    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: MovieAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        shimmerViewContainer = view.findViewById<ShimmerFrameLayout>(R.id.shimmer_view_container)

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
            // Quan s'hagen carregat les pel·lícules, para el shimmer i mostra el RecyclerView
            shimmerViewContainer.stopShimmer()
            shimmerViewContainer.visibility = View.GONE
            recyclerView.visibility = View.VISIBLE
        }

        // Observem el LiveData "loading" del ViewModel per saber si s'està carregant o no
        viewModel.loading.observe(viewLifecycleOwner) { isLoading ->
            if (isLoading) {
                // Si s'està carregant: mostra el shimmer i amaga la llista real
                shimmerViewContainer.visibility = View.VISIBLE
                recyclerView.visibility = View.GONE
                shimmerViewContainer.startShimmer() // Inicia l'animació del shimmer
            } else {
                // Si ja s'ha carregat: para el shimmer i mostra la llista real de pel·lícules
                shimmerViewContainer.stopShimmer() // Para l'animació del shimmer
                shimmerViewContainer.visibility = View.GONE
                recyclerView.visibility = View.VISIBLE
            }
        }

        viewModel.errorMessage.observe(viewLifecycleOwner) { message ->
            message?.let {
                Toast.makeText(requireContext(), it, Toast.LENGTH_SHORT).show()
            }
        }
    }
}
