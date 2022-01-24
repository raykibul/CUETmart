package com.cuetmart.user.ui.sell

import com.cuetmart.user.sellUtil
import com.google.common.truth.Truth.assertThat

import org.junit.Test

class sellUtilTest{
    @Test
    fun `empty_product_details_return_false`(){
      val result = sellUtil.validateProduct("Product 1",
       "Used",1,"this product is very good quality")
        assertThat(result).isTrue()
    }

    @Test
    fun `quantity_is_0_or_not_valid`(){
      val result= sellUtil.validedQuantity(500)
        assertThat(result).isTrue()
    }
}