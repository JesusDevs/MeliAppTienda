package com.example.meliapp.ui

import android.content.Context
import android.graphics.Rect
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.widget.SearchView
import androidx.core.os.HandlerCompat.postDelayed
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import androidx.viewpager2.widget.ViewPager2
import cl.wom.transformacion.appwommobile.beneficios.adapter.HorizontalMarginItemDecoration
import com.example.meliapp.R
import com.example.meliapp.databinding.FragmentFirstBinding
import com.example.meliapp.model.payment.PurchaseItem
import com.example.meliapp.ui.sliderviewpager.adapter.ProductAdapter
import com.example.meliapp.ui.sliderviewpager.adapter.RecommendSliderAdapter
import com.example.meliapp.utils.AdministradorPreferencias
import com.example.meliapp.utils.CacheUtilShared
import com.google.android.material.tabs.TabLayoutMediator
import kotlin.math.abs

class FirstFragment : Fragment() {

    private var _binding: FragmentFirstBinding? = null
    private val binding get() = _binding!!
    private lateinit var adapter: ProductAdapter
    private lateinit var adapterRecommned: RecommendSliderAdapter
    private var namebank :String? =null
    private var installments  :String? =null
    private var payment :String? =null
    private var amount  :String? =null
    private val purchaseItem by lazy { args.purchase }
    private val args by navArgs<FirstFragmentArgs>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        getArgs()
        displayToastPurchase()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentFirstBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
     /*  listado de objetos no destacados
        binding.recyclerViewItems.layoutManager = StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL)
        adapter = ProductAdapter(mockAdapter(), context)
        binding.recyclerViewItems.adapter = adapter
*/
        adapterRecommned = RecommendSliderAdapter(mockAdapter(),context,this)
        val tab =binding.include.tabLayout
        val viewPager = binding.include.viewpager2
        viewPager.adapter = adapterRecommned
        viewPager.setPageTransformer(transform())
        viewPager.offscreenPageLimit = 3
        TabLayoutMediator(tab,viewPager){tab, position ->}.attach()
        val decoration = context?.let { HorizontalMarginItemDecoration(it, R.dimen.viewpager_current_item_horizontal_margin) }
        viewPager.addItemDecoration(decoration!!)
        searchProducts()
    }

    private fun searchProducts() {
        binding.searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                return false
            }
            override fun onQueryTextChange(newText: String?): Boolean {
                val list = mockAdapter().filter { it.title?.contains(newText.toString(), true) ?: false } as MutableList<ItemProduct>
                //validar que el texto no sea vacio
                if (newText.isNullOrEmpty()) {
                    adapterRecommned.updateList(mockAdapter())
                    return false
                }
                return false
            }
        })
    }

    private fun transform() : ViewPager2.PageTransformer {
        val nextItemVisiblePx = resources.getDimension(R.dimen.viewpager_next_item_visible)
        val currentItemHorizontalMarginPx =
            resources.getDimension(R.dimen.viewpager_current_item_horizontal_margin)
        val pageTranslationX = nextItemVisiblePx + currentItemHorizontalMarginPx
        val pageTransformer = ViewPager2.PageTransformer { page: View, position: Float ->
            page.translationX = -pageTranslationX * position
            // cambio de tamaño item siguiente
            page.scaleY = 1 - (0.25f * abs(position))
            // efecto de difuminado en los items
            //  page.alpha = 0.25f + (1 - abs(position))
        }
        return pageTransformer
    }

    fun mockAdapter(): MutableList<ItemProduct> {
         var  postItems: MutableList<ItemProduct> = mutableListOf()
          postItems.add(ItemProduct("1", "Soda Estereo ",13000,"https://www.spirit-of-rock.com/les%20goupes/S/Soda%20Stereo/pics/490551_logo.jpg","nuevo","Me verás volver"))
          postItems.add(ItemProduct("2", "Kuervos del sur ",15500,"https://pbs.twimg.com/media/EISqOeyXsAAUgx7.jpg","nuevo","El Vuelo Del Pillán"))
          postItems.add(ItemProduct("3", "Kuervos del sur ",12200,"https://vinilogarage.cl/wp-content/uploads/2021/05/190452520_10157802063250940_886593049488731790_n.jpg","nuevo","Canto a lo Brujo"))
          postItems.add(ItemProduct("4", "Muse ",11000,"https://www.lahiguera.net/musicalia/artistas/muse/disco/9294/muse_simulation_theory-portada.jpg","nuevo","Simulation Theory"))
          postItems.add(ItemProduct("7", "Pink Floyd ",19000,"https://e00-marca.uecdn.es/assets/multimedia/imagenes/2019/12/04/15754721234271.jpg","nuevo","The Wall"))
          postItems.add(ItemProduct("6", "Iron Maiden ",17000,"https://imagizer.imageshack.com/v2/629x887q90/924/LBWKky.jpg","nuevo","The Future Past Tour"))
          postItems.add(ItemProduct("5", "Bowie ",10000,"https://www.lahiguera.net/musicalia/artistas/david_bowie/disco/7962/david_bowie_legacy-portada.jpg","nuevo","Legacy"))
          postItems.add(ItemProduct("8", "Second ",10000,"https://www.lahiguera.net/musicalia/artistas/second/disco/5239/second_montana_rusa-portada.jpg","nuevo","Montana Rusa"))
          return postItems
      }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
    private fun getArgs() {
        arguments?.let {
            namebank = it.getString("bank")
            installments = it.getString("installments")
            payment = it.getString("payment")
            amount = it.getDouble("amount", 0.0).toString()
        }
    }

    private fun displayToastPurchase() {
        if (arguments != null && !amount.equals("0.0")) {
            //handler postdelayed para que se ejecute despues de que se cargue la vista
            Handler(Looper.getMainLooper()).postDelayed({
                Toast.makeText(
                    context,
                    "monto pagado : " + amount + "\n" +
                            "Banco : " + namebank + "\n" +
                            "Cuotas : " + installments + "\n" +
                            "Medio de pago : " + payment, Toast.LENGTH_LONG
                ).show()
            }, 800)
        }
    }
}