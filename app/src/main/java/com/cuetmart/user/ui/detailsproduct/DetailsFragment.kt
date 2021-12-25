package com.cuetmart.user.ui.detailsproduct

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.cuetmart.user.R
import com.cuetmart.user.data.model.Controller
import com.cuetmart.user.databinding.FragmentDetailsBinding
import com.google.gson.Gson


class DetailsFragment : Fragment() {

    private lateinit var detailsViewModel: DetailsViewModel
    private var _binding: FragmentDetailsBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        detailsViewModel =
            ViewModelProvider(this).get(DetailsViewModel::class.java)

        _binding = FragmentDetailsBinding.inflate(inflater, container, false)
        val root: View = binding.root
        var product = Controller.currentProduct

        Glide.with(requireActivity()).load(product?.imageLink).fitCenter().placeholder(R.drawable.ic_card)
            .into(binding.productImageview);

        binding.productPriceDetails.text = "${product?.price} TAKA"
        binding.productDetails.text = "${product?.description}\nStock Available: ${product?.quantity} \n\n\n Seller Details: \n\nName: ${product?.seller?.name}\nEmail: ${product?.seller?.email}"
        binding.productNameText.text= product?.name+"- ${product?.type}"

        binding.button.setOnClickListener {
            val mPrefs = requireActivity().getPreferences(Context.MODE_PRIVATE)
            val gson = Gson()
            val json: String = gson.toJson(product)
            val prefsEditor = mPrefs.edit()
            val size = mPrefs.getInt("cartlistsize", 0)
            prefsEditor.putString("cartlistobject$size", json)
            prefsEditor.putInt("cartlistsize", size + 1)
            prefsEditor.apply()
            Toast.makeText(activity, "Product added to your cart!", Toast.LENGTH_SHORT).show()
        }
        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}