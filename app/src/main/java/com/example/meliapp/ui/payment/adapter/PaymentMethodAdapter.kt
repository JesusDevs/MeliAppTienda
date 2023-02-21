package com.example.meliapp.ui.payment.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.MutableLiveData
import androidx.recyclerview.widget.RecyclerView
import com.example.meliapp.R
import com.example.meliapp.databinding.MethodItemBinding
import com.example.meliapp.model.payment.method.PaymentMethodItem
import com.example.meliapp.utils.loadGif
import com.example.meliapp.utils.loadImgShop
import com.example.meliapp.utils.loadSvgReco

class PaymentMethodAdapter(
    private val items: List<PaymentMethodItem>,
    private val context: Context?,
    private val selectedListener: (PaymentMethodItem) -> Unit,
    private var selectedPosition: (Int)->Unit
) : RecyclerView.Adapter<PaymentMethodAdapter.ViewHolder>() {
    private var selectedItem = MutableLiveData<PaymentMethodItem>()
    private var selected = -1
    private val itemsList: List<PaymentMethodItem> =items
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(MethodItemBinding.inflate(LayoutInflater.from(parent.context),parent,false)) }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val items = itemsList[position]
        holder.bindItems(items,selectedListener,selectedPosition)
    }

    override fun getItemCount(): Int {
        return itemsList.size
    }
    fun setSelection(position: Int) {
        selected = position
        notifyDataSetChanged()
    }
    inner class ViewHolder(private val binding: MethodItemBinding) : RecyclerView.ViewHolder(binding.root) {

      fun bindItems(
          item: PaymentMethodItem,
          selectedListener: (PaymentMethodItem) -> Unit,
          selectedPosition: (Int) -> Unit
      ) {
            with(binding) {
                if (selected == adapterPosition){
                    materialCardView3.strokeWidth = 2
                    materialCardView3.strokeColor = context?.getColor( R.color.teal_200) ?: 0
                } else {
                    materialCardView3.strokeWidth = 0
                    materialCardView3.strokeColor = context?.getColor( com.google.android.material.R.color.mtrl_btn_transparent_bg_color) ?: 0
                }
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
                itemView.setOnClickListener {
                    selectedItem.value = item
                    selectedPosition(adapterPosition)
                    selectedListener(item)
                    notifyItemChanged(adapterPosition)


                }
            }

        }

    }

    }