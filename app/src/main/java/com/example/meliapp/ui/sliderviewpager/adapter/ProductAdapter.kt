package com.example.meliapp.ui.sliderviewpager.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.lifecycle.MutableLiveData
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.meliapp.databinding.ItemContainerImgBinding
import com.example.meliapp.ui.ItemProduct
import com.example.meliapp.utils.loadGif
import com.example.meliapp.utils.loadImg
import com.example.meliapp.utils.loadSvgBeneficios
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class ProductAdapter(private val items: List<ItemProduct>, private val context: Context?) : RecyclerView.Adapter<ProductAdapter.ViewHolder>() {
    private var selectedItem = MutableLiveData<ItemProduct>()
    fun selectedItem()=selectedItem
    private val itemsList: List<ItemProduct> =items
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(ItemContainerImgBinding.inflate(LayoutInflater.from(parent.context),parent,false)) }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val items = itemsList[position]
        holder.bindItems(items)
    }

    override fun getItemCount(): Int {
        return itemsList.size
    }

    inner class ViewHolder(private val binding: ItemContainerImgBinding) : RecyclerView.ViewHolder(binding.root) {

      fun bindItems(item: ItemProduct) {
            with(binding) {
                sliderItemTitle.text = item.title
                item.thumbnail?.let { urlImg ->
                    if (!item.thumbnail.isNullOrEmpty()) {
                        try {
                            if (urlImg.contains(".jpg") || urlImg.contains(".jpeg") || item.thumbnail!!.contains(".png")) {
                                    imagePost.loadImg(item.thumbnail)
                                } else if (urlImg.contains(".svg")) {
                                    imagePost.loadSvgBeneficios(item.thumbnail!!)
                                } else if (urlImg.contains(".gif")) {
                                    imagePost.loadGif(urlImg)
                                }
                            } catch (e: Exception) { e.printStackTrace() } }
                }
            }
        }

    }

    }