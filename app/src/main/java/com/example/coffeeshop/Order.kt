package com.example.coffeshop

import java.io.Serializable
import java.util.Date

data class Order(
    val id: String = System.currentTimeMillis().toString(), // ID unik berdasarkan timestamp
    val coffee: Coffee,
    val customerName: String,
    val customerAddress: String,
    val customerLandmark: String,
    val totalPrice: Int,
    val quantity: Int,
    val orderDate: Date = Date(), // Tanggal pesanan
    val status: String = "Diproses" // Status pesanan
) : Serializable