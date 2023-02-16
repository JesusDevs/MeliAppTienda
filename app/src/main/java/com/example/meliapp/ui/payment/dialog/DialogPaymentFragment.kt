package com.example.meliapp.ui.payment.dialog

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.meliapp.R
import com.example.meliapp.core.status.Status
import com.example.meliapp.databinding.MethodDialogBinding
import com.example.meliapp.datasource.PaymentMethodDataSource
import com.example.meliapp.model.payment.bank.BankItem
import com.example.meliapp.model.payment.method.PaymentMethodItem
import com.example.meliapp.repository.PaymentMethodRepository
import com.example.meliapp.ui.payment.adapter.PaymentBankAdapter
import com.example.meliapp.ui.payment.adapter.PaymentMethodAdapter
import com.example.meliapp.viewmodel.PaymentMethodsViewModel
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class DialogPaymentFragment : BottomSheetDialogFragment(){

    private var _binding: MethodDialogBinding? = null
    private val binding get() = _binding!!
    private lateinit var adapter: PaymentMethodAdapter
    private lateinit var adapterBank: PaymentBankAdapter
    private lateinit var adapterRecommned: PaymentMethodAdapter
    private val args by navArgs<DialogPaymentFragmentArgs>()

    private val viewModel by viewModels<PaymentMethodsViewModel>(){
        PaymentMethodsViewModel
            .PaymentViewModelFactory(
                PaymentMethodRepository(
                    PaymentMethodDataSource()
                )
            ) }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = MethodDialogBinding.inflate(inflater, container, false)
        return binding.root

    }
    override fun getTheme(): Int = R.style.BottomSheetMenuTheme
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        //instanciar adapter
        getPaymentMethods()
    }
    private fun getPaymentMethods(){
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.CREATED) {
                viewModel.getPaymentMethods().collectLatest { result ->
                    result.let { response ->
                        when(response.status) {
                            Status.LOADING -> { }
                            Status.SUCCESS -> {
                                response.data?.let { method ->
                                    if (method.isNotEmpty()) {
                                        Log.d("getmethods", "getmethods: ${method}")
                                        displayPaymentMethods(method)

                                    }else{

                                    }
                                }
                            }
                            Status.ERROR -> {
                                Log.d("TAG", "getGames: ${response.message}")
                            }
                        }
                    }
                }
            }
        }}
    private fun getPaymentMBanks(paymentMethod:String){
        lifecycleScope.launch {
                viewModel.getPaymentBanks(paymentMethod = paymentMethod).collectLatest { result ->
                    result.let { response ->
                        when(response.status) {
                            Status.LOADING -> { }
                            Status.SUCCESS -> {
                                response.data?.let { bank ->
                                    if (bank.isNotEmpty()) {
                                        Log.d("getmethods", "getmethods: ${bank}")
                                        displayPaymentBank(bank)

                                    }else{

                                    }
                                }
                            }
                            Status.ERROR -> {
                                Log.d("TAG", "getGames: ${response.message}")
                            }
                        }
                    }
                }
        }}
    private fun displayPaymentMethods(list : List<PaymentMethodItem>) {
        val listFilter = list as MutableList<PaymentMethodItem>
        //eliminar de la lista los que no son credit_card
        listFilter.removeIf { it.paymentTypeId != "credit_card" }
        Log.d("listFilter", "listFilter: $listFilter")
        binding.recyclerViewItems.layoutManager = GridLayoutManager(context, 5)
        adapter = PaymentMethodAdapter(listFilter,context){ select->
            onItemSelected(select)
        }
        binding.recyclerViewItems.adapter = adapter

    }
    private fun displayPaymentBank(listbank : List<BankItem>) {
        val listFilterBank = listbank as MutableList<BankItem>
        // Filtros de testeo , clean list
       // listFilterBank.let { it -> it.removeIf {  filtro -> !filtro.name!!.contains("Banco")} }
        listFilterBank.let { it -> it.removeIf {  filtro ->  filtro.thumbnail!!.contains("gif")} }
        binding.recyclerViewBank.layoutManager = GridLayoutManager(context, 2)
        adapterBank = PaymentBankAdapter(listFilterBank,context){ select->
            onBankSelected(select)
        }

        binding.recyclerViewBank.adapter = adapterBank
    }

    fun onItemSelected(item: PaymentMethodItem) {
        Log.d("item", "item: ${item}")
        Toast.makeText(context, item.name, Toast.LENGTH_SHORT).show()
        item.id?.let { getPaymentMBanks(it) }
    }

    fun onBankSelected(item: BankItem) {
        Log.d("item", "item: ${item}")
        Toast.makeText(context, item.name, Toast.LENGTH_SHORT).show()
    }
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}