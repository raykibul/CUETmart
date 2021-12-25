package com.cuetmart.user.ui.cart;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.cuetmart.user.R;
import com.cuetmart.user.data.model.CartInterface;
import com.cuetmart.user.data.model.Product;

import java.util.List;

public class CartAdapter extends RecyclerView.Adapter<CartAdapter.viewholder>{

    List<Product> allProducts;
    Context context;
    CartInterface cartInterface;

    public CartAdapter(List<Product> allProducts,Context context,CartInterface cartInterface) {
        this.allProducts = allProducts;
        this.context=context;
        this.cartInterface=cartInterface;
    }

    @NonNull

    @Override
    public viewholder onCreateViewHolder(@NonNull   ViewGroup parent, int viewType) {
           View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.cartitem,parent,false);
           return new viewholder(view);

    }

    @Override
    public void onBindViewHolder(@NonNull   viewholder holder, int position) {
          Product temp = allProducts.get(position);
          if (temp!=null){
              if (temp.getImageLink()!=null)
                  Glide.with(context).load(temp.getImageLink()).centerCrop().into(holder.productImage);
              holder.productName.setText(temp.getName());
              holder.productDetails.setText("Price: "+temp.getPrice()+"\nQuantity: "+temp.getQuantity());
              holder.removeButton.setOnClickListener(new View.OnClickListener() {
                 @Override
                 public void onClick(View v) {
                     new AlertDialog.Builder(context)
                             .setTitle("ওয়ার্নিং")
                             .setMessage("আপনি কি এই প্রোডাক্ট টি মুছে ফেলতে চাচ্ছেন?")
                             .setPositiveButton("হ্যা", new DialogInterface.OnClickListener() {
                                 @Override
                                 public void onClick(DialogInterface dialog, int which) {
                                    cartInterface.onProductRemove(temp,allProducts);
                                 }
                             })
                             .setNegativeButton("না", new DialogInterface.OnClickListener() {
                                 @Override
                                 public void onClick(DialogInterface dialog, int which) {
                                     dialog.dismiss();
                                 }
                             })
                             .show();
                 }
             });

          }

    }




    @Override
    public int getItemCount() {
       if (allProducts==null){
           return  0;
       }else
           return allProducts.size();
    }

    public class  viewholder extends RecyclerView.ViewHolder{

        ImageView productImage;
        TextView productName,productDetails;
        Button removeButton;


        public viewholder(@NonNull   View itemView) {
            super(itemView);

            productDetails=itemView.findViewById(R.id.cart_producctdetails);
            productName=itemView.findViewById(R.id.cart_productname);
            removeButton=itemView.findViewById(R.id.cart_removeProducts);
            productImage=itemView.findViewById(R.id.imageview_cart);
        }
    }

}
