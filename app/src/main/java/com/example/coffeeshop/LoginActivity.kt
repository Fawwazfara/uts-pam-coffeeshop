package com.example.coffeshop

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.coffeshop.databinding.ActivityLoginBinding

class LoginActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLoginBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupLogin()
    }

    private fun setupLogin() {
        binding.btnLogin.setOnClickListener {
            val username = binding.etUsername.text.toString().trim()
            val password = binding.etPassword.text.toString().trim()

            // Validasi input tidak boleh kosong
            if (username.isEmpty() || password.isEmpty()) {
                Toast.makeText(this, "Username dan password harus diisi", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            // Login berhasil - username dan password apa saja bisa
            loginSuccess()
        }

        // Skip login langsung ke main activity
        binding.tvSkip.setOnClickListener {
            loginSuccess()
        }
    }

    private fun loginSuccess() {
        Toast.makeText(this, "Login berhasil!", Toast.LENGTH_SHORT).show()

        // Pindah ke MainActivity
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
        finish() // Tutup login activity
    }
}