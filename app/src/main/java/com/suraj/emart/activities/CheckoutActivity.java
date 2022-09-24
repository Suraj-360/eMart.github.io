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
import com.suraj.emart.adapters.CheckoutProductAdapter;
import com.suraj.emart.databinding.ActivityCartBinding;
import com.suraj.emart.databinding.ActivityCheckoutBinding;
import com.suraj.emart.models.Product;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Map;
import java.util.Objects;

public class CheckoutActivity extends AppCompatActivity {

    ActivityCheckoutBinding activityCheckoutBinding;
    ActivityCartBinding activityCartBinding;
    CardAdapter cardAdapter;
    CheckoutProductAdapter checkoutProductAdapter;
    ArrayList<Product> productArrayList;
    double ttlPrice = 0;
    final int tax = 5;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityCheckoutBinding = ActivityCheckoutBinding.inflate(getLayoutInflater());
        setContentView(activityCheckoutBinding.getRoot());

        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);


        Cart cart = TinyCartHelper.getCart();
        productArrayList = new ArrayList<>();
        checkoutProductAdapter = new CheckoutProductAdapter(productArrayList, this);
        Objects.requireNonNull(Objects.requireNonNull(Objects.requireNonNull(getSupportActionBar()))).setBackgroundDrawable(AppCompatResources.getDrawable(this, R.color.blue));

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(this,linearLayoutManager.getOrientation());
        dividerItemDecoration.setDrawable(Objects.requireNonNull(AppCompatResources.getDrawable(this, R.drawable.irem_divider)));
        activityCheckoutBinding.recyclerViewCheckout.setLayoutManager(linearLayoutManager);
        activityCheckoutBinding.recyclerViewCheckout.addItemDecoration(dividerItemDecoration);
        activityCheckoutBinding.recyclerViewCheckout.setAdapter(checkoutProductAdapter);

        ttlPrice = (cart.getTotalPrice().doubleValue() * tax / 100) + cart.getTotalPrice().doubleValue();
        activityCheckoutBinding.subTtlCheckout.setText(String.format("INR %s", ttlPrice));
        activityCheckoutBinding.taxCheckout.setText(String.format("%s", tax + "%"));
        ttlPrice = cart.getTotalPrice().doubleValue()*tax/100 + cart.getTotalPrice().doubleValue();
        activityCheckoutBinding.ttlCheckout.setText(String.format("INR %s", ttlPrice));

        for(Map.Entry<Item, Integer> item : cart.getAllItemsWithQty().entrySet())
        {
            Product product = (Product) item.getKey();
            int quantity = item.getValue();
            product.setQuantity(quantity);
            productArrayList.add(product);
        }

        activityCheckoutBinding.proceedToPayment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent proceed2Pay = new Intent(CheckoutActivity.this,PaymentActivity.class);
                proceed2Pay.putExtra("name",activityCheckoutBinding.customerName.getText().toString());
                proceed2Pay.putExtra("mobile",activityCheckoutBinding.customerMobile.getText().toString());
                proceed2Pay.putExtra("email",activityCheckoutBinding.customerEmail.getText().toString());
                proceed2Pay.putExtra("address",activityCheckoutBinding.customerAddress.getText().toString());
                proceed2Pay.putExtra("payment",activityCheckoutBinding.ttlCheckout.getText().toString());
                proceed2Pay.putExtra("products",productArrayList);

                startActivity(proceed2Pay);
                finish();
            }
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        super.onActivityResult(requestCode, resultCode, data);
        // check if the request code is same as what is passed  here it is 2
        if(requestCode==2)
        {
            checkoutProductAdapter.notifyItemRemoved(0);
        }
    }

    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return super.onSupportNavigateUp();
    }

}