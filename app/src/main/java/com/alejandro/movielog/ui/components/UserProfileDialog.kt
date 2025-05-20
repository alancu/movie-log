package com.alejandro.movielog.ui.components

import android.annotation.SuppressLint
import android.app.Dialog
import android.os.Bundle
import android.view.LayoutInflater
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.DialogFragment
import com.alejandro.movielog.R
import com.alejandro.movielog.ui.profile.UserProfileActivity
import com.alejandro.movielog.utils.loadCircularImage
import com.alejandro.movielog.utils.navigateTo

/**
 * Diàleg (modal) que mostra la informació de l'usuari de Google i permet tancar la sessió o anar al perfil.
 * Aquesta classe hereta de DialogFragment, així pot mostrar-se des de qualsevol activitat/fragment
 */
@Suppress("DEPRECATION")
class UserProfileDialog(
    private val account: com.google.android.gms.auth.api.signin.GoogleSignInAccount?,
    private val onLogout: () -> Unit
) : DialogFragment() {

    @SuppressLint("UseGetLayoutInflater")
    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val view = LayoutInflater.from(context).inflate(R.layout.dialog_user_profile, null)

        view.findViewById<TextView>(R.id.text_user_name).text = account?.displayName
        view.findViewById<TextView>(R.id.text_user_email).text = account?.email

        // Carrega la imatge d'usuari (circular)
        val imageViewUserPhoto: ImageView = view.findViewById(R.id.image_user_photo)
        imageViewUserPhoto.loadCircularImage(account?.photoUrl.toString())

        // Botó de logout: crida la funció rebuda per paràmetre
        view.findViewById<Button>(R.id.button_logout).setOnClickListener {
            dismiss()
            onLogout()
        }
        // Botó per obrir la pantalla de perfil
        view.findViewById<Button>(R.id.button_profile).setOnClickListener {
            dismiss()
            // Obri la nova activity de perfil (amb extensió pròpia)
            requireActivity().navigateTo<UserProfileActivity>()
        }

        // Crea el diàleg d'usuari amb la vista configurada
        return AlertDialog.Builder(requireContext())
            .setView(view)
            .create()
    }
}
