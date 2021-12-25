package com.cuetmart.user.ui.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.cuetmart.user.data.model.Product
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.google.firebase.firestore.QueryDocumentSnapshot

import com.google.firebase.firestore.QuerySnapshot

import androidx.annotation.NonNull
import com.cuetmart.user.data.model.Response

import com.google.android.gms.tasks.OnCompleteListener

import com.google.firebase.firestore.CollectionReference




class HomeViewModel : ViewModel() {

    var listofProduct= MutableLiveData<List<Product>>()
    var db = Firebase.firestore
    var response = MutableLiveData<Response>()


    fun loadallProducts(){

        val reference: CollectionReference = db.collection("products")
        val templist = ArrayList<Product>()

        reference.get().addOnCompleteListener { task ->
            if (task.isSuccessful) {

                for (documentSnapshot in task.result) {
                    val temp: Product = documentSnapshot.toObject(Product::class.java)
                    templist.add(temp)

                }
                listofProduct.postValue(templist)
            } else {
                response.postValue(Response("Sorry something went wrong!",false,"Server Error: ",true))
            }
        }

    }

}