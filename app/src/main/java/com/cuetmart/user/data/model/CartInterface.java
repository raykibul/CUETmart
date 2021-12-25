package com.cuetmart.user.data.model;



import java.util.List;

public interface CartInterface {
    void onProductRemove(Product products, List<Product>allproducts);
}
