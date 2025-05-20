package com.alejandro.movielog.ui.components

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.alejandro.movielog.R
import com.alejandro.movielog.data.model.ApiMovie
import com.alejandro.movielog.utils.Constants
import com.alejandro.movielog.utils.loadImage
import com.alejandro.movielog.utils.openMovieDetail

/**
 * Adaptador per a mostrar pel·lícules populars i el resultat d'una búsqueda en un RecyclerView.
 */
// DIFF_CALLBACK s'utilitza per a que el RecyclerView sàpiga quins elements han canviat realment
class MovieAdapter :
    ListAdapter<ApiMovie, MovieAdapter.MovieViewHolder>(DIFF_CALLBACK) {

    // Un ViewHolder és un "contenidor" que guarda les referències  a les vistes d'un ítem del RecyclerView

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_movie, parent, false)
        return MovieViewHolder(view)
    }

    /**
     * Assigna les dades d'una pel·lícula al ViewHolder corresponent.
     * S'executa automàticament per cada element de la llista.
     */
    override fun onBindViewHolder(holder: MovieViewHolder, position: Int) {
        val movie = getItem(position)
        holder.title.text = movie.title
        holder.description.text = movie.overview

        // Carrega el pòster de la pel·lícula usant una extensió de Glide
        holder.poster.loadImage("${Constants.Api.POSTER_BASE_URL}${movie.posterPath}")

        // Acció al fer clic: obri l'activitat de detalls (utilitza una extensió)
        holder.itemView.setOnClickListener {
            holder.itemView.context.openMovieDetail(movie)
        }
    }

    /**
     * ViewHolder per a cada element de la llista de pel·lícules.
     */
    // Un ViewHolder és un contenidor on es posen les vistes d'un ítem de la llista.
    class MovieViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val title: TextView = itemView.findViewById(R.id.tv_movie_title)
        val description: TextView = itemView.findViewById(R.id.tv_movie_description)
        val poster: ImageView = itemView.findViewById(R.id.iv_movie_poster)
    }

    /**
     * Objecte que defineix com comparar els ítems de la llista.
     * DiffUtil compara ítems automàticament.
     */
    companion object {
        private val DIFF_CALLBACK = object : DiffUtil.ItemCallback<ApiMovie>() {
            override fun areItemsTheSame(oldItem: ApiMovie, newItem: ApiMovie): Boolean {
                // Compara pel·lícules per id únic (això evita actualitzar tot si només canvia una)
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: ApiMovie, newItem: ApiMovie): Boolean {
                // Compara tots els camps per veure si el contingut ha canviat realment
                return oldItem == newItem
            }
        }
    }

    /**
     * Actualitza la llista de pel·lícules amb una nova llista.
     */
    fun updateMovies(newApiMovies: List<ApiMovie>) {
        submitList(newApiMovies)
    }
}
