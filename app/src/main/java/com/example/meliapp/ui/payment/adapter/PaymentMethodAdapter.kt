package com.example.meliapp.ui.payment.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.lifecycle.MutableLiveData
import androidx.recyclerview.widget.RecyclerView
import com.example.meliapp.databinding.ItemContainerBinding
import com.example.meliapp.databinding.ItemContainerImgBinding
import com.example.meliapp.databinding.MethodItemBinding
import com.example.meliapp.databinding.ShopItemBinding
import com.example.meliapp.model.payment.PaymentMethodItem
import com.example.meliapp.ui.ItemProduct
import com.example.meliapp.utils.loadGif
import com.example.meliapp.utils.loadImg
import com.example.meliapp.utils.loadImgShop
import com.example.meliapp.utils.loadSvgReco

class PaymentMethodAdapter(private val items: List<PaymentMethodItem>, private val context: Context?) : RecyclerView.Adapter<PaymentMethodAdapter.ViewHolder>() {
    private var selectedItem = MutableLiveData<PaymentMethodItem>()
    fun selectedItem()=selectedItem
    private val itemsList: List<PaymentMethodItem> =items
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(MethodItemBinding.inflate(LayoutInflater.from(parent.context),parent,false)) }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val items = itemsList[position]
        holder.bindItems(items)
    }

    override fun getItemCount(): Int {
        return itemsList.size
    }

    inner class ViewHolder(private val binding: MethodItemBinding) : RecyclerView.ViewHolder(binding.root) {

      fun bindItems(item: PaymentMethodItem) {
            with(binding) {

                item.thumbnail?.let { urlImg ->
                    if (item.thumbnail.isNotEmpty()) {
                        try {
                            if (urlImg.contains(".jpg") || urlImg.contains(".jpeg") || item.thumbnail.contains(".png")) {
                                logoIv.loadImgShop(item.thumbnail)
                                } else if (urlImg.contains(".svg")) {
                                logoIv.loadSvgReco(item.thumbnail)
                                } else if (urlImg.contains(".gif")) {
                                logoIv.loadGif(urlImg)
                                }
                            } catch (e: Exception) { e.printStackTrace() } }
                }
            }
        }

    }

    }