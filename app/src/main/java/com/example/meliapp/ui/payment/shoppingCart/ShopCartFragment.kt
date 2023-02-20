package com.example.meliapp.ui.payment.shoppingCart

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.meliapp.R
import com.example.meliapp.core.status.Status
import com.example.meliapp.databinding.ShopCartFragmentBinding
import com.example.meliapp.datasource.PaymentMethodDataSource
import com.example.meliapp.local.database.ProductDatabase
import com.example.meliapp.local.entity.ItemProductEntity
import com.example.meliapp.repository.PaymentMethodRepository
import com.example.meliapp.ui.payment.adapter.PaymentShopCarAdapter
import com.example.meliapp.viewmodel.PaymentMethodsViewModel
import com.google.gson.Gson
import kotlinx.coroutines.launch

class ShopCartFragment : Fragment() {

    private var _binding: ShopCartFragmentBinding? = null
    private val binding get() = _binding!!
    private lateinit var adapter: PaymentShopCarAdapter
    private val bundle = Bundle()
    private val viewModel by viewModels<PaymentMethodsViewModel>() {
        PaymentMethodsViewModel
            .PaymentViewModelFactory(
                PaymentMethodRepository(
                    PaymentMethodDataSource(), ProductDatabase.getDataBase(requireActivity()).itemProductDao()))
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = ShopCartFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.payBtn.setOnClickListener {
            redirectDialogPayment()
        }
       //desplegar carro de compras
        getShoppingCart()
        onBack()
    }

    private fun onBack() {
        binding.imageBack.setOnClickListener {
            requireActivity().onBackPressedDispatcher.onBackPressed()
        }
    }

    private fun redirectDialogPayment() {
        findNavController().navigate(R.id.action_shopCartFragment_to_dialogPaymentFragment, bundle)
    }

    private fun getShoppingCart(){
        lifecycleScope.launch {
            lifecycle.repeatOnLifecycle(Lifecycle.State.STARTED) {

                viewModel.getShoppingCart().collect { result ->
                    result.let { response ->
                        when(response.status) {
                            Status.LOADING -> { }
                            Status.SUCCESS -> {
                                response.data?.let { items ->
                                    displayShopCart(items)
                                }
                            }

                            Status.ERROR -> {
                                Log.d("TAG", "items: ${response.message}")
                            }
                        }
                    }
                }
            }
        }
    }

    private fun displayShopCart(items: List<ItemProductEntity>) {
        binding.recyclerViewItems.layoutManager = LinearLayoutManager(context)
        adapter = PaymentShopCarAdapter(items = items as MutableList<ItemProductEntity>, context)
        binding.recyclerViewItems.adapter = adapter
        binding.txAmount.text = "$ " + calculateTotal(items).toString()

        binding.deleteAllBtn.setOnClickListener {
            deleteAll()
            adapter.updateList(items)
        }
    }

    private fun deleteAll() {
        viewModel.deleteAllProducts()
    }
    fun calculateTotal(items: List<ItemProductEntity>): Int {
        var total = 0
        for (item in items) {
            total += item.total ?: 0
        }
        bundle.putInt("price", total)
        return total
    }
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}