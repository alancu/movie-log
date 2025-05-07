package com.alejandro.movielog

import android.os.Bundle
import android.util.Log
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.lifecycleScope
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import okhttp3.OkHttpClient
import okhttp3.Request

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        // Prova de connexió anònima Firebase
        /*
        FirebaseAuth.getInstance().signInAnonymously()
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    val user = FirebaseAuth.getInstance().currentUser
                    Log.d("FIREBASE", "Usuari connectat: ${user?.uid}")
                } else {
                    Log.e("FIREBASE", "Error al connectar: ${task.exception}")
                }

         */

        // Prova de connexió TMDb
        // Crida segura en segon pla
        /*
        lifecycleScope.launch {
            withContext(Dispatchers.IO) {
                try {
                    val client = OkHttpClient()
                    val request = Request.Builder()
                        .url("https://api.themoviedb.org/3/movie/popular?language=en-US&page=1")
                        .get()
                        .addHeader("accept", "application/json")
                        .addHeader("Authorization", "Bearer eyJhbGciOiJIUzI1NiJ9.eyJhdWQiOiI0YTVjYmZmMTQzZWRhOTBlNTk2NjIyODc4YWFhNjM1NCIsIm5iZiI6MTc0MzE1MjkzNy44MDQsInN1YiI6IjY3ZTY2NzI5NWYzZTBhYzE4ODAwNTBkNCIsInNjb3BlcyI6WyJhcGlfcmVhZCJdLCJ2ZXJzaW9uIjoxfQ.BD-VZDhYzpiOnyOhnS6rKJJfkVlF9VieQVQ54f3dpZo")
                        .build()

                    val response = client.newCall(request).execute()
                    val body = response.body()?.string()

                    Log.d("TMDb", "Resposta: $body")
                } catch (e: Exception) {
                    Log.e("TMDb", "Error: ${e.message}")
                }
            }
        }
        */
    }
}