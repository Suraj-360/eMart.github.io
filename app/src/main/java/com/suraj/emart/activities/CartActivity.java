package com.suraj.emart.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.content.res.AppCompatResources;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.hishd.tinycart.model.Cart;
import com.hishd.tinycart.model.Item;
import com.hishd.tinycart.util.TinyCartHelper;
import com.suraj.emart.R;
import com.suraj.emart.adapters.CardAdapter;
import com.suraj.emart.databinding.ActivityCartBinding;
import com.suraj.emart.models.Product;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Map;
import java.util.Objects;

public class CartActivity extends AppCompatActivity implements CardAdapter.DeleteItem
{
    ActivityCartBinding activityCartBinding;
    CardAdapter cardAdapter;
    ArrayList<Product> productArrayList;
    Cart cart;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        activityCartBinding = ActivityCartBinding.inflate(getLayoutInflater());
        setContentView(activityCartBinding.getRoot());

        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setBackgroundDrawable(AppCompatResources.getDrawable(this,R.color.blue));

        cart = TinyCartHelper.getCart();

        productArrayList = new ArrayList<>();
        cardAdapter = new CardAdapter(productArrayList, this, new CardAdapter.CartListener() {
            @Override
            public void onQuantityChanged() {
                Log.e("Quantity", productArrayList.get(0).getQuantity()+"");
                activityCartBinding.totalPrices.setText(String.format("INR %s", cart.getTotalPrice()));
            }
        }, this);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(this,linearLayoutManager.getOrientation());
        dividerItemDecoration.setDrawable(Objects.requireNonNull(AppCompatResources.getDrawable(this,R.drawable.irem_divider)));
        activityCartBinding.recyclerViewCart.setLayoutManager(linearLayoutManager);
        activityCartBinding.recyclerViewCart.addItemDecoration(dividerItemDecoration);
        activityCartBinding.recyclerViewCart.setAdapter(cardAdapter);

        activityCartBinding.totalPrices.setText(String.format("INR %s", cart.getTotalPrice()));

        updateData();

        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);

        activityCartBinding.continueBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent orderIntent = new Intent(CartActivity.this,CheckoutActivity.class);
                startActivity(orderIntent);
            }
        });
    }

    @Override
    public boolean onSupportNavigateUp()
    {
        finish();
        return super.onSupportNavigateUp();
    }

    @Override
    public void deleteItem(int position) {
        Log.e("Del",position+"");
        Item item = (Item) productArrayList.get(position);
        productArrayList.remove(position);
        cart.removeItem(item);
        cardAdapter.notifyItemRemoved(position);
        activityCartBinding.totalPrices.setText(String.format("INR %s", cart.getTotalPrice()));
    }

    public  void updateData()
    {
        for(Map.Entry<Item, Integer> item : cart.getAllItemsWithQty().entrySet())
        {
            Product product = (Product) item.getKey();
            int quantity = item.getValue();
            product.setQuantity(quantity);
            Log.e("Product added",product.getName());
            productArrayList.add(product);
        }
    }
}