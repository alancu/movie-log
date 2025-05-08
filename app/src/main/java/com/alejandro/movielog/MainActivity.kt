package com.alejandro.movielog

import android.annotation.SuppressLint
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.alejandro.movielog.data.Movie
import com.alejandro.movielog.retrofit.RetrofitClient
import com.alejandro.movielog.ui.MovieAdapter
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: MovieAdapter
    private val movieList = mutableListOf<Movie>()

    @SuppressLint("NotifyDataSetChanged")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Inicialitzar RecyclerView
        recyclerView = findViewById(R.id.rvMovies)
        recyclerView.layoutManager = LinearLayoutManager(this)

        // càrrega inicial de pel·lícules populars
        lifecycleScope.launch {
            try {
                val response = RetrofitClient.apiService.getPopularMovies("4a5cbff143eda90e596622878aaa6354")
                movieList.clear()
                movieList.addAll(response.results)
                adapter.notifyDataSetChanged()
            } catch (e: Exception) {
                e.printStackTrace()
                Toast.makeText(this@MainActivity, "Error carregant les pel·lícules", Toast.LENGTH_SHORT).show()
            }
        }

        // Crear l'adaptador
        adapter = MovieAdapter(movieList)
        recyclerView.adapter = adapter
    }
}