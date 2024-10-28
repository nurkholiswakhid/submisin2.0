package com.dicodingeventstracker.domain.splashscreen

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.lifecycle.lifecycleScope
import com.dicodingeventstracker.R
import com.dicodingeventstracker.databinding.ActivitySplashScreenBinding
import com.dicodingeventstracker.domain.home.MainActivity
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@SuppressLint("CustomSplashScreen")
@AndroidEntryPoint
class SplashScreenActivity : AppCompatActivity() {

    private val viewModel: SplashViewModel by viewModels()
    private lateinit var binding: ActivitySplashScreenBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySplashScreenBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Observe tema dari DataStore
        lifecycleScope.launch {
            viewModel.getTheme().collect { isDarkMode ->
                updateTheme(isDarkMode)
            }
        }

        // Pindah ke MainActivity setelah delay
        Handler(Looper.getMainLooper()).postDelayed({
            startActivity(Intent(this, MainActivity::class.java))
            finish()
        }, 3000)
    }

    private fun updateTheme(isDarkMode: Boolean) {
        if (isDarkMode) {
            // Dark Mode
            binding.splashScreenLayout.setBackgroundColor(
                ContextCompat.getColor(this, R.color.darker)
            )

        } else {
            // Light Mode
            binding.splashScreenLayout.setBackgroundColor(
                ContextCompat.getColor(this, R.color.white)
            )

        }
    }
}