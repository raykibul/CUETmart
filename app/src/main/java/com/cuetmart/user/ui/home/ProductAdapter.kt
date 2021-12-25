package com.cuetmart.user.ui.home


import android.content.Context
import android.graphics.drawable.Drawable
import android.util.Log

import android.widget.TextView

import androidx.recyclerview.widget.RecyclerView

import android.view.animation.Animation

import com.bumptech.glide.Glide

import android.view.LayoutInflater
import android.view.View

import android.view.ViewGroup
import android.widget.ImageView
import com.bumptech.glide.GlideExperiments
import com.bumptech.glide.request.target.Target
import com.cuetmart.user.R
import com.cuetmart.user.data.`interface`.ProductInterface
import com.cuetmart.user.data.model.Product


class ProductAdapter(
    recipeCategories: List<Product>?,
    context: Context,
    recipeClickInterface: ProductInterface
) :
    RecyclerView.Adapter<ProductAdapter.viewHolder>() {
    var productList: List<Product>?
    var context: Context
    var productInterface: ProductInterface
    private   val TAG = "ProductAdapter"

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): viewHolder {
        val view: View = LayoutInflater.from(parent.context).inflate(R.layout.product_item, parent, false)
        return viewHolder(view, productInterface)
    }

    override fun onBindViewHolder(holder: viewHolder, position: Int) {
        val current: Product = productList!![position]
        holder.productName.text= current.name
        holder.productPrize.text= current.price.toString()+ "Taka"
        Log.e(TAG, "onBindViewHolder: "+current.imageLink)
        Glide.with(context).load(current.imageLink).centerCrop().placeholder(R.drawable.ic_card)
            .into(holder.productImage);

    }

    override fun getItemCount(): Int {
        return if (productList == null || productList!!.size == 0) 0 else productList!!.size
    }

    inner class viewHolder(itemView: View, productInterface1: ProductInterface) :
        RecyclerView.ViewHolder(itemView), View.OnClickListener {
        var productImage :ImageView
        var productPrize: TextView
        var productName: TextView
        var recipeClickInterface: ProductInterface

        override fun onClick(v: View?) {
            recipeClickInterface.onProductClick(
                productList!![adapterPosition]
            )
        }

        init {

            productImage = itemView.findViewById(R.id.imageview_category)
            productName = itemView.findViewById(R.id.reciepeCategoryTextview)
            productPrize = itemView.findViewById(R.id.recipeCounterText)
            this.recipeClickInterface = productInterface1
            itemView.setOnClickListener(this)
        }
    }

    init {
        this.productList = recipeCategories
        this.context = context
        this.productInterface = recipeClickInterface
    }
}