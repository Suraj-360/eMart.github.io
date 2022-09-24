package com.suraj.emart.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.content.res.AppCompatResources;
import android.content.Intent;
import android.os.Bundle;
import android.text.Html;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import com.bumptech.glide.Glide;
import com.hishd.tinycart.model.Cart;
import com.hishd.tinycart.util.TinyCartHelper;
import com.suraj.emart.R;
import com.suraj.emart.databinding.ActivityProductDetailsBinding;
import com.suraj.emart.models.Product;
import java.util.Objects;

public class ProductDetailsActivity extends AppCompatActivity
{
    ActivityProductDetailsBinding activityProductDetailsBinding;
    Product product;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        activityProductDetailsBinding = ActivityProductDetailsBinding.inflate(getLayoutInflater());
        setContentView(activityProductDetailsBinding.getRoot());

        Intent productInt = getIntent();
        String productName = productInt.getStringExtra("Product Name");
        String productImage = productInt.getStringExtra("Product Image");
        String productPrice = productInt.getStringExtra("Product Price");
        String productStatus = productInt.getStringExtra("Product Status");
        String productDesc = productInt.getStringExtra("Product Desc");
        int productStock = productInt.getIntExtra("Product Stock",0);

        product = new Product(productName,productImage,productStatus,productPrice,productDesc,productStock);

        Glide.with(this).load(productImage).into(activityProductDetailsBinding.productDetImg);

        activityProductDetailsBinding.productDescription.setText(Html.fromHtml(productDesc,0));

        Objects.requireNonNull(getSupportActionBar()).setTitle("Product Details");

        getSupportActionBar().setBackgroundDrawable(AppCompatResources.getDrawable(this,R.color.blue));

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        Cart cart = TinyCartHelper.getCart();

        activityProductDetailsBinding.addToCartProductDet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                cart.addItem(product,1);
                activityProductDetailsBinding.addToCartProductDet.setEnabled(false);
                activityProductDetailsBinding.addToCartProductDet.setText("Added to Cart");
                activityProductDetailsBinding.addToCartProductDet.setBackgroundColor(getColor(R.color.blue));
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if(item.getItemId() == R.id.cart)
        {
            startActivity(new Intent(this,CartActivity.class));
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onSupportNavigateUp()
    {
        finish();
        return super.onSupportNavigateUp();
    }
}