package com.suraj.emart.adapters;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.bumptech.glide.Glide;
import com.suraj.emart.R;
//import com.suraj.emart.activities.ProductDetailsActivity;
import com.suraj.emart.activities.ProductDetailsActivity;
import com.suraj.emart.databinding.ItemProductLayoutBinding;
import com.suraj.emart.models.Product;
import java.util.ArrayList;


public class ProductAdapter extends RecyclerView.Adapter<ProductAdapter.ProductViewHolder>
{

    ArrayList<Product> productArrayList;
    Context context;

    public ProductAdapter(ArrayList<Product> productArrayList, Context context)
    {
        this.productArrayList = productArrayList;
        this.context = context;
    }

    @NonNull
    @Override
    public ProductViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType)
    {
        return new ProductViewHolder(LayoutInflater.from(context).inflate(R.layout.item_product_layout, parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull ProductViewHolder holder, int position) {

        Product product = productArrayList.get(position);
        holder.itemProductLayoutBinding.productName.setText(product.getName());
        holder.itemProductLayoutBinding.productPrice.setText(String.format("INR %s", product.getPrice()));


        Glide.with(context).load(product.getImage()).into(holder.itemProductLayoutBinding.productImage);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               Intent productDetInt = new Intent(context, ProductDetailsActivity.class);
               productDetInt.putExtra("Product Name",product.getName());
               productDetInt.putExtra("Product Image", product.getImage());
               productDetInt.putExtra("Product Price", product.getPrice());
               productDetInt.putExtra("Product Status", product.getStatus());
               productDetInt.putExtra("Product Stock", product.getStock());
               productDetInt.putExtra("Product Desc", product.getProductDesc());
               context.startActivity(productDetInt);
            }
        });
    }

    @Override
    public int getItemCount() {
        return productArrayList.size();
    }

    public static class ProductViewHolder extends RecyclerView.ViewHolder
    {
        ItemProductLayoutBinding itemProductLayoutBinding;
        public ProductViewHolder(@NonNull View itemView)
        {
            super(itemView);
            itemProductLayoutBinding = ItemProductLayoutBinding.bind(itemView);
        }
    }
}
