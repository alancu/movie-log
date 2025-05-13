package com.alejandro.movielog.ui

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
import com.bumptech.glide.Glide

@Suppress("DEPRECATION")
// classe de diàleg que mostra el perfil de l'usuari i permet tancar sessió
class UserProfileDialog(
    private val account: com.google.android.gms.auth.api.signin.GoogleSignInAccount?,
    private val onLogout: () -> Unit
) : DialogFragment() {
    @SuppressLint("UseGetLayoutInflater")
    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val view = LayoutInflater.from(context).inflate(R.layout.dialog_user_profile, null)

        view.findViewById<TextView>(R.id.text_user_name).text = account?.displayName
        view.findViewById<TextView>(R.id.text_user_email).text = account?.email

        Glide.with(requireContext())
            .load(account?.photoUrl)
            .placeholder(R.drawable.ic_user)
            .circleCrop()
            .into(view.findViewById<ImageView>(R.id.image_user_photo))

        // acció del botó de tancar sessió: tanca el diàleg i crida la funció onLogout
        view.findViewById<Button>(R.id.button_logout).setOnClickListener {
            dismiss()
            onLogout()
        }

        // aquesta funció retorna el diàleg amb la vista personalitzada
        return AlertDialog.Builder(requireContext())
            .setView(view)
            .create()
    }
}