package com.example.meliapp.ui.sliderviewpager.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.meliapp.databinding.LayoutItemProductBinding
import com.example.meliapp.ui.ItemProduct
import com.example.meliapp.utils.loadImg


class RecommendSliderAdapter(private val items: List<ItemProduct>, private val context: Context?) : RecyclerView.Adapter<RecommendSliderAdapter.ViewHolder>() {
    private val newListBenefcios: List<ItemProduct> = items

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(LayoutItemProductBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val items: ItemProduct = newListBenefcios[position]
        holder.bindItems(items)
    }

    override fun getItemCount(): Int {
       return newListBenefcios.size
    }

    inner class ViewHolder(private val binding : LayoutItemProductBinding) : RecyclerView.ViewHolder(binding.root) {

        fun bindItems(item: ItemProduct) {
            item.thumbnail?.let { binding.sliderItemImg.loadImg(it) }
        }
    }
}
