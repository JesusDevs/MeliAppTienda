package com.example.meliapp.ui.payment.dialog

import android.app.Dialog
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import androidx.viewpager2.widget.ViewPager2
import cl.wom.transformacion.appwommobile.beneficios.adapter.HorizontalMarginItemDecoration
import com.example.meliapp.R
import com.example.meliapp.core.status.Status
import com.example.meliapp.databinding.FragmentFirstBinding
import com.example.meliapp.databinding.MethodDialogBinding
import com.example.meliapp.datasource.PaymentMethodDataSource
import com.example.meliapp.model.payment.PaymentMethodItem
import com.example.meliapp.repository.PaymentMethodRepository
import com.example.meliapp.ui.ItemProduct
import com.example.meliapp.ui.payment.adapter.PaymentMethodAdapter
import com.example.meliapp.ui.payment.adapter.PaymentShopAdapter
import com.example.meliapp.ui.sliderviewpager.adapter.ProductAdapter
import com.example.meliapp.ui.sliderviewpager.adapter.RecommendSliderAdapter
import com.example.meliapp.viewmodel.PaymentMethodsViewModel
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.google.android.material.tabs.TabLayoutMediator
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import kotlin.math.abs

class DialogPaymentFragment : BottomSheetDialogFragment(){

    private var _binding: MethodDialogBinding? = null
    private val binding get() = _binding!!
    private lateinit var adapter: PaymentMethodAdapter
    private lateinit var adapterRecommned: PaymentMethodAdapter
    private val args by navArgs<DialogPaymentFragmentArgs>()

    private val viewModel by viewModels<PaymentMethodsViewModel>(){
        PaymentMethodsViewModel
            .PaymentViewModelFactory(
                PaymentMethodRepository(
                    PaymentMethodDataSource()
                )
            ) }

    private val paymentMethods by lazy { args.method }
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
    private fun displayPaymentMethods(list : List<PaymentMethodItem>) {
        val listFilter = list as MutableList<PaymentMethodItem>
        //eliminar de la lista los que no son credit_card
        listFilter.removeIf { it.paymentTypeId != "credit_card" }

        Log.d("listFilter", "listFilter: ${listFilter}")
        binding.recyclerViewItems.layoutManager = GridLayoutManager(context, 5)
        adapter = PaymentMethodAdapter(listFilter, context)
        binding.recyclerViewItems.adapter = adapter
    }
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}