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
 * Adaptador per a mostrar pel·lícules en un RecyclerView.
 * Utilitza ListAdapter amb DiffUtil per a actualitzacions eficients.
 */
class MovieAdapter :
    ListAdapter<ApiMovie, MovieAdapter.MovieViewHolder>(DIFF_CALLBACK) {

    /**
     * Crea un ViewHolder per a una nova vista de pel·lícula.
     */
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_movie, parent, false)
        return MovieViewHolder(view)
    }

    /**
     * Assigna les dades d'una pel·lícula al ViewHolder corresponent.
     */
    override fun onBindViewHolder(holder: MovieViewHolder, position: Int) {
        val movie = getItem(position)
        holder.title.text = movie.title
        holder.description.text = movie.overview

        // Carrega el pòster de la pel·lícula
        holder.poster.loadImage("${Constants.Api.POSTER_BASE_URL}${movie.posterPath}")

        // Acció al fer clic: obri l'activitat de detalls
        holder.itemView.setOnClickListener {
            holder.itemView.context.openMovieDetail(movie)
        }
    }

    /**
     * ViewHolder per a cada element de la llista de pel·lícules.
     */
    class MovieViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val title: TextView = itemView.findViewById(R.id.tv_movie_title)
        val description: TextView = itemView.findViewById(R.id.tv_movie_description)
        val poster: ImageView = itemView.findViewById(R.id.iv_movie_poster)
    }

    /**
     * Objecte que defineix com comparar els ítems de la llista.
     */
    companion object {
        // aquest objecte serveix per a que el ListAdapter sàpiga com actualitzar la llista
        private val DIFF_CALLBACK = object : DiffUtil.ItemCallback<ApiMovie>() {
            override fun areItemsTheSame(oldItem: ApiMovie, newItem: ApiMovie): Boolean {
                // ací estem diguent-li que si dues pel·lis tenen la mateix id, són la mateixa pel·li
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: ApiMovie, newItem: ApiMovie): Boolean {
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
