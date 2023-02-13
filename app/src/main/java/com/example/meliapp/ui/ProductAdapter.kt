package com.example.meliapp.ui

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.lifecycle.MutableLiveData
import androidx.recyclerview.widget.RecyclerView
import com.example.meliapp.databinding.ItemContainerBinding
import com.example.meliapp.utils.loadGif
import com.example.meliapp.utils.loadImg
import com.example.meliapp.utils.loadSvgBeneficios

class ProductAdapter(private val items: List<ItemProduct>, private val context: Context?) : RecyclerView.Adapter<ProductAdapter.ViewHolder>() {
    private var selectedItem = MutableLiveData<ItemProduct>()
    fun selectedItem()=selectedItem
    private val itemsList: List<ItemProduct> =items
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(ItemContainerBinding.inflate(LayoutInflater.from(parent.context),parent,false)) }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val items = itemsList[position]
        holder.bindItems(items)
    }

    override fun getItemCount(): Int {
        return itemsList.size
    }

    inner class ViewHolder(private val binding: ItemContainerBinding) : RecyclerView.ViewHolder(binding.root) {

        fun bindItems(item: ItemProduct) {
            with(binding) {
                item.thumbnail?.let { urlImg ->
                    if (!item.thumbnail.isNullOrEmpty()) {
                        try {
                            if (urlImg.contains(".jpg") || urlImg.contains(".jpeg") || item.thumbnail!!.contains(".png"))
                            { imagePost.loadImg(item.thumbnail!!) }
                            else if (urlImg.contains(".svg")){
                                imagePost.loadSvgBeneficios(item.thumbnail!!) }
                            else if (urlImg.contains(".gif")){ imagePost.loadGif(urlImg) }
                        } catch (e: Exception) { e.printStackTrace() }
                    }
                }

            }
        }

    }

    }