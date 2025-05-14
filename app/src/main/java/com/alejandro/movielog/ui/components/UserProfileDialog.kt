
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
import com.alejandro.movielog.utils.loadCircularImage

/**
 * Diàleg que mostra la informació de l'usuari iniciat amb Google i permet tancar sessió.
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

        val imageViewUserPhoto: ImageView = view.findViewById(R.id.image_user_photo)
        imageViewUserPhoto.loadCircularImage(account?.photoUrl.toString())

        view.findViewById<Button>(R.id.button_logout).setOnClickListener {
            dismiss()
            onLogout()
        }

        return AlertDialog.Builder(requireContext())
            .setView(view)
            .create()
    }
}
