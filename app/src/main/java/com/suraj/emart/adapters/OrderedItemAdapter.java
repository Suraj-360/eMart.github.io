package com.suraj.emart.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.bumptech.glide.Glide;
import com.suraj.emart.R;
import com.suraj.emart.databinding.ItemProductLayoutTwoBinding;
import com.suraj.emart.models.Product;
import java.util.ArrayList;

public class OrderedItemAdapter extends RecyclerView.Adapter<OrderedItemAdapter.ProductViewHolder>
{

    ArrayList<Product> productArrayList;
    Context context;
    double price;

    public OrderedItemAdapter(ArrayList<Product> productArrayList, double price, Context context)
    {
        this.productArrayList = productArrayList;
        this.context = context;
        this.price = price;
    }

    @NonNull
    @Override
    public ProductViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType)
    {
        return new ProductViewHolder(LayoutInflater.from(context).inflate(R.layout.item_product_layout_two, parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull ProductViewHolder holder, int position) {

        Product product = productArrayList.get(position);
        holder.itemProductLayoutTwoBinding.productName.setText(product.getName());
        holder.itemProductLayoutTwoBinding.productPrice.setText(String.format("INR %s", product.getPrice()));
        holder.itemProductLayoutTwoBinding.productQuantity.setText(String.format("Quantity %s", product.getQuantity()));
        Glide.with(context).load(product.getImage()).into(holder.itemProductLayoutTwoBinding.productImage);
    }

    @Override
    public int getItemCount() {
        return productArrayList.size();
    }

    public static class ProductViewHolder extends RecyclerView.ViewHolder
    {
        ItemProductLayoutTwoBinding itemProductLayoutTwoBinding;
        public ProductViewHolder(@NonNull View itemView)
        {
            super(itemView);
            itemProductLayoutTwoBinding = ItemProductLayoutTwoBinding.bind(itemView);
        }
    }
}
