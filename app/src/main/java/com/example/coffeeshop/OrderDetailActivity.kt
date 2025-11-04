package com.example.coffeshop

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.coffeshop.databinding.ActivityOrderDetailBinding

class OrderDetailActivity : AppCompatActivity() {

    private lateinit var binding: ActivityOrderDetailBinding
    private lateinit var coffee: Coffee

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityOrderDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Terima data dari MainActivity
        coffee = intent.getSerializableExtra("COFFEE") as Coffee

        setupUI()
        setupButton()
    }

    private fun setupUI() {
        // Set data coffee
        binding.tvCoffeeName.text = coffee.name
        binding.tvQuantity.text = "Jumlah: ${coffee.quantity}"
        binding.tvTotalPrice.text = "Total: Rp ${coffee.getTotalPrice()}"

        if (coffee.imageRes != 0) {
            binding.ivCoffee.setImageResource(coffee.imageRes)
        }
    }

    private fun setupButton() {
        binding.btnConfirmOrder.setOnClickListener {
            val name = binding.etName.text.toString().trim()
            val address = binding.etAddress.text.toString().trim()
            val landmark = binding.etLandmark.text.toString().trim()

            // Validasi form
            if (name.isEmpty() || address.isEmpty()) {
                Toast.makeText(this, "Nama dan alamat harus diisi", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            // SIMPAN PESANAN KE ORDER MANAGER
            saveOrder(name, address, landmark)

            // Tampilkan konfirmasi dengan data yang sudah diambil
            showOrderConfirmation(name, address, landmark)
        }
    }

    private fun saveOrder(name: String, address: String, landmark: String) {
        val order = Order(
            coffee = coffee,
            customerName = name,
            customerAddress = address,
            customerLandmark = landmark,
            totalPrice = coffee.getTotalPrice(),
            quantity = coffee.quantity
        )

        OrderManager.addOrder(order)
    }

    private fun showOrderConfirmation(name: String, address: String, landmark: String) {
        val message = """
            Terima kasih $name!
            
            ✅ Pesanan ${coffee.name} sebanyak ${coffee.quantity} cup telah diterima.
            
            📍 Alamat: $address
            🏷️ Patokan: ${if (landmark.isEmpty()) "-" else landmark}
            
            ⏱️ Pesanan akan sampai dalam 15-30 menit.
            
            Lihat tab 'Pesanan' untuk melacak status pesanan Anda.
        """.trimIndent()

        androidx.appcompat.app.AlertDialog.Builder(this)
            .setTitle("🎉 Pesanan Berhasil!")
            .setMessage(message)
            .setPositiveButton("Lihat Pesanan") { dialog, _ ->
                dialog.dismiss()
                backToMain()
            }
            .setCancelable(false)
            .show()
    }

    private fun backToMain() {
        val intent = Intent(this, MainActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_SINGLE_TOP
        startActivity(intent)
        finish()
    }
}