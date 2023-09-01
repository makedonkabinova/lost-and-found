package com.example.lostfound.core

import android.app.Application
import com.google.firebase.FirebaseApp
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class MyApplication : Application() {
    lateinit var database : FirebaseFirestore
    override fun onCreate() {
        super.onCreate()
        connectToDb()
    }

    private fun connectToDb(){
        FirebaseApp.initializeApp(this)
        database = Firebase.firestore
    }
    companion object {
        // Define a HashMap to store country names and their coordinates
        val countriesAvailable = HashMap<String, Pair<Double, Double>>()

        init {
            // Add countries and their coordinates to the HashMap
            countriesAvailable["Slovenia"] = Pair(46.1512, 14.9955)
            countriesAvailable["North Macedonia"] = Pair(41.6086, 21.7453)
            countriesAvailable["Serbia"] = Pair(44.0165, 21.0059)
            countriesAvailable["Bosnia and Herzegovina"] = Pair(43.9159, 17.6791)
            countriesAvailable["Montenegro"] = Pair(42.7087, 19.3744)
            countriesAvailable["Croatia"] = Pair(45.1000, 15.2000)
        }
    }
}