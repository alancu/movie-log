package com.alejandro.movielog.ui.favorites

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.alejandro.movielog.R
import com.alejandro.movielog.databinding.ActivityFavoriteMoviesBinding
import com.alejandro.movielog.ui.base.BaseActivity
import com.alejandro.movielog.ui.components.FavoriteAdapter
import com.alejandro.movielog.ui.detail.MovieDetailActivity
import com.alejandro.movielog.utils.Constants
import com.alejandro.movielog.utils.toApiMovie
import com.alejandro.movielog.viewmodel.FavoriteViewModel
import com.facebook.shimmer.ShimmerFrameLayout
import dagger.hilt.android.AndroidEntryPoint

/**
 * Activitat que mostra totes les pel·lícules guardades com a favorites per l'usuari.
 * Mostra la llista de favorits amb el RecyclerView i permet veure detalls al clicar.
 * La llista es carrega des de Firestore mitjançant FavoriteViewModel.
 */
@AndroidEntryPoint
class FavoriteMoviesActivity : BaseActivity() {

    private lateinit var binding: ActivityFavoriteMoviesBinding
    private val favoriteViewModel: FavoriteViewModel by viewModels()
    private lateinit var adapter: FavoriteAdapter
    private lateinit var shimmerViewContainer: ShimmerFrameLayout

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFavoriteMoviesBinding.inflate(layoutInflater)
        setContentView(binding.root)

        shimmerViewContainer = findViewById(R.id.shimmer_view_container)

        // Configura la Toolbar amb títol i botó d'anar enrere
        setupToolbar(binding.toolbar, getString(R.string.favorite_movies), showBack = true)

        // Inicialitza l'adaptador i el RecyclerView
        adapter = FavoriteAdapter { savedMovie ->
            // Quan es fa clic a un ítem, obri la pantalla de detalls de la pel·lícula
            val apiMovie = savedMovie.toApiMovie()
            val intent = Intent(this, MovieDetailActivity::class.java)
            intent.putExtra(Constants.Extras.EXTRA_MOVIE, apiMovie)
            startActivity(intent)
        }
        binding.rvFavorites.layoutManager = LinearLayoutManager(this)
        binding.rvFavorites.adapter = adapter

        // Mostra shimmer en iniciar càrrega
        shimmerViewContainer.visibility = View.VISIBLE
        shimmerViewContainer.startShimmer()
        binding.rvFavorites.visibility = View.GONE

        // Carrega les pel·lícules favorites de l'usuari
        favoriteViewModel.loadFavorites(this)

        favoriteViewModel.favoriteMovies.observe(this) { movies ->
            adapter.submitList(movies)
            // Quan tens dades, amaga shimmer i mostra la llista
            shimmerViewContainer.stopShimmer()
            shimmerViewContainer.visibility = View.GONE
            binding.rvFavorites.visibility = View.VISIBLE
        }

        favoriteViewModel.errorMessage.observe(this) { msg ->
            msg?.let {
                Toast.makeText(this, it, Toast.LENGTH_SHORT).show()
            }
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_user, menu)
        setupUserMenu(menu)
        return true
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressedDispatcher.onBackPressed()
        return true
    }

    override fun onResume() {
        super.onResume()
        // tornem a carregar el llistat quan tornem d'una fitxa
        favoriteViewModel.loadFavorites(this)
    }
}
