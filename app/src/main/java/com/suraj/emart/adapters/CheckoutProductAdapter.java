package com.suraj.emart.adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.bumptech.glide.Glide;
import com.hishd.tinycart.model.Cart;
import com.hishd.tinycart.util.TinyCartHelper;
import com.suraj.emart.R;
import com.suraj.emart.databinding.ItemCartBinding;
import com.suraj.emart.models.Product;
import java.util.ArrayList;

public class CheckoutProductAdapter extends RecyclerView.Adapter<CheckoutProductAdapter.CardViewHolder>  {

    ArrayList<Product> products;
    Context context;
    Cart cart;
    int qt;
    CardAdapter.CartListener cartListener;

    public interface CartListener{
        public void onQuantityChanged();
    }

    public CheckoutProductAdapter(ArrayList<Product> products, Context context) {
        this.products = products;
        this.context = context;
        cart = TinyCartHelper.getCart();
    }

    @NonNull
    @Override
    public CheckoutProductAdapter.CardViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType)
    {
        return new CheckoutProductAdapter.CardViewHolder(LayoutInflater.from(context).inflate(R.layout.item_cart,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull CardViewHolder holder, int position) {
        Product product = products.get(position);
        Glide.with(context).load(product.getImage()).into(holder.itemCartBinding.imageProductCart);
        holder.itemCartBinding.nameProductCart.setText(product.getName());
        Log.e("Product name",product.getName());
        holder.itemCartBinding.priceProductCart.setText(String.format("INR %s", product.getPrice().toString()));
        holder.itemCartBinding.totalProductCart.setText(String.format("%d Item's", product.getQuantity()));

    }
    @Override
    public int getItemCount() {
        return products.size();
    }

    public static class CardViewHolder extends RecyclerView.ViewHolder{
        ItemCartBinding itemCartBinding;
        public CardViewHolder(@NonNull View itemView) {
            super(itemView);
            itemCartBinding = ItemCartBinding.bind(itemView);
        }
    }
}

