package com.example.meliapp.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
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
        binding.recyclerViewItems.layoutManager = LinearLayoutManager(context)
        adapter = ProductAdapter(mockAdapter(), context)
        binding.recyclerViewItems.adapter = adapter
        binding.buttonFirst.setOnClickListener {
            findNavController().navigate(R.id.action_FirstFragment_to_SecondFragment)
        }
    }
      fun mockAdapter(): MutableList<ItemProduct> {
         var  postItems: MutableList<ItemProduct> = mutableListOf()
        postItems.add(ItemProduct("1", "Kuervos, ",1000,"https://www.spirit-of-rock.com/les%20goupes/S/Soda%20Stereo/pics/490551_logo.jpg","nuevo"))
          postItems.add(ItemProduct("1", "Kuervos, ",1000,"https://www.spirit-of-rock.com/les%20goupes/S/Soda%20Stereo/pics/490551_logo.jpg","nuevo"))
       return postItems
      }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}