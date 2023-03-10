package com.example.meliapp.ui.payment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.meliapp.R
import com.example.meliapp.databinding.PaymentFragmentBinding
import com.example.meliapp.datasource.PaymentMethodDataSource
import com.example.meliapp.local.database.ProductDatabase
import com.example.meliapp.local.entity.ItemProductEntity
import com.example.meliapp.repository.PaymentMethodRepository
import com.example.meliapp.ui.ItemProduct
import com.example.meliapp.ui.payment.adapter.PaymentShopCarAdapter
import com.example.meliapp.utils.loadImgShop
import com.example.meliapp.utils.showCustomToast
import com.example.meliapp.viewmodel.PaymentMethodsViewModel

class PaymentFragment : Fragment() {

    private var _binding: PaymentFragmentBinding? = null
    private val binding get() = _binding!!
    private val args by navArgs<PaymentFragmentArgs>()
    private val recommend by lazy { args.product }
    private val viewModel by viewModels<PaymentMethodsViewModel>() {
        PaymentMethodsViewModel
            .PaymentViewModelFactory(
                PaymentMethodRepository(
                    PaymentMethodDataSource(), ProductDatabase.getDataBase(requireActivity()).itemProductDao()))
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        recommend?.let {}
    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = PaymentFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        onBack()
        displayView()
        binding.selectedPayment.setOnClickListener {
            redirectDialogPayment()
        }
        binding.addProduct.setOnClickListener {
            Toast(requireContext()).showCustomToast("Producto agregado al carrito",requireActivity())
            insertItem(recommend!!)
        }
    }

    private fun redirectDialogPayment() {
        findNavController().navigate(R.id.action_paymentFragment_to_shopCartFragment)
    }
     private fun onBack() {
        binding.imageBack.setOnClickListener {
            requireActivity().onBackPressedDispatcher.onBackPressed()
        }
    }
    private fun insertItem(productEntity: ItemProduct) {
        viewModel.insertProduct(
            ItemProductEntity(
                id = productEntity.id?:"",
                title = productEntity.title,
                price = productEntity.price,
                thumbnail = productEntity.thumbnail,
                condition = productEntity.condition,
                description = productEntity.description,
                quantity = 1,
                favorite = false
            )
        )
    }
    private fun displayView() {
        binding.txAmount.text = "$ " + recommend?.price.toString()
        binding.cardItemInclude.sliderItemTitle.text = recommend?.title
        binding.cardItemInclude.sliderItemText.text = recommend?.price.toString()
        recommend?.thumbnail?.let { binding.cardItemInclude.sliderItemImg.loadImgShop(it) }
        recommend?.title?.let { binding.cardItemInclude.sliderItemTitle.text = it }
        binding.cardItemInclude.sliderItemBtn.visibility = View.GONE
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}