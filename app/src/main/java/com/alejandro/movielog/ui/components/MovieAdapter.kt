package com.alejandro.movielog.ui.components

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.alejandro.movielog.R
import com.alejandro.movielog.data.model.Movie
import com.alejandro.movielog.utils.Constants
import com.alejandro.movielog.utils.loadImage
import com.alejandro.movielog.utils.openMovieDetail

/**
 * Adaptador per a mostrar pel·lícules en un RecyclerView.
 */
class MovieAdapter(private val movieList: MutableList<Movie>) : RecyclerView.Adapter<MovieAdapter.MovieViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_movie, parent, false)
        return MovieViewHolder(view)
    }

    override fun onBindViewHolder(holder: MovieViewHolder, position: Int) {
        val movie = movieList[position]
        holder.title.text = movie.title
        holder.description.text = movie.overview

        holder.poster.loadImage("${Constants.POSTER_BASE_URL}${movie.posterPath}")

        holder.itemView.setOnClickListener {
            holder.itemView.context.openMovieDetail(movie)
        }
    }

    override fun getItemCount(): Int = movieList.size

    class MovieViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val title: TextView = itemView.findViewById(R.id.tv_movie_title)
        val description: TextView = itemView.findViewById(R.id.tv_movie_description)
        val poster: ImageView = itemView.findViewById(R.id.iv_movie_poster)
    }

    /**
     * Actualitza el contingut de la llista de pel·lícules.
     */
    @SuppressLint("NotifyDataSetChanged")
    fun updateMovies(newMovies: List<Movie>) {
        movieList.clear()
        movieList.addAll(newMovies)
        notifyDataSetChanged()
    }
}
