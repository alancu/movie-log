package com.alejandro.movielog.ui.profile

import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.alejandro.movielog.R
import com.alejandro.movielog.ui.favorites.FavoriteMoviesActivity
import com.alejandro.movielog.utils.auth.UserAuthHelper
import com.alejandro.movielog.utils.loadCircularImage
import com.alejandro.movielog.utils.navigateTo

class UserProfileActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_user_profile)

        val imageUserPhoto = findViewById<ImageView>(R.id.image_user_photo)
        val textUserName = findViewById<TextView>(R.id.text_user_name)
        val textUserEmail = findViewById<TextView>(R.id.text_user_email)
        val buttonFavorites = findViewById<Button>(R.id.button_favorites)
        val buttonLogout = findViewById<Button>(R.id.button_logout)
        val account = UserAuthHelper.getGoogleAccount(this)

        account?.let {
            textUserName.text = it.displayName ?: ""
            textUserEmail.text = it.email ?: ""
            imageUserPhoto.loadCircularImage(it.photoUrl?.toString() ?: "")
        }

        buttonFavorites.setOnClickListener {
            navigateTo<FavoriteMoviesActivity>()
        }

        buttonLogout.setOnClickListener {
            UserAuthHelper.logout(this)
            finish()
        }
    }
}
