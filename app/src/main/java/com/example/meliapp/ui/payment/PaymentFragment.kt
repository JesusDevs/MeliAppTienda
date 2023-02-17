package com.example.meliapp.ui.payment

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.meliapp.R
import com.example.meliapp.core.status.Status
import com.example.meliapp.databinding.PaymentFragmentBinding
import com.example.meliapp.datasource.PaymentMethodDataSource
import com.example.meliapp.model.payment.method.PaymentMethodItem
import com.example.meliapp.repository.PaymentMethodRepository
import com.example.meliapp.ui.ItemProduct
import com.example.meliapp.ui.payment.adapter.PaymentShopAdapter
import com.example.meliapp.utils.loadImgShop
import com.example.meliapp.viewmodel.PaymentMethodsViewModel
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class PaymentFragment : Fragment() {

    private var _binding: PaymentFragmentBinding? = null
    private val binding get() = _binding!!
    private lateinit var adapter: PaymentShopAdapter
    private val args by navArgs<PaymentFragmentArgs>()
    private val recommend by lazy { args.product }
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

        binding.button.setOnClickListener {
            val bundle = Bundle()
            recommend?.price?.let { it1 -> bundle.putInt("price", it1.toInt()) }
            findNavController().navigate(R.id.action_paymentFragment_to_dialogPaymentFragment, bundle)
        }
        displayView()
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