package com.cuetmart.user.ui.cart;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import com.cuetmart.user.R;
import com.cuetmart.user.data.model.Product;
import com.google.android.material.bottomnavigation.BottomNavigationView;


import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;



public class OrderFragment extends Fragment {

    View view;
    List<Product> allProducts = new ArrayList<>();
    Button confirmOrder;
    EditText name, phone, address;
    private static final String TAG = "OrderFragment";


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.fragment_order, container, false);
        initialize();
        try {
            allProducts = (ArrayList<Product>) getArguments().getSerializable("productlist");
            if (allProducts.size() == 0) {
                Toast.makeText(getContext(), "সমস্যা হয়েছে!", Toast.LENGTH_SHORT).show();
            } else {
                confirmOrder.setActivated(true);
                Toast.makeText(getContext(), allProducts.size() + " টি পন্য", Toast.LENGTH_SHORT).show();
            }

        } catch (Exception e) {

        }
        return view;

    }

    private void initialize() {

        confirmOrder = view.findViewById(R.id.orderconfirmbutton);
        confirmOrder.setActivated(false);
        name = view.findViewById(R.id.order_name);
        phone = view.findViewById(R.id.orderphoneNumber);
        address = view.findViewById(R.id.orderdeliveryaddress);
        confirmOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new AlertDialog.Builder(getActivity()).setTitle("Order Confirm!")
                        .setMessage("Your order is confirmed! Thank you for using CUETmart. ")
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                            }
                        })
                        .show();
            }
        });

     /*   confirmOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (name.getText().toString().equals("") || phone.getText().toString().equals("")
                        || address.getText().toString().equals("")) {
                    Toast.makeText(getActivity(), "Please Insert All Details!", Toast.LENGTH_SHORT).show();
                } else {
                    HashSet<String> recieverEmailList = new HashSet<>();
                    HashMap<String, String> myEmailandMsg = new HashMap<>();

                    for (Products prod : allProducts) {
                        recieverEmailList.add(prod.getShopkeeperEmail()+"@@@@");
                    }
                    List<String> emailList = new ArrayList<>(recieverEmailList);

                    for (int i = 0; i < emailList.size(); i++) {
                        String email = emailList.get(i);
                        String msg = "";

                        msg = "<h3>Hello Sir,</h3><br>You have an order in Bangla Recipe App. <br>Client Name:" + name.getText().toString() + "<br>Client Phone: " + phone.getText().toString() +
                                "<br>Client Address: " + address.getText().toString() + "<br> prodcts Details are given below!<br><br>";
                        myEmailandMsg.put(email, msg);


                        for (Products products : allProducts) {
                            if (email.contains(products.getShopkeeperEmail())) {
                                msg = myEmailandMsg.get(email);
                                myEmailandMsg.put(email, msg + "<br> <br> *** -> Product Name:" + products.getProductName() + "<br>Product Quantity: " + products.getQuantity() + "<br>" +
                                        "Product Price: " + products.getPrice());

                            }
                        }
                        msg = myEmailandMsg.get(email);
                        if (i==emailList.size()){
                            myEmailandMsg.put(email , msg + "<br><br>Thank you for your contribution on  Bangla Recipe App.<br>-Team Bangla Recipe");


                        }else{
                            myEmailandMsg.put(email , msg + "<br><br>Thank you for your contribution on  Bangla Recipe App.<br>-Team Bangla Recipe@@@@=");


                        }

                    }
                  sendEmail(myEmailandMsg, emailList);

                }

            }
        });*/

    }

   /* private void sendEmail(HashMap<String, String> myhasMap, List<String> emailList) {
        ApiInterface apiInterface = ApiClient.getMailClient().create(ApiInterface.class);
        HashMap<String, HashMap<String, String>> myHashMap = new HashMap<>();
        myHashMap.put("data", myhasMap);

        ProgressDialog myprogress=new ProgressDialog(getActivity());
        myprogress.setTitle("অপেক্ষা করুন...");
        myprogress.setMessage("অর্ডার কনফার্ম করা হচ্ছে...");
        myprogress.show();

        Call<ResponseBody> myEmail = apiInterface.sendMail(myHashMap);
        myEmail.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {

                myprogress.dismiss();
                try {
                    String msg=response.body().string();
                    Log.e(TAG, "onResponse: "+msg );
                    if (msg.contains("wrong")){
                        Toasty.error(getActivity(), " RESPONSE: " + msg, Toast.LENGTH_SHORT).show();
                    }else{
                        Toasty.success(getActivity(), " RESPONSE: " + msg, Toast.LENGTH_SHORT).show();
                        removeCartProducts();
                    }

                } catch (IOException e) {
                    e.printStackTrace();
                }


            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                myprogress.dismiss();

                Toasty.error(getActivity(), " RESPONSE: " + t.toString(), Toast.LENGTH_SHORT).show();
            }
        });


    }*/

    private void removeCartProducts() {
        SharedPreferences mPrefs = getActivity().getPreferences(Context.MODE_PRIVATE);
        SharedPreferences.Editor prefsEditor = mPrefs.edit();
        prefsEditor.clear();
        prefsEditor.apply();


    }

}