package com.example.coffeshop

object OrderManager {
    private val orders = mutableListOf<Order>()

    fun addOrder(order: Order) {
        orders.add(0, order) // Tambah di paling atas (yang terbaru)
    }

    fun getAllOrders(): List<Order> {
        return orders.toList()
    }

    fun getOrderCount(): Int {
        return orders.size
    }

    fun clearOrders() {
        orders.clear()
    }
}