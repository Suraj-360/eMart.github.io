package com.suraj.emart.adapters;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.content.res.AppCompatResources;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.hishd.tinycart.model.Cart;
import com.hishd.tinycart.util.TinyCartHelper;
import com.suraj.emart.R;
import com.suraj.emart.activities.CartActivity;
import com.suraj.emart.databinding.ItemCartBinding;
import com.suraj.emart.databinding.QuantityDialogBinding;
import com.suraj.emart.models.Product;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Objects;

public class CardAdapter extends RecyclerView.Adapter<CardAdapter.CardViewHolder> {

    ArrayList<Product> products;
    Context context;
    Cart cart;
    int qt;
    CardAdapter cardAdapter;
    CartListener cartListener;
    DeleteItem deleteItem;

    public interface CartListener{
        public void onQuantityChanged();
    }

    public CardAdapter(ArrayList<Product> products, Context context, CartListener cartListener) {
        this.products = products;
        this.context = context;
        this.cartListener = cartListener;
        cart = TinyCartHelper.getCart();
    }

    public CardAdapter(ArrayList<Product> products, Context context, CartListener cartListener,DeleteItem deleteItem) {
        this.products = products;
        this.context = context;
        this.cartListener = cartListener;
        this.deleteItem = deleteItem;
        cart = TinyCartHelper.getCart();
    }

    @NonNull
    @Override
    public CardViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new CardViewHolder(LayoutInflater.from(context).inflate(R.layout.item_cart,parent,false));
    }

    @SuppressLint("DefaultLocale")
    @Override
    public void onBindViewHolder(@NonNull CardViewHolder holder, int position)
    {

        Product product = products.get(position);
        Glide.with(context).load(product.getImage()).into(holder.itemCartBinding.imageProductCart);
        holder.itemCartBinding.nameProductCart.setText(product.getName());
        Log.e("Product name",product.getName());
        holder.itemCartBinding.priceProductCart.setText(String.format("INR %s", product.getPrice().toString()));
        holder.itemCartBinding.totalProductCart.setText(String.format("%d Item's", product.getQuantity()));

        holder.itemView.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                QuantityDialogBinding quantityDialogBinding = QuantityDialogBinding.inflate(LayoutInflater.from(context));
                AlertDialog alertDialog = new AlertDialog.Builder(context).setView(quantityDialogBinding.getRoot()).create();
                alertDialog.getWindow().setBackgroundDrawable(AppCompatResources.getDrawable(context, android.R.color.transparent));
                quantityDialogBinding.productNameDialog.setText(product.getName());
                quantityDialogBinding.quantityDisplay.setText(String.valueOf(product.getQuantity()));
                qt = product.getQuantity();
                quantityDialogBinding.incrementBtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v)
                    {
                        if(qt<product.getStock())
                        {
                            qt = qt + 1;
                            product.setQuantity(qt);
                            quantityDialogBinding.quantityDisplay.setText(String.valueOf(product.getQuantity()));
                        }
                    }
                });

                quantityDialogBinding.decrementBtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if(qt>2)
                        {
                            qt = qt -1;
                            product.setQuantity(qt);
                            quantityDialogBinding.quantityDisplay.setText(String.valueOf(product.getQuantity()));
                        }
                    }
                });

                quantityDialogBinding.productStockDialog.setText(String.format("Stock : %d", product.getStock()));

                quantityDialogBinding.saveQuantity.setOnClickListener(new View.OnClickListener() {
                    @SuppressLint("NotifyDataSetChanged")
                    @Override
                    public void onClick(View v) {
                        alertDialog.dismiss();
                        notifyDataSetChanged();
                        cart.updateItem(product, product.getQuantity());
                        cartListener.onQuantityChanged();
                    }
                });
                alertDialog.show();
            }
        });

        holder.itemCartBinding.productRemove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.e("posi", String.valueOf(holder.getAbsoluteAdapterPosition()));
                int posit = holder.getAbsoluteAdapterPosition();
                deleteItem.deleteItem(posit);
                notifyItemRemoved(posit);
            }
        });
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

    public static interface DeleteItem {
        void deleteItem(int position);
    }

}
