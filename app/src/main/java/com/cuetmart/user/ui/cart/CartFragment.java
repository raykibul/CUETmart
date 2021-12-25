package com.cuetmart.user.ui.cart;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.cuetmart.user.R;
import com.cuetmart.user.data.model.CartInterface;
import com.cuetmart.user.data.model.Product;
import com.google.gson.Gson;


import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;




public class CartFragment extends Fragment implements CartInterface {

    RecyclerView recyclerView;
    CartAdapter adapter;
    Button orderNowButton;

    List<Product> productsArrayList =new ArrayList<>();


    View view;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        view= inflater.inflate(R.layout.fragment_cart, container, false);
        initialze();
        LoadAllCartProducts();

        return view;

    }

    private void LoadAllCartProducts() {
        productsArrayList.clear();

        Gson gson = new Gson();
        SharedPreferences mPrefs = getActivity().getPreferences(Context.MODE_PRIVATE);
        int size = mPrefs.getInt("cartlistsize", 0);

        if (size == 0){
            Toast.makeText (getActivity(), "No Products Found!", Toast.LENGTH_SHORT).show();
            return;
        }


        for (int i=0;i<size;i++){
            String json = mPrefs.getString("cartlistobject"+i, "");
            Product obj = gson.fromJson(json, Product.class);
            productsArrayList.add(obj);

        }
        if (productsArrayList.size()>0){
            adapter=new  CartAdapter(productsArrayList,getActivity(),CartFragment.this);
            recyclerView.setAdapter(adapter);

        }else{
            Toast.makeText(getActivity(), "No Products Found!!", Toast.LENGTH_SHORT).show();
        }
    }

    private void initialze() {
        recyclerView=view.findViewById(R.id.cartrecyclerveiw);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        orderNowButton= view.findViewById(R.id.button2);
        orderNowButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                OrderNow();
            }
        });
    }

    private  void OrderNow(){
        if (productsArrayList.size()<=0){
            Toast.makeText(getActivity(),"আপনার কার্টে প্রোডাক্ট নেই!",Toast.LENGTH_SHORT).show();
            return;
        }
     Bundle mybundle=new Bundle();
     mybundle.putSerializable("productlist", (Serializable) productsArrayList);

 }



    @Override
    public void onProductRemove(Product products, List<Product> allproducts) {
        allproducts.remove(products);
        Gson gson = new Gson();
        SharedPreferences mPrefs = getActivity().getPreferences(Context.MODE_PRIVATE);
        int size = mPrefs.getInt("cartlistsize", 0);
        SharedPreferences.Editor prefsEditor = mPrefs.edit();
        prefsEditor.putInt("cartlistsize",allproducts.size());

        if (allproducts.size()>0){
            for(int i=0;i<allproducts.size();i++){
                Product prod= allproducts.get(i);
                String json= gson.toJson(prod);
                prefsEditor.putString("cartlistobject"+i,json);

            }
            prefsEditor.apply();
        }
        productsArrayList.remove(products);
        adapter.notifyDataSetChanged();
        Toast.makeText(getActivity(),"Product Removed!",Toast.LENGTH_SHORT).show();
    }
}