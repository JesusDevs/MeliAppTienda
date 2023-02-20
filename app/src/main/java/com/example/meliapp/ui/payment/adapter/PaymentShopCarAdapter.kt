package com.example.meliapp.ui.payment.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.lifecycle.MutableLiveData
import androidx.recyclerview.widget.RecyclerView
import com.example.meliapp.databinding.ShopCarItemBinding
import com.example.meliapp.local.entity.ItemProductEntity
import com.example.meliapp.ui.ItemProduct
import com.example.meliapp.utils.loadGif
import com.example.meliapp.utils.loadImgShop
import com.example.meliapp.utils.loadSvgReco

class PaymentShopCarAdapter(private val items: List<ItemProductEntity>, private val context: Context?) : RecyclerView.Adapter<PaymentShopCarAdapter.ViewHolder>() {
    private var selectedItem = MutableLiveData<ItemProduct>()
    fun selectedItem()=selectedItem
    private val itemsList: List<ItemProductEntity> =items
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(ShopCarItemBinding.inflate(LayoutInflater.from(parent.context),parent,false)) }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val items = itemsList[position]
        holder.bindItems(items)
    }

    override fun getItemCount(): Int {
        return itemsList.size
    }

    inner class ViewHolder(private val binding: ShopCarItemBinding) : RecyclerView.ViewHolder(binding.root) {

      fun bindItems(item: ItemProductEntity) {
            with(binding) {
               binding.productName.text = item.title
                binding.productPrice.text = item.price.toString()
                binding.productQuantity.text = item.quantity.toString()
                item.thumbnail?.let { urlImg ->
                    if (item.thumbnail.isNotEmpty()) {
                        try {
                            if (urlImg.contains(".jpg") || urlImg.contains(".jpeg") || item.thumbnail.contains(".png")) {
                                productImage.loadImgShop(item.thumbnail)
                                } else if (urlImg.contains(".svg")) {
                                productImage.loadSvgReco(item.thumbnail)
                                } else if (urlImg.contains(".gif")) {
                                productImage.loadGif(urlImg)
                                }
                            } catch (e: Exception) { e.printStackTrace() } }
                }
            }
        }

    }

    }