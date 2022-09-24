package com.suraj.emart.activities;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.content.res.AppCompatResources;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;
import com.suraj.emart.R;
import com.suraj.emart.adapters.OrderedItemAdapter;
import com.suraj.emart.adapters.ProductAdapter;
import com.suraj.emart.databinding.ActivityOrderHisBinding;
import com.suraj.emart.models.Product;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Objects;

public class OrderHisDet extends AppCompatActivity {

    ActivityOrderHisBinding activityOrderHisBinding;
    FirebaseAuth firebaseAuth;
    FirebaseFirestore firebaseFirestore;
    ArrayList<Product> products;
    ProductAdapter productAdapter;
    OrderedItemAdapter orderedItemAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityOrderHisBinding = ActivityOrderHisBinding.inflate(getLayoutInflater());
        setContentView(activityOrderHisBinding.getRoot());

        Intent get = getIntent();
        String oId = get.getStringExtra("orderId");
        firebaseAuth = FirebaseAuth.getInstance();
        products = new ArrayList<>();
        Objects.requireNonNull(Objects.requireNonNull(Objects.requireNonNull(getSupportActionBar()))).setBackgroundDrawable(AppCompatResources.getDrawable(this, R.color.blue));
        final double[] price = {0};
        //productAdapter = new ProductAdapter(products, OrderHisDet.this);
        orderedItemAdapter = new OrderedItemAdapter(products, price[0],OrderHisDet.this);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        firebaseFirestore = FirebaseFirestore.getInstance();
        String userId = Objects.requireNonNull(firebaseAuth.getCurrentUser()).getUid();
        firebaseFirestore.collection("orderList").document(userId).collection("orderList").document(oId).collection("products").addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                products.clear();
                assert value != null;
                for (DocumentSnapshot documentSnapshot : value.getDocuments())
                {
                    Product product = documentSnapshot.toObject(Product.class);
                    products.add(product);
                    assert product != null;
                    price[0] = price[0] + Double.parseDouble(product.getPrice());
                }
                activityOrderHisBinding.recyclerViewCheckout.setLayoutManager(linearLayoutManager);
                activityOrderHisBinding.recyclerViewCheckout.setAdapter(orderedItemAdapter);
            }
        });

        activityOrderHisBinding.taxCheckout.setText("5%");
        activityOrderHisBinding.subTtlCheckout.setText(MessageFormat.format("INR ", price[0]));
        double ttlPrice = price[0] *5/100 + price[0];
        activityOrderHisBinding.ttlCheckout.setText(MessageFormat.format("INR ", ttlPrice));

        }
}