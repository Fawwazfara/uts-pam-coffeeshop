package com.example.coffeshop

import java.io.Serializable

data class Banner(
    val title: String,
    val description: String,
    val imageRes: Int = 0
) : Serializable // Tambahkan implements Serializable