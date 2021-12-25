package com.cuetmart.user.data.model

import com.google.firebase.auth.FirebaseUser
import com.google.firebase.firestore.ServerTimestamp
import kotlinx.serialization.Contextual
import java.util.*
import kotlinx.serialization.Serializable


data class Product(var name:String="",
                   var description:String="",
                   var type:String="",
                   var quantity: Int=1,
                   var seller: CuetMartUser?=null,
                   var imageLink: String?="",

                   @ServerTimestamp var date :Date?=null,
                   var price:Int=1
                   )
{
   public fun Product() {}
}


data class Response(var msg:String,var isToast:Boolean,var title:String?,var isAvaileable:Boolean)

data class CuetMartUser(
                        var name: String?="",
                        var email:String?="",
                        var photourl:String?="",
                        var uuid :String?=""

                        ){
    fun CuetMartUser() {}
}