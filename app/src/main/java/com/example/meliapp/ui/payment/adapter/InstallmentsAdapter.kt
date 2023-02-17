package com.example.meliapp.ui.payment.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.lifecycle.MutableLiveData
import androidx.recyclerview.widget.RecyclerView
import com.example.meliapp.databinding.BankItemBinding
import com.example.meliapp.databinding.InstallmentsItemBinding
import com.example.meliapp.databinding.MethodBankItemBinding
import com.example.meliapp.databinding.MethodItemBinding
import com.example.meliapp.model.payment.bank.BankItem
import com.example.meliapp.model.payment.installments.InstallmentsResponseItem
import com.example.meliapp.model.payment.installments.PayerCost
import com.example.meliapp.model.payment.method.PaymentMethodItem
import com.example.meliapp.utils.loadGif
import com.example.meliapp.utils.loadImgBank
import com.example.meliapp.utils.loadImgShop
import com.example.meliapp.utils.loadSvgReco

class InstallmentsAdapter(
    private val items: List<PayerCost>,
    private val context: Context?,
    private val selectedListener: (PayerCost) -> Unit,
    private var selectedPosition: (Int)->Unit
) : RecyclerView.Adapter<InstallmentsAdapter.ViewHolder>() {
    private var selectedItem = MutableLiveData<PayerCost>()
    private var selected = -1
    fun selectedItem()=selectedItem
    private val itemsList: List<PayerCost> =items
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(InstallmentsItemBinding.inflate(LayoutInflater.from(parent.context),parent,false)) }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val items = itemsList[position]
        holder.bindItems(items,selectedListener,selectedPosition)
    }
    fun setSelection(position: Int) {
        selected = position
        notifyDataSetChanged()
    }
    override fun getItemCount(): Int {
        return itemsList.size
    }

    inner class ViewHolder(private val binding: InstallmentsItemBinding) : RecyclerView.ViewHolder(binding.root) {

      fun bindItems(item: PayerCost,
                    selectedListener: (PayerCost) -> Unit,
                    selectedPosition: (Int) -> Unit) {
            with(binding) {
                if (selected == adapterPosition){
                    logoIv.setImageResource(android.R.drawable.checkbox_on_background)

                } else {
                    logoIv.setImageResource(android.R.drawable.checkbox_off_background)
                }
                recommendTv.text = item.recommendedMessage
                itemView.setOnClickListener {
                    selectedPosition(adapterPosition)
                    notifyItemChanged(adapterPosition)
                    selectedItem.value = item
                    selectedListener(item)
                }
            }
        }

    }

    }