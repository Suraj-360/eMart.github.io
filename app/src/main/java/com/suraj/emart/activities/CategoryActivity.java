package com.suraj.emart.activities;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.content.res.AppCompatResources;
import androidx.recyclerview.widget.GridLayoutManager;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;
import com.suraj.emart.R;
import com.suraj.emart.adapters.ProductAdapter;
import com.suraj.emart.databinding.ActivityCategoryBinding;
import com.suraj.emart.models.Product;

import java.util.ArrayList;
import java.util.Objects;

public class CategoryActivity extends AppCompatActivity {
    ActivityCategoryBinding activityCategoryBinding;
    Product product;
    ArrayList<Product> products;
    ProductAdapter productAdapter;
    FirebaseFirestore firebaseFirestore;
    FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityCategoryBinding = ActivityCategoryBinding.inflate(getLayoutInflater());
        setContentView(activityCategoryBinding.getRoot());

        Objects.requireNonNull(Objects.requireNonNull(Objects.requireNonNull(getSupportActionBar()))).setBackgroundDrawable(AppCompatResources.getDrawable(this, R.color.blue));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        Intent getCat = getIntent();
        String type = getCat.getStringExtra("name");
        Log.e("Type",type);
        String name = getType(type);
        Log.e("Name",name);
        initProducts(name);

    }

    private String getType(String type) {
        if (Objects.equals(type, "Electronics")) {
            Objects.requireNonNull(getSupportActionBar()).setTitle(R.string.electronic);
            return "electronics";
        } else if (Objects.equals(type, "Korean Fashion")) {
            Objects.requireNonNull(getSupportActionBar()).setTitle(R.string.korean);
            return "korean";
        } else if (Objects.equals(type, "Kids")) {
            Objects.requireNonNull(getSupportActionBar()).setTitle(R.string.kids);
            return "kids";
        }else if (Objects.equals(type, "Foot Wear")) {
            Objects.requireNonNull(getSupportActionBar()).setTitle(R.string.foot);
            return "footWear";
        }else if (Objects.equals(type, "Health")) {
            Objects.requireNonNull(getSupportActionBar()).setTitle(R.string.health);
            return "health";
        }else if (Objects.equals(type, "Baby Care Products")) {
            Objects.requireNonNull(getSupportActionBar()).setTitle(R.string.babycare);
            return "babyCare";
        }
        else {
            return "";
        }
    }

    void initProducts(String type) {
        products = new ArrayList<>();
        productAdapter = new ProductAdapter(products, CategoryActivity.this);

        // Add in arrayList Products from firebase
        firebaseAuth = FirebaseAuth.getInstance();
        firebaseFirestore = FirebaseFirestore.getInstance();
        GridLayoutManager layoutManagerProduct = new GridLayoutManager(CategoryActivity.this, 2);
        firebaseFirestore.collection(type).addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                products.clear();
                assert value != null;
                for (DocumentSnapshot documentSnapshot : value.getDocuments()) {
                    Product product = documentSnapshot.toObject(Product.class);
                    products.add(product);
                    assert product != null;
                    Log.e("product electronic",product.getName());
                }
                activityCategoryBinding.productsRecyclerView.setLayoutManager(layoutManagerProduct);
                activityCategoryBinding.productsRecyclerView.setAdapter(productAdapter);
            }
        });
    }
    @Override
    public boolean onSupportNavigateUp()
    {
        finish();
        return super.onSupportNavigateUp();
    }
}