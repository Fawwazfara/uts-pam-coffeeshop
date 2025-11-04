package com.example.coffeshop

import java.io.Serializable

data class Coffee(
    val name: String,
    val description: String,
    val price: Int,
    val imageRes: Int = 0,
    var quantity: Int = 0
) : Serializable { // Tambahkan implements Serializable
    // Function untuk hitung total harga
    fun getTotalPrice(): Int = price * quantity
}