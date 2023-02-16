package com.example.meliapp.ui.payment.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.lifecycle.MutableLiveData
import androidx.recyclerview.widget.RecyclerView
import com.example.meliapp.databinding.BankItemBinding
import com.example.meliapp.databinding.MethodBankItemBinding
import com.example.meliapp.databinding.MethodItemBinding
import com.example.meliapp.model.payment.bank.BankItem
import com.example.meliapp.model.payment.method.PaymentMethodItem
import com.example.meliapp.utils.loadGif
import com.example.meliapp.utils.loadImgBank
import com.example.meliapp.utils.loadImgShop
import com.example.meliapp.utils.loadSvgReco

class PaymentBankAdapter(
    private val items: List<BankItem>,
    private val context: Context?,
    private val selectedListener: (BankItem) -> Unit
) : RecyclerView.Adapter<PaymentBankAdapter.ViewHolder>() {
    private var selectedItem = MutableLiveData<BankItem>()
    fun selectedItem()=selectedItem
    private val itemsList: List<BankItem> =items
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(MethodBankItemBinding.inflate(LayoutInflater.from(parent.context),parent,false)) }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val items = itemsList[position]
        holder.bindItems(items,selectedListener)
    }

    override fun getItemCount(): Int {
        return itemsList.size
    }

    inner class ViewHolder(private val binding: MethodBankItemBinding) : RecyclerView.ViewHolder(binding.root) {

      fun bindItems(item: BankItem, selectedListener: (BankItem) -> Unit) {
            with(binding) {
                item.name?.let {titleTx.text = it }
                item.secureThumbnail?.let { urlImg ->
                    if (item.secureThumbnail.isNotEmpty()) {
                        try {
                            if (urlImg.contains(".jpg") || urlImg.contains(".jpeg") || item.secureThumbnail.contains(".png")) {
                                logoIv.loadImgBank(item.secureThumbnail)
                                } else if (urlImg.contains(".svg")) {
                                logoIv.loadSvgReco(item.secureThumbnail)
                                } else if (urlImg.contains(".gif")) {
                                logoIv.loadGif(urlImg)
                                }
                            } catch (e: Exception) { e.printStackTrace() } }
                }
                itemView.setOnClickListener {
                    selectedItem.value = item
                    selectedListener(item)
                }
            }
        }

    }

    }