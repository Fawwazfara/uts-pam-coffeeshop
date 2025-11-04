package com.example.coffeshop

import android.content.Intent
import android.os.Bundle
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.coffeshop.databinding.ActivityMainBinding
import com.google.android.material.bottomnavigation.BottomNavigationView

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var coffeeAdapter: CoffeeAdapter
    private lateinit var bannerAdapter: BannerAdapter

    // Buat ID secara manual
    companion object {
        private const val ID_EMPTY_ORDERS = 1001
        private const val ID_RV_ORDERS = 1002
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupBanner()
        setupCoffeeMenu()
        setupBottomNavigation()
    }

    private fun setupBottomNavigation() {
        val bottomNav: BottomNavigationView = binding.bottomNavigation

        bottomNav.setOnNavigationItemSelectedListener { item ->
            when (item.itemId) {
                R.id.nav_home -> {
                    showHomeContent()
                    true
                }
                R.id.nav_orders -> {
                    showOrdersContent()
                    true
                }
                R.id.nav_profile -> {
                    showLogoutDialog()
                    true
                }
                else -> false
            }
        }
    }

    private fun showHomeContent() {
        // Tampilkan konten home
        binding.viewPager.visibility = android.view.View.VISIBLE
        findViewById<TextView>(ID_EMPTY_ORDERS)?.visibility = android.view.View.GONE
        findViewById<RecyclerView>(ID_RV_ORDERS)?.visibility = android.view.View.GONE
        binding.recyclerView.visibility = android.view.View.VISIBLE
    }

    private fun showOrdersContent() {
        // Sembunyikan konten home
        binding.viewPager.visibility = android.view.View.GONE
        binding.recyclerView.visibility = android.view.View.GONE

        val orders = OrderManager.getAllOrders()

        if (orders.isEmpty()) {
            showEmptyOrdersMessage()
        } else {
            showOrdersList(orders)
        }
    }

    private fun showEmptyOrdersMessage() {
        // Sembunyikan list orders jika ada
        findViewById<RecyclerView>(ID_RV_ORDERS)?.visibility = android.view.View.GONE

        // Buat atau tampilkan pesan kosong
        var tvEmpty = findViewById<TextView>(ID_EMPTY_ORDERS)

        if (tvEmpty == null) {
            tvEmpty = TextView(this).apply {
                id = ID_EMPTY_ORDERS
                text = "☕ Belum ada pesanan\n\nPesan kopi favorit Anda di tab Home! 🚀"
                setTextColor(0xFF666666.toInt())
                textSize = 16f
                gravity = android.view.Gravity.CENTER
                setPadding(0, 100, 0, 0)
            }
            (binding.root as android.view.ViewGroup).addView(tvEmpty)
        }

        tvEmpty.visibility = android.view.View.VISIBLE
    }

    private fun showOrdersList(orders: List<Order>) {
        // Sembunyikan pesan kosong
        findViewById<TextView>(ID_EMPTY_ORDERS)?.visibility = android.view.View.GONE

        // Buat atau update RecyclerView untuk pesanan
        var rvOrders = findViewById<RecyclerView>(ID_RV_ORDERS)

        if (rvOrders == null) {
            rvOrders = RecyclerView(this).apply {
                id = ID_RV_ORDERS
                layoutParams = android.widget.LinearLayout.LayoutParams(
                    android.widget.LinearLayout.LayoutParams.MATCH_PARENT,
                    android.widget.LinearLayout.LayoutParams.MATCH_PARENT
                )
                layoutManager = LinearLayoutManager(this@MainActivity)
                setPadding(16, 16, 16, 16)
            }
            (binding.root as android.view.ViewGroup).addView(rvOrders)
        }

        rvOrders.adapter = OrderAdapter(orders)
        rvOrders.visibility = android.view.View.VISIBLE
    }

    private fun showLogoutDialog() {
        androidx.appcompat.app.AlertDialog.Builder(this)
            .setTitle("Logout")
            .setMessage("Apakah Anda yakin ingin logout?")
            .setPositiveButton("Ya") { dialog, _ ->
                val intent = Intent(this, LoginActivity::class.java)
                intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK
                startActivity(intent)
                finish()
            }
            .setNegativeButton("Tidak") { dialog, _ ->
                dialog.dismiss()
            }
            .show()
    }

    private fun setupBanner() {
        val bannerList = listOf(
            Banner("Coffee Fresh", "Rasakan kenikmatan kopi terbaik", R.drawable.banner1),
            Banner("Special Blend", "Kopi racikan khusus barista", R.drawable.banner2),
            Banner("Weekend Promo", "Buy 1 Get 1 every weekend", R.drawable.banner3)
        )

        bannerAdapter = BannerAdapter(bannerList)
        binding.viewPager.adapter = bannerAdapter
    }

    private fun setupCoffeeMenu() {
        val coffeeList = listOf(
            Coffee("Espresso", "Kopi murni dengan rasa kuat", 25000, R.drawable.espresso),
            Coffee("Cappuccino", "Espresso dengan steamed milk foam", 35000, R.drawable.cappuccino),
            Coffee("Latte", "Espresso dengan steamed milk", 40000, R.drawable.latte),
            Coffee("Americano", "Espresso dengan air panas", 30000, R.drawable.americano),
            Coffee("Mocha", "Coklat dengan espresso dan susu", 45000, R.drawable.mocha),
            Coffee("Macchiato", "Espresso dengan sedikit foam milk", 32000, R.drawable.macchiato),
            Coffee("Cold Brew", "Kopi seduh dingin 12 jam", 38000, R.drawable.coldbrew),
            Coffee("Vietnamese Coffee", "Kopi Vietnam dengan susu kental", 42000, R.drawable.vietnamese),
            Coffee("Affogato", "Es krim vanilla dengan espresso", 48000, R.drawable.affogato),
            Coffee("Turkish Coffee", "Kopi tradisional Turki", 35000, R.drawable.turkish)
        )

        coffeeAdapter = CoffeeAdapter(coffeeList) { coffee ->
            val intent = Intent(this, OrderDetailActivity::class.java)
            intent.putExtra("COFFEE", coffee)
            startActivity(intent)
        }

        binding.recyclerView.apply {
            layoutManager = GridLayoutManager(this@MainActivity, 2)
            adapter = coffeeAdapter
        }
    }
}