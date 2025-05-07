package com.alejandro.movielog

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.alejandro.movielog.data.Movie
import com.alejandro.movielog.ui.MovieAdapter

class MainActivity : AppCompatActivity() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: MovieAdapter
    private val movieList = mutableListOf<Movie>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Inicialitzar RecyclerView
        recyclerView = findViewById(R.id.rvMovies)
        recyclerView.layoutManager = LinearLayoutManager(this)

        // Crear l'adaptador
        adapter = MovieAdapter(movieList)
        recyclerView.adapter = adapter

        // Exemple de dades (potser les obtens de l'API)
        val movie1 = Movie(1, "Pelicula A", "Descripció A", "https://www.tallengestore.com/cdn/shop/products/Art_Poster_-_Sicario_-_Tallenge_Hollywood_Collection_large.jpg")
        val movie2 = Movie(2, "Pelicula B", "Descripció B", "https://assets.mubicdn.net/images/notebook/post_images/38230/images-w1400.jpeg")

        // Afegir les pel·lícules a la llista
        movieList.add(movie1)
        // Notificar al RecyclerView que les dades han canviat
        adapter.notifyItemInserted(0)
        movieList.add(movie2)
        adapter.notifyItemInserted(1)

    }
}