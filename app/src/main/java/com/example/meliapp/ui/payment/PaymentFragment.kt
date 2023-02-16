package com.example.meliapp.ui.payment

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
import com.example.meliapp.databinding.PaymentFragmentBinding
import com.example.meliapp.datasource.PaymentMethodDataSource
import com.example.meliapp.model.payment.PaymentMethodItem
import com.example.meliapp.repository.PaymentMethodRepository
import com.example.meliapp.ui.ItemProduct
import com.example.meliapp.ui.payment.adapter.PaymentShopAdapter
import com.example.meliapp.viewmodel.PaymentMethodsViewModel
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class PaymentFragment : Fragment() {

    private var _binding: PaymentFragmentBinding? = null
    private val binding get() = _binding!!
    private lateinit var adapter: PaymentShopAdapter
   //instanciar viewmodel
    private val viewModel by viewModels<PaymentMethodsViewModel>(){
        PaymentMethodsViewModel
            .PaymentViewModelFactory(
                PaymentMethodRepository(
                    PaymentMethodDataSource())) }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = PaymentFragmentBinding.inflate(inflater, container, false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        getPaymentMethods()
        binding.button.setOnClickListener {
            //navigation
            findNavController().navigate(R.id.action_paymentFragment_to_dialogPaymentFragment)
            //aca llamar al servicio y pasar por bundle el objecto
        }
           //navigation
          // findNavController().navigate(R.id.action_FirstFragment_to_SecondFragment)

    }

    private fun displayPaymentMethods(list : List<PaymentMethodItem>) {
        binding.recyclerViewItems.layoutManager = LinearLayoutManager(context)
        adapter = PaymentShopAdapter(mockAdapter(), context)
        binding.recyclerViewItems.adapter = adapter
    }

    fun mockAdapter(): MutableList<ItemProduct> {
         var  postItems: MutableList<ItemProduct> = mutableListOf()
          postItems.add(ItemProduct("1", "Soda Estereo, ",13000,"https://www.spirit-of-rock.com/les%20goupes/S/Soda%20Stereo/pics/490551_logo.jpg","nuevo","Me verás volver"))
          postItems.add(ItemProduct("2", "Kuervos del sur ",15500,"https://pbs.twimg.com/media/EISqOeyXsAAUgx7.jpg","nuevo","El Vuelo Del Pillán"))
          postItems.add(ItemProduct("3", "Kuervos del sur ",12200,"https://vinilogarage.cl/wp-content/uploads/2021/05/190452520_10157802063250940_886593049488731790_n.jpg","nuevo","Canto a lo Brujo"))
          postItems.add(ItemProduct("4", "Muse ",11000,"https://www.lahiguera.net/musicalia/artistas/muse/disco/9294/muse_simulation_theory-portada.jpg","nuevo","Simulation Theory"))
          postItems.add(ItemProduct("7", "Pink Floyd ",19000,"https://e00-marca.uecdn.es/assets/multimedia/imagenes/2019/12/04/15754721234271.jpg","nuevo","The Wall"))
          postItems.add(ItemProduct("6", "Iron Maiden ",17000,"https://imagizer.imageshack.com/v2/629x887q90/924/LBWKky.jpg","nuevo","The Future Past Tour"))
          postItems.add(ItemProduct("5", "Bowie ",10000,"https://www.lahiguera.net/musicalia/artistas/david_bowie/disco/7962/david_bowie_legacy-portada.jpg","nuevo","Legacy"))
          postItems.add(ItemProduct("8", "Second ",10000,"https://www.lahiguera.net/musicalia/artistas/second/disco/5239/second_montana_rusa-portada.jpg","nuevo","Montana Rusa"))
          return postItems
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
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}