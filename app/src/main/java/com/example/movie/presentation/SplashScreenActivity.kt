package com.example.movie.presentation

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.appcompat.app.AppCompatActivity
import com.example.movie.R
import com.example.movie.databinding.ActivitySplashScreenBinding

class SplashScreenActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySplashScreenBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash_screen)

        binding = ActivitySplashScreenBinding.inflate(layoutInflater)

        Handler(Looper.getMainLooper()).postDelayed({
            val intent = Intent(this, MovieDashboardActivity::class.java)
            startActivity(intent)
            finish()
        }, 3000)
    }
}