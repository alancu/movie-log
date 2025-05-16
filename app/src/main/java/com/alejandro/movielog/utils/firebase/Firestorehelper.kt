package com.alejandro.movielog.utils.firebase

import android.util.Log
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

/**
 * Classe auxiliar per a operar amb Firestore.
 */
object FirestoreHelper {
    fun testWriteData() {
        val db = Firebase.firestore

        val testData = hashMapOf(
            "title" to "The Matrix",
            "year" to 1999,
            "userId" to FirebaseAuth.getInstance().currentUser?.uid
        )

        db.collection("test_movies")
            .add(testData)
            .addOnSuccessListener { documentReference ->
                Log.d("FirestoreTest", "Document afegit amb ID: ${documentReference.id}")
            }
            .addOnFailureListener { e ->
                Log.w("FirestoreTest", "S'ha produït un error en afegir el document", e)
            }
    }
}
