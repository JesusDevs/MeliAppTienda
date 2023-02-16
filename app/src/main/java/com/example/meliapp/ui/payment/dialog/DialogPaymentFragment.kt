package com.example.meliapp.ui.payment.dialog

import android.app.Dialog
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import androidx.viewpager2.widget.ViewPager2
import cl.wom.transformacion.appwommobile.beneficios.adapter.HorizontalMarginItemDecoration
import com.example.meliapp.R
import com.example.meliapp.databinding.FragmentFirstBinding
import com.example.meliapp.databinding.MethodDialogBinding
import com.example.meliapp.ui.ItemProduct
import com.example.meliapp.ui.payment.adapter.PaymentMethodAdapter
import com.example.meliapp.ui.sliderviewpager.adapter.ProductAdapter
import com.example.meliapp.ui.sliderviewpager.adapter.RecommendSliderAdapter
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.google.android.material.tabs.TabLayoutMediator
import kotlin.math.abs

class DialogPaymentFragment : BottomSheetDialogFragment(){

    private var _binding: MethodDialogBinding? = null
    private val binding get() = _binding!!
    private lateinit var adapter: PaymentMethodAdapter
    private lateinit var adapterRecommned: PaymentMethodAdapter

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
        binding.recyclerViewItems.layoutManager = LinearLayoutManager(context)
        adapter = PaymentMethodAdapter(mockAdapter(), context)
        binding.recyclerViewItems.adapter = adapter

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

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}