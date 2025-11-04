package com.example.coffeshop

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.animation.AccelerateDecelerateInterpolator
import androidx.appcompat.app.AppCompatActivity
import com.example.coffeshop.databinding.ActivitySplashBinding

class SplashActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySplashBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySplashBinding.inflate(layoutInflater)
        setContentView(binding.root)

        startAnimations()
    }

    private fun startAnimations() {
        // Animasi untuk teks "Selamat Datang"
        binding.tvWelcome.animate()
            .translationY(0f)
            .alpha(1f)
            .setDuration(1000)
            .setStartDelay(500)
            .setInterpolator(AccelerateDecelerateInterpolator())
            .withEndAction {
                // Setelah teks pertama muncul, animasi teks kedua
                binding.tvCoffeeShop.animate()
                    .translationY(0f)
                    .alpha(1f)
                    .setDuration(800)
                    .setInterpolator(AccelerateDecelerateInterpolator())
                    .withEndAction {
                        // Setelah teks kedua muncul, tampilkan tombol
                        binding.btnStart.animate()
                            .alpha(1f)
                            .setDuration(600)
                            .start()
                    }
                    .start()
            }
            .start()

        // Tombol mulai diklik
        binding.btnStart.setOnClickListener {
            // Animasi klik tombol
            binding.btnStart.animate()
                .scaleX(0.9f)
                .scaleY(0.9f)
                .setDuration(150)
                .withEndAction {
                    binding.btnStart.animate()
                        .scaleX(1f)
                        .scaleY(1f)
                        .setDuration(150)
                        .withEndAction {
                            // Pindah ke LoginActivity
                            val intent = Intent(this, LoginActivity::class.java)
                            startActivity(intent)
                            overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out)
                            finish()
                        }
                        .start()
                }
                .start()
        }
    }

    // Optional: Auto navigate setelah beberapa detik
    private fun autoNavigate() {
        Handler(Looper.getMainLooper()).postDelayed({
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
            overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out)
            finish()
        }, 4000) // 4 detik
    }
}