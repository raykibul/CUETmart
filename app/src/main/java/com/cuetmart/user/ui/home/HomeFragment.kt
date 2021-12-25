package com.cuetmart.user.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.cuetmart.user.R
import com.cuetmart.user.data.`interface`.ProductInterface
import com.cuetmart.user.data.model.Controller
import com.cuetmart.user.data.model.Product
import com.cuetmart.user.databinding.FragmentHomeBinding

class HomeFragment : Fragment(),ProductInterface {

    private lateinit var homeViewModel: HomeViewModel
    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!
    private  var allproducts = ArrayList<Product>()
    private  var adapter:ProductAdapter ?= null
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        homeViewModel =
            ViewModelProvider(this).get(HomeViewModel::class.java)

        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        val root: View = binding.root
        binding.homerecycler.setHasFixedSize(true)



        binding.homerecycler.layoutManager= GridLayoutManager(requireActivity(),2)

        homeViewModel.loadallProducts()



        //observing the list of product from viewmodel
        homeViewModel.listofProduct.observe(viewLifecycleOwner, Observer {
            if (it!=null){
                allproducts.addAll(it)
                adapter= ProductAdapter(it,requireActivity(),this)
                binding.homerecycler.adapter= adapter

            }
        })

        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onProductClick(product: Product) {
        Controller.currentProduct=product
        var action = HomeFragmentDirections.actionNavHomeToDetailsFragment()
        findNavController().navigate(action)
    }
}