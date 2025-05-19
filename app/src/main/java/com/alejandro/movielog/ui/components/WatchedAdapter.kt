package com.alejandro.movielog.ui.components

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.alejandro.movielog.R
import com.alejandro.movielog.data.model.WatchedMovie
import com.alejandro.movielog.utils.Constants
import com.alejandro.movielog.utils.loadImage
import java.text.SimpleDateFormat
import java.util.Locale

/**
 * Adaptador per a la llista de pel·lícules vistes per l'usuari.
 * Mostra la data en què s'ha vist cada pel·lícula.
 */
class WatchedAdapter(
    private val onItemClick: (WatchedMovie) -> Unit
) : RecyclerView.Adapter<WatchedAdapter.ViewHolder>() {

    private val items = mutableListOf<WatchedMovie>()

    @SuppressLint("NotifyDataSetChanged")
    fun submitList(newList: List<WatchedMovie>) {
        items.clear()
        items.addAll(newList)
        notifyDataSetChanged()
    }

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val poster: ImageView = view.findViewById(R.id.iv_movie_poster)
        val title: TextView = view.findViewById(R.id.tv_movie_title)
        val description: TextView = view.findViewById(R.id.tv_movie_description)
        val date: TextView = view.findViewById(R.id.tv_movie_date)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_movie_watched, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int = items.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val movie = items[position]
        holder.title.text = movie.title
        holder.description.text = movie.overview
        holder.poster.loadImage("${Constants.Api.POSTER_BASE_URL}${movie.posterPath}")

        // Formata la data
        val format = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
        val dateString = format.format(movie.watchedAt.toDate())
        holder.date.text = holder.itemView.context.getString(R.string.watched_at, dateString)

        holder.itemView.setOnClickListener {
            onItemClick(movie)
        }
    }
}
