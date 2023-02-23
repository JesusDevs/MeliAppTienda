package com.example.meliapp.ui.sliderviewpager.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.MutableLiveData
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.example.meliapp.databinding.LayoutItemProductBinding
import com.example.meliapp.ui.FirstFragmentDirections
import com.example.meliapp.ui.ItemProduct
import com.example.meliapp.utils.loadImg


class RecommendSliderAdapter(private val items: MutableList<ItemProduct>,val frg: Fragment?) : RecyclerView.Adapter<RecommendSliderAdapter.ViewHolder>() {
    private val newListBenefcios: List<ItemProduct> = items
    private var selectedItem = MutableLiveData<ItemProduct>()
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
    fun updateList(list: MutableList<ItemProduct>) {
        items.clear()
        items.addAll(list)
        notifyDataSetChanged()
    }
    inner class ViewHolder(private val binding : LayoutItemProductBinding) : RecyclerView.ViewHolder(binding.root) {

        fun bindItems(item: ItemProduct) {
            binding.sliderItemTitle.text = item.title
            binding.sliderItemText.text = "Precio :" +item.price.toString()
            item.thumbnail?.let { binding.sliderItemImg.loadImg(it) }

            binding.sliderItemBtn.setOnClickListener {
                selectedItem.value = item
                val action = FirstFragmentDirections.actionFirstFragmentToPaymentFragment(selectedItem.value!!)
                frg?.findNavController()?.navigate(action)

            }
        }
    }
}
