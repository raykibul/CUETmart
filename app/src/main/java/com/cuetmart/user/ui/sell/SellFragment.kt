package com.cuetmart.user.ui.sell

import android.app.AlertDialog
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider

import com.cuetmart.user.data.model.Product
import com.cuetmart.user.databinding.FragmentSellBinding
import com.cuetmart.user.databinding.FragmentSlideshowBinding
import java.io.File


import android.content.Intent
import android.widget.*
import androidx.annotation.Nullable
import com.canhub.cropper.CropImage
import com.canhub.cropper.CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE
import com.canhub.cropper.CropImage.CROP_IMAGE_ACTIVITY_RESULT_ERROR_CODE
import com.canhub.cropper.CropImage.getActivityResult
import com.cuetmart.user.R
import com.cuetmart.user.data.model.CuetMartUser
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase


class SellFragment : Fragment(), CompoundButton.OnCheckedChangeListener {

    private lateinit var sellViewModel: SellViewModel
    private var _binding: FragmentSellBinding? = null
    private val binding get() = _binding!!
    var currentUser = Firebase.auth.currentUser

    private var imageUri:Uri? = null
    private  var imageFile: File? = null
    private  var type:String = "Used"
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        sellViewModel = ViewModelProvider(this).get(SellViewModel::class.java)
        _binding = FragmentSellBinding.inflate(inflater, container, false)
        val root: View = binding.root
        sellViewModel.response.observe(viewLifecycleOwner, Observer {
            if(it!=null&&it.isAvaileable){
                binding.progressbar.visibility=View.GONE
                if (it.isToast){
                  Toast.makeText(requireActivity(),it.msg,Toast.LENGTH_SHORT).show()
                }else{
                    AlertDialog.Builder(requireActivity()).setTitle(it.title).setMessage(it.msg).show()
                }
                sellViewModel.setresponsefalse()
            }
        })
        binding.newRadioButton.setOnCheckedChangeListener(this)
        binding.usedRadiobutton.setOnCheckedChangeListener(this)
        binding.productImageVIew.setOnClickListener {

            CropImage.activity()
                .start(requireContext(),this);
        }


        binding.uploadProductButton.setOnClickListener {
          if (binding.productName.text.toString()==""||binding.productQuantity.text.toString()==""||binding.description.text.toString()==""
              ||binding.productPrice.text.toString()==""){
              Toast.makeText(requireActivity(),"Please Insert Every Input!",Toast.LENGTH_SHORT).show()
          }else if(imageUri==null){
              Toast.makeText(requireActivity(),"Please Select an image for your product!",Toast.LENGTH_SHORT).show()
          }else{
              binding.progressbar.visibility=View.VISIBLE
              Toast.makeText(requireActivity(),"Uploading your product ...",Toast.LENGTH_SHORT).show()
              var cuetmartuser:CuetMartUser= CuetMartUser(currentUser?.displayName,currentUser?.email!!,currentUser?.photoUrl.toString(),currentUser?.uid)
              var product = Product(binding.productName.text.toString(),binding.description.text.toString(),
                            type,binding.productQuantity.text.toString().toInt(),cuetmartuser,null,null,binding.productPrice.text.toString().toInt())
              sellViewModel.uploadImagethenAddProduct(product,imageFile!!)
          }
        }


        return root
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, @Nullable data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == CROP_IMAGE_ACTIVITY_REQUEST_CODE) {
            val result= getActivityResult(data)

            if (resultCode == -1) {
                val resultUri: Uri? = result?.uriContent
                  binding.productImageVIew.setImageURI(resultUri)
                  imageUri = resultUri
                  imageFile = File(result?.getUriFilePath(requireActivity(), false))

            } else if (resultCode == CROP_IMAGE_ACTIVITY_RESULT_ERROR_CODE) {
                Toast.makeText(activity, "Image crop error!", Toast.LENGTH_SHORT).show()
            }
        }
    }

    override fun onCheckedChanged(buttonView: CompoundButton?, isChecked: Boolean) {
            var checked = buttonView?.isChecked

        when (buttonView?.getId()) {
            R.id.new_radioButton ->
                if (checked!!) {
                    type="New"
                }
            R.id.used_radiobutton ->
                if (checked!!) {
                    type="Used"
                }
        }
    }

}