package com.example.coffeshop

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.coffeshop.databinding.ItemCoffeeBinding

class CoffeeAdapter(
    private val coffeeList: List<Coffee>,
    private val onOrderClick: (Coffee) -> Unit
) : RecyclerView.Adapter<CoffeeAdapter.CoffeeViewHolder>() {

    inner class CoffeeViewHolder(private val binding: ItemCoffeeBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(coffee: Coffee) {
            binding.tvCoffeeName.text = coffee.name
            binding.tvCoffeeDesc.text = coffee.description
            binding.tvCoffeePrice.text = "Rp ${coffee.price}"
            binding.tvQuantity.text = coffee.quantity.toString()

            if (coffee.imageRes != 0) {
                binding.ivCoffee.setImageResource(coffee.imageRes)
            }

            // Tombol minus - FIX: Hilangkan background agar tidak nabrak
            binding.btnMinus.setOnClickListener {
                if (coffee.quantity > 0) {
                    coffee.quantity--
                    binding.tvQuantity.text = coffee.quantity.toString()
                    updateOrderButton(coffee)
                }
            }

            // Tombol plus
            binding.btnPlus.setOnClickListener {
                coffee.quantity++
                binding.tvQuantity.text = coffee.quantity.toString()
                updateOrderButton(coffee)
            }

            // Tombol pesan
            binding.btnOrder.setOnClickListener {
                if (coffee.quantity > 0) {
                    onOrderClick(coffee)
                } else {
                    // Jika quantity masih 0, set jadi 1
                    coffee.quantity = 1
                    binding.tvQuantity.text = coffee.quantity.toString()
                    updateOrderButton(coffee)
                }
            }

            updateOrderButton(coffee)
        }

        private fun updateOrderButton(coffee: Coffee) {
            if (coffee.quantity > 0) {
                binding.btnOrder.text = "Pesan (${coffee.quantity})"
                binding.btnOrder.isEnabled = true
                binding.btnOrder.alpha = 1.0f
            } else {
                binding.btnOrder.text = "Pesan"
                binding.btnOrder.isEnabled = true
                binding.btnOrder.alpha = 0.7f
            }

            // Update tombol minus
            binding.btnMinus.isEnabled = coffee.quantity > 0
            binding.btnMinus.alpha = if (coffee.quantity > 0) 1.0f else 0.5f
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CoffeeViewHolder {
        val binding = ItemCoffeeBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return CoffeeViewHolder(binding)
    }

    override fun onBindViewHolder(holder: CoffeeViewHolder, position: Int) {
        holder.bind(coffeeList[position])
    }

    override fun getItemCount(): Int = coffeeList.size
}