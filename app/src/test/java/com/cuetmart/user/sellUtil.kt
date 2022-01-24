package com.cuetmart.user

import java.io.File

object sellUtil {

    fun validateProduct(
        name:String,
        type:String,
        quantity:Int,
        description:String

    ):Boolean{
        return !(name.equals("")||type.equals("")||quantity.equals("")||description.equals(""))
    }

    fun validedQuantity(quantity: Int):Boolean{
        return !(quantity<0&& quantity>1000)
    }
    fun imageNullTesting(file : File):Boolean{
        return !(file==null)
    }

}