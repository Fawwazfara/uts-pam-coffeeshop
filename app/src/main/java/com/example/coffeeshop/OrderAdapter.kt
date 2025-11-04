package com.example.coffeshop

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.coffeshop.databinding.ItemOrderBinding
import java.text.SimpleDateFormat
import java.util.Locale

class OrderAdapter(private val orderList: List<Order>) :
    RecyclerView.Adapter<OrderAdapter.OrderViewHolder>() {

    inner class OrderViewHolder(private val binding: ItemOrderBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(order: Order) {
            binding.tvCoffeeName.text = order.coffee.name
            binding.tvStatus.text = order.status

            // Set warna status berdasarkan status pesanan
            when (order.status) {
                "Diproses" -> {
                    binding.tvStatus.setBackgroundColor(0xFFE8F5E8.toInt())
                    binding.tvStatus.setTextColor(0xFF4CAF50.toInt())
                }
                "Dikirim" -> {
                    binding.tvStatus.setBackgroundColor(0xFFE3F2FD.toInt())
                    binding.tvStatus.setTextColor(0xFF2196F3.toInt())
                }
                "Selesai" -> {
                    binding.tvStatus.setBackgroundColor(0xFFF3E5F5.toInt())
                    binding.tvStatus.setTextColor(0xFF9C27B0.toInt())
                }
            }

            binding.tvOrderDetail.text = "Jumlah: ${order.quantity} - Total: Rp ${order.totalPrice}"
            binding.tvCustomerInfo.text = "Nama: ${order.customerName} - Alamat: ${order.customerAddress}"

            // Format tanggal
            val dateFormat = SimpleDateFormat("dd MMM yyyy HH:mm", Locale.getDefault())
            binding.tvOrderDate.text = "Tanggal: ${dateFormat.format(order.orderDate)}"
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): OrderViewHolder {
        val binding = ItemOrderBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return OrderViewHolder(binding)
    }

    override fun onBindViewHolder(holder: OrderViewHolder, position: Int) {
        holder.bind(orderList[position])
    }

    override fun getItemCount(): Int = orderList.size
}