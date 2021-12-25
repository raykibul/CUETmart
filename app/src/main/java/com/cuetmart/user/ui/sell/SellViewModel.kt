package com.cuetmart.user.ui.sell

import android.net.Uri
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.cuetmart.user.data.model.Product
import com.cuetmart.user.data.model.Response
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.ktx.storage
import java.io.File

class SellViewModel : ViewModel() {
      val storage = Firebase.storage("gs://cuet-mart.appspot.com")
      var response= MutableLiveData<Response>()

      var storageRef = storage.getReference().child("products");
      val db = Firebase.firestore

      fun setresponsefalse(){
            response.postValue(Response("sd",false,null,false))
      }

      fun uploadImagethenAddProduct(  product: Product, imagefile:File){
            var  finalref = storageRef.child(getRandomString(16))
            var uploadTask= finalref.putFile(Uri.fromFile(imagefile))
            val urlTask = uploadTask.continueWithTask { task ->
                  if (!task.isSuccessful) {
                        task.exception?.let {
                              response.postValue(Response(it.message.toString(),false,"Error",true))
                              throw it
                        }
                  }
                  finalref.downloadUrl

            }.addOnCompleteListener { task ->
                  if (task.isSuccessful) {
                        val downloadUri = task.result
                        product.imageLink=downloadUri.toString()
                        addProduct(product)
                  } else {
                        response.postValue(Response("Something went wrong!",false,"Image Upload Failed",true))
                  }
            }
      }

      fun addProduct(product :Product){

            db.collection("products")
                  .add(product)
                  .addOnSuccessListener { documentReference ->
                        response.postValue(Response("SUCCESS: Your product added to CUETmart. Thank You",false,"Server Response!",true))
                  }
                  .addOnFailureListener { e ->
                        response.postValue(Response("Failed: "+e.message.toString(),false,"Server Response!",true))

                  }

      }


      fun getRandomString(length: Int) : String {
            val allowedChars = ('A'..'Z') + ('a'..'z') + ('0'..'9')
            return (1..length)
                  .map { allowedChars.random() }
                  .joinToString("")
      }

}