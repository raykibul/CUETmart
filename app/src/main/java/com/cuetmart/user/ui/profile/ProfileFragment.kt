package com.cuetmart.user.ui.profile

import android.app.AlertDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.cuetmart.user.R
import com.cuetmart.user.databinding.FragmentProfileBinding

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthUserCollisionException
import com.google.firebase.auth.FirebaseUser

class ProfileFragment : Fragment() {

    private lateinit var profileViewModel: ProfileViewModel
    private var _binding: FragmentProfileBinding? = null
    private val binding get() = _binding!!

    val currentUser : FirebaseUser? = FirebaseAuth.getInstance().currentUser

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        profileViewModel = ViewModelProvider(this).get(ProfileViewModel::class.java)

        _binding =  FragmentProfileBinding.inflate(inflater, container, false)
        val root: View = binding.root
        updateUI()

        return root
    }





    fun updateUI(){
        if(currentUser==null){
            val action = ProfileFragmentDirections.actionProfileFragmentToLoginFragment()
            findNavController().navigate(action)
        }else{
            Glide.with(this).load(currentUser.photoUrl).placeholder(R.drawable.ic_profile_24)
                .into(binding.profileImage);
            binding.textView3.setText(currentUser.displayName)
            binding.emailtext.setText(currentUser.email)
            setlistener()
        }
    }

    private fun setlistener() {
         binding.signoutCard.setOnClickListener {
             AlertDialog.Builder(requireContext()).setTitle("Warning!")
                 .setMessage("Do you really want to sign-out ? ")
                 .setPositiveButton("Yes"){dialog,_ ->
                     FirebaseAuth.getInstance().signOut()
                     updateUI()
                 }
                 .setNegativeButton("No"){dialog,_->
                     dialog.dismiss()
                 }
                 .show()
         }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}