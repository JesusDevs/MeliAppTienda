package com.example.meliapp.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.example.meliapp.R
import com.example.meliapp.databinding.FragmentFirstBinding

/**
 * A simple [Fragment] subclass as the default destination in the navigation.
 */
class FirstFragment : Fragment() {

    private var _binding: FragmentFirstBinding? = null
    private val binding get() = _binding!!
    private lateinit var adapter: ProductAdapter


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentFirstBinding.inflate(inflater, container, false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        //instanciar adapter
        binding.recyclerViewItems.layoutManager = StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL)
        adapter = ProductAdapter(mockAdapter(), context)
        binding.recyclerViewItems.adapter = adapter

           //navigation
          // findNavController().navigate(R.id.action_FirstFragment_to_SecondFragment)

    }
      fun mockAdapter(): MutableList<ItemProduct> {
         var  postItems: MutableList<ItemProduct> = mutableListOf()
          postItems.add(ItemProduct("1", "Soda Estereo, ",13000,"https://www.spirit-of-rock.com/les%20goupes/S/Soda%20Stereo/pics/490551_logo.jpg","nuevo"))
          postItems.add(ItemProduct("2", "Kuervos del sur ",15500,"https://pbs.twimg.com/media/EISqOeyXsAAUgx7.jpg","nuevo"))
          postItems.add(ItemProduct("3", "Kuervos del sur ",12200,"https://vinilogarage.cl/wp-content/uploads/2021/05/190452520_10157802063250940_886593049488731790_n.jpg","nuevo"))
          postItems.add(ItemProduct("4", "Muse ",11000,"https://www.lahiguera.net/musicalia/artistas/muse/disco/9294/muse_simulation_theory-portada.jpg","nuevo"))
          postItems.add(ItemProduct("7", "Pink Floyd ",19000,"https://e00-marca.uecdn.es/assets/multimedia/imagenes/2019/12/04/15754721234271.jpg","nuevo"))
          postItems.add(ItemProduct("6", "Iron Maiden ",17000,"https://imagizer.imageshack.com/v2/629x887q90/924/LBWKky.jpg","nuevo"))
          postItems.add(ItemProduct("5", "Second ",10000,"https://www.lahiguera.net/musicalia/artistas/second/disco/5239/second_montana_rusa-portada.jpg","nuevo"))

          return postItems
      }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}