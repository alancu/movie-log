package com.alejandro.movielog.ui.components

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.alejandro.movielog.R
import com.alejandro.movielog.data.model.SavedMovie
import com.alejandro.movielog.utils.Constants
import com.alejandro.movielog.utils.loadImage

/**
 * Adaptador per a la llista de pel·lícules guardades per l'usuari (favorits).
 * Permet especificar una acció al clicar una pel·lícula.
 */
class FavoriteAdapter(
    private val onItemClick: (SavedMovie) -> Unit
) : RecyclerView.Adapter<FavoriteAdapter.ViewHolder>() {

    private val items = mutableListOf<SavedMovie>()

    /**
     * Substitueix la llista per una nova i refresca tot el RecyclerView.
     */
    @SuppressLint("NotifyDataSetChanged")
    fun submitList(newList: List<SavedMovie>) {
        items.clear()
        items.addAll(newList)
        notifyDataSetChanged()
    }

    /**
     * ViewHolder per a cada pel·lícula guardada (favorita).
     */
    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val poster: ImageView = view.findViewById(R.id.iv_movie_poster)
        val title: TextView = view.findViewById(R.id.tv_movie_title)
        val description: TextView = view.findViewById(R.id.tv_movie_description)
    }

    /**
     * Crea un ViewHolder per a una pel·lícula favorita.
     * (Funciona igual que en MovieAdapter)
     */
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_movie, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int = items.size

    /**
     * Assigna les dades d'una pel·lícula favorita a la vista.
     */
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val movie = items[position]
        holder.title.text = movie.title
        holder.description.text = movie.overview
        holder.poster.loadImage("${Constants.Api.POSTER_BASE_URL}${movie.posterPath}")

        // Quan es fa clic a la pel·lícula, es crida la funció rebuda pel constructor.
        holder.itemView.setOnClickListener {
            onItemClick(movie)
        }
    }
}
