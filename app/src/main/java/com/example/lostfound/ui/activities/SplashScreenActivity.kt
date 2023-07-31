package com.example.lostfound.ui.activities

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.ui.AppBarConfiguration
import com.example.lostfound.databinding.ActivitySplashScreenBinding

class SplashScreenActivity : AppCompatActivity() {

    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var binding: ActivitySplashScreenBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

         binding = ActivitySplashScreenBinding.inflate(layoutInflater)
         setContentView(binding.root)

        // Start the main activity after a certain delay
        val splashScreenDuration = 3000 // 3 seconds
        val intent = Intent(this, MainActivity::class.java)

        Thread {
            Thread.sleep(splashScreenDuration.toLong())
            startActivity(intent)
            finish()
        }.start()
    }
}