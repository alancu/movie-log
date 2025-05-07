package com.alejandro.movielog.ui

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.alejandro.movielog.R
import com.alejandro.movielog.data.Movie
import com.bumptech.glide.Glide

class MovieAdapter(private val movieList: MutableList<Movie>) : RecyclerView.Adapter<MovieAdapter.MovieViewHolder>() {

    // Crea la vista de cada element
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_movie, parent, false)
        return MovieViewHolder(view)
    }

    // Enllaça les dades amb la vista de cada element
    override fun onBindViewHolder(holder: MovieViewHolder, position: Int) {
        val movie = movieList[position]

        holder.title.text = movie.title
        holder.description.text = movie.description
        // Carregar imatge
        Glide.with(holder.poster.context)
            .load("https://image.tmdb.org/t/p/w500${movie.posterPath}")
            .into(holder.poster)
    }

    // Retorna el número de pel·lícules retornades
    override fun getItemCount(): Int = movieList.size

    // Vista de cada element
    class MovieViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var title: TextView = itemView.findViewById(R.id.tv_movie_title)
        val description: TextView = itemView.findViewById(R.id.tv_movie_description)
        val poster: ImageView = itemView.findViewById(R.id.iv_movie_poster)
    }
}