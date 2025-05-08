package com.alejandro.movielog

import android.annotation.SuppressLint
import android.os.Bundle
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.alejandro.movielog.data.Movie
import com.alejandro.movielog.retrofit.RetrofitClient
import com.alejandro.movielog.ui.MovieAdapter
import kotlinx.coroutines.launch

class MovieListFragment : Fragment(R.layout.fragment_movie_list) {

    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: MovieAdapter
    private val movieList = mutableListOf<Movie>()

    @SuppressLint("NotifyDataSetChanged")
    override fun onViewCreated(view: android.view.View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // inicialitzar RecyclerView
        recyclerView = view.findViewById(R.id.rvMovies)
        recyclerView.layoutManager = LinearLayoutManager(requireContext())

        // càrrega inicial de pel·lícules populars
        lifecycleScope.launch {
            try {
                val response = RetrofitClient.apiService.getPopularMovies("4a5cbff143eda90e596622878aaa6354")
                movieList.clear()
                movieList.addAll(response.results)
                adapter.notifyDataSetChanged()
            } catch (e: Exception) {
                e.printStackTrace()
                Toast.makeText(requireContext(), getString(R.string.error_loading_movies), Toast.LENGTH_SHORT).show()
            }
        }

        // Crear l'adaptador
        adapter = MovieAdapter(movieList)
        recyclerView.adapter = adapter
    }

    @SuppressLint("NotifyDataSetChanged")
    fun searchMovies(query: String) {
        // crida asíncrona per a no bloquejar la interfície mentre es fa la búsqueda
        lifecycleScope.launch {
            try {
                val response = RetrofitClient.apiService.searchMovies("4a5cbff143eda90e596622878aaa6354", query)
                movieList.clear()
                movieList.addAll(response.results)
                adapter.notifyDataSetChanged()
            } catch (e: Exception) {
                e.printStackTrace()
                Toast.makeText(requireContext(), getString(R.string.error_loading_movies), Toast.LENGTH_SHORT).show()
            }
        }
    }
}