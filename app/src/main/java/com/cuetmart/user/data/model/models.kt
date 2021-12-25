package com.cuetmart.user.data.model

import com.google.firebase.auth.FirebaseUser
import com.google.firebase.firestore.ServerTimestamp
import java.util.*


data class Product(var name:String,
                   var description:String,
                   var type:String,
                   var quantity: Int,
                   var seller: CuetMartUser,
                   var imageLink: String?,
                   @ServerTimestamp
                   var Date:Date?,
                   var price:Int
                   )
data class Response(var msg:String,var isToast:Boolean,var title:String?,var isAvaileable:Boolean)

data class CuetMartUser(
                        var name: String?,
                        var email:String?,
                        var photourl:String?,
                        var uuid :String?

                        )