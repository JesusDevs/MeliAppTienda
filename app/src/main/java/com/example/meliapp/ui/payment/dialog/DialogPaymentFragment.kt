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
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.GridLayoutManager
import com.example.meliapp.R
import com.example.meliapp.core.status.Status
import com.example.meliapp.databinding.MethodDialogBinding
import com.example.meliapp.datasource.PaymentMethodDataSource
import com.example.meliapp.local.database.ProductDatabase
import com.example.meliapp.model.payment.bank.BankItem
import com.example.meliapp.model.payment.installments.InstallmentsResponseItem
import com.example.meliapp.model.payment.installments.PayerCost
import com.example.meliapp.model.payment.method.PaymentMethodItem
import com.example.meliapp.repository.PaymentMethodRepository
import com.example.meliapp.ui.payment.adapter.InstallmentsAdapter
import com.example.meliapp.ui.payment.adapter.PaymentBankAdapter
import com.example.meliapp.ui.payment.adapter.PaymentMethodAdapter
import com.example.meliapp.utils.showCustomToast
import com.example.meliapp.viewmodel.PaymentMethodsViewModel
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class DialogPaymentFragment : BottomSheetDialogFragment() {

    private var _binding: MethodDialogBinding? = null
    private val binding get() = _binding!!
    private lateinit var adapter: PaymentMethodAdapter
    private lateinit var adapterBank: PaymentBankAdapter
    private lateinit var adapterInstallments: InstallmentsAdapter
    private val args by navArgs<DialogPaymentFragmentArgs>()
    private val price by lazy { args.price}
    private var bank: BankItem? = null
    private var method: PaymentMethodItem? = null
    private var installments: PayerCost? = null
    private val bundlePurchase by lazy { Bundle() }
    private val viewModel by viewModels<PaymentMethodsViewModel>() {
        PaymentMethodsViewModel
            .PaymentViewModelFactory(
                PaymentMethodRepository(
                    PaymentMethodDataSource(),ProductDatabase.getDataBase(requireActivity()).itemProductDao()))
    }
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
        binding.payBtn.visibility = View.VISIBLE
        binding.payBtn.isEnabled = false
        getPaymentMethods()
        binding.payBtn.setOnClickListener {
            //ir al primer fragment con bundle purchase
            findNavController().navigate(R.id.action_dialogPaymentFragment_to_FirstFragment,bundlePurchase)
            dismiss()
            displayToast(R.string.payment_successo)
        }
    }

    /**
     * funciones que consumen los servicios GET
     * @sample getPaymentMBanks
     * @sample getPaymentMethods
     * @sample getPaymentInstallments
     */
    private fun getPaymentMethods() {
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.CREATED) {
                viewModel.getPaymentMethods().collect { result ->
                    result.let { response ->
                        when (response.status) {
                            Status.LOADING -> {}
                            Status.SUCCESS -> {
                                response.data?.let { method ->
                                    if (method.isNotEmpty()) {
                                        Log.d("getmethods", "getmethods: ${method}")
                                        displayPaymentMethods(method)

                                    } else {

                                    }
                                }
                            }
                            Status.ERROR -> {
                                Log.d("TAG", "getError: ${response.message}")
                            }
                        }
                    }
                }
            }
        }
    }

    private fun getPaymentMBanks(paymentMethod: String) {
        lifecycleScope.launch {
            viewModel.getPaymentBanks(paymentMethod = paymentMethod).collectLatest { result ->
                result.let { response ->
                    when (response.status) {
                        Status.LOADING -> {}
                        Status.SUCCESS -> {
                            response.data?.let { bank ->
                                if (bank.isNotEmpty()) {
                                    Log.d("getmethods", "getmethods: ${bank}")
                                    displayPaymentBank(bank)

                                } else {

                                }
                            }
                        }
                        Status.ERROR -> {
                            Log.d("TAG", "get: ${response.message}")
                        }
                    }
                }
            }
        }
    }

    private fun getPaymentInstallments(paymentMethod: String, bank: String, amount: Double) {
        lifecycleScope.launch {
            viewModel.getPaymentInstalment(
                paymentMethod = paymentMethod,
                bank = bank,
                amount = amount
            ).collectLatest { result ->
                result.let { response ->
                    when (response.status) {
                        Status.LOADING -> {}
                        Status.SUCCESS -> {
                            response.data?.let { installments ->
                                if (bank.isNotEmpty()) {
                                    Log.d("getInstall", "getmethods: ${bank}")
                                    displayInstallment(installments)
                                } else {

                                }
                            }
                        }
                        Status.ERROR -> {
                            Log.d("TAG", "get error: ${response.message}")
                        }
                    }
                }
            }
        }
    }
    /**
     * funciones que reciben listados desde los servicios para desplegar la vista
     * @sample displayPaymentBank
     * @sample displayInstallment
     * @sample displayPaymentMethods
     * @sample displayToast
     */
    private fun displayPaymentMethods(list: List<PaymentMethodItem>) {
        val listFilter = list as MutableList<PaymentMethodItem>
        //eliminar de la lista los que no son credit_card
        listFilter.removeIf { it.paymentTypeId != "credit_card" }
        binding.recyclerViewItems.layoutManager = GridLayoutManager(context, 5)
        adapter = PaymentMethodAdapter(items = listFilter, context = context,
            selectedListener = { select -> onItemSelected(select) },
            selectedPosition = { position -> onItemPosition(position) })
        binding.recyclerViewItems.adapter = adapter

    }

    private fun displayPaymentBank(listbank: List<BankItem>) {
        val listFilterBank = listbank as MutableList<BankItem>
        listFilterBank.let { it -> it.removeIf { filtro -> filtro.thumbnail!!.contains("gif") } }
        binding.recyclerViewBank.layoutManager = GridLayoutManager(context, 2)
        adapterBank = PaymentBankAdapter(listFilterBank) { select ->
            onBankSelected(select)
        }

        binding.recyclerViewBank.adapter = adapterBank
    }

    private fun displayInstallment(listInstallments: List<InstallmentsResponseItem>) {
        val listPayerCost = listInstallments[0].payerCosts as MutableList<PayerCost>
        binding.materialCardViewBank.visibility = View.GONE
        binding.recyclerViewInstalments.layoutManager = GridLayoutManager(context, 2)
        adapterInstallments = InstallmentsAdapter(
            items = listPayerCost,
            selectedListener = { select -> onInstallmentSelected(select) },
            selectedPosition = { position -> onInstallmentPosition(position) })
        binding.recyclerViewInstalments.adapter = adapterInstallments
    }

    private fun displayToast(message: Int) {
        Toast(context).showCustomToast(getString(message), requireActivity())
    }

    /**
     * Seleccionar item de la lista y obtener sus posiciones para marcar cheks en los items
     * @sample onItemPosition(position: Int)
     * @sample onInstallmentPosition(position: Int)
     */
    private fun onItemPosition(position: Int) {
        adapter.setSelection(position)
        adapter.notifyItemChanged(position)
    }
    private fun onInstallmentPosition(position: Int) {
        adapterInstallments.setSelection(position)
        adapterInstallments.notifyItemChanged(position)

    }

    /**
     * Seleccionar item de la lista y obtener sus atributos para consumir servicios
     * @sample onItemSelected(item: PaymentMethodItem)
     * @sample onBankSelected(item: BankItem)
     * @sample onInstallmentSelected(item: PayerCost)
     *
     */
    private fun onItemSelected(item: PaymentMethodItem) {
        Log.d("item", "item: $item")
        method = item
        binding.materialCardViewBank.visibility = View.VISIBLE
        binding.materialCardViewInstalments.visibility = View.GONE
        item.id?.let { getPaymentMBanks(it) }
        bundlePurchase.putString("payment", item.name)
        binding.payBtn.isEnabled = false
    }

    private fun onBankSelected(item: BankItem) {
        Toast.makeText(context, item.name, Toast.LENGTH_SHORT).show()
        binding.materialCardViewInstalments.visibility = View.VISIBLE
        bank = item
        method?.id?.let { method ->
            bank?.id?.let { bank ->
                getPaymentInstallments(
                    method, bank = bank,
                    amount = price.toDouble())
            }
        }
        bundlePurchase.putDouble("amount", price.toDouble())
        bundlePurchase.putString("bank", item.name)
    }

    private fun onInstallmentSelected(item: PayerCost) {
        binding.payBtn.isEnabled = true
        installments = item
        bundlePurchase.putString("installments", item.recommendedMessage)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}