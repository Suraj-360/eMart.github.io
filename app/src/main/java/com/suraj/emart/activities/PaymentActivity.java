package com.suraj.emart.activities;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.content.res.AppCompatResources;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.hishd.tinycart.model.Cart;
import com.hishd.tinycart.model.Item;
import com.hishd.tinycart.util.TinyCartHelper;
import com.razorpay.Checkout;
import com.razorpay.PaymentResultListener;
import com.suraj.emart.R;
import com.suraj.emart.databinding.ActivityPaymentBinding;
import com.suraj.emart.models.Product;
import com.suraj.emart.models.orderHistory;
import com.suraj.emart.models.orderProduct;

import org.json.JSONException;
import org.json.JSONObject;
import java.util.ArrayList;
import java.util.Map;
import java.util.Objects;
import java.util.UUID;

public class PaymentActivity extends AppCompatActivity implements PaymentResultListener {

    ActivityPaymentBinding activityPaymentBinding;
    ArrayList<Product> productList;
    Cart cart;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityPaymentBinding = ActivityPaymentBinding.inflate(getLayoutInflater());
        setContentView(activityPaymentBinding.getRoot());

        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setBackgroundDrawable(AppCompatResources.getDrawable(this, R.color.blue));

        Intent getValue = getIntent();
        String name = getValue.getStringExtra("name");
        String mobile = getValue.getStringExtra("mobile");
        String email = getValue.getStringExtra("email");
        String address = getValue.getStringExtra("address");
        String payment = getValue.getStringExtra("payment");

        cart = TinyCartHelper.getCart();
        productList = new ArrayList<>();

        String amt = payment.substring(4);
        int amount = Math.round(Float.parseFloat(amt)*100);
        Checkout checkout = new Checkout();
        checkout.setKeyID("rzp_test_ZLogoPJyi7eElV");
        JSONObject object = new JSONObject();
        try {
            object.put("name",name);
            object.put("description","eMart Shopping");
            object.put("theme.color","#0093DD");
            object.put("currency","INR");
            object.put("amount",amount);
            object.put("prefill.contact",mobile);
            object.put("prefill.email",email);
            checkout.open(PaymentActivity.this,object);
        }catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onPaymentSuccess(String s) {
        try {
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle("Payment ID");
            builder.setMessage(s);
            builder.show();
            FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
            FirebaseFirestore firebaseFirestore = FirebaseFirestore.getInstance();
            String userId = Objects.requireNonNull(firebaseAuth.getCurrentUser()).getUid();

            String orderNumber = usingRandomUUID();
            firebaseFirestore.collection("orderList").document(userId).collection("orderList").document(orderNumber).set(new orderHistory(orderNumber,s));

            int i  = 1;
            for(Map.Entry<Item, Integer> item : cart.getAllItemsWithQty().entrySet())
            {
                Product product = (Product) item.getKey();
                int quantity = item.getValue();
                product.setQuantity(quantity);
                productList.add(product);
                Log.e("product order ",product.getName());
                firebaseFirestore.collection("orderList").document(userId).collection("orderList").document(orderNumber).set(new orderHistory(s,orderNumber));
                firebaseFirestore.collection("orderList").document(userId).collection("orderList").document(orderNumber).collection("products").document(orderNumber+i).set(new orderProduct(product.getName(), product.getImage(), product.getStatus(), product.getPrice(), product.getProductDesc(), product.getStock(), product.getId(), product.getQuantity()));
                i++;
            }
            Intent intent = new Intent(PaymentActivity.this,MainActivity.class);
            startActivity(intent);
        }catch (Exception e)
        {
            Log.e("Exe",e.toString());
        }
    }

    @Override
    public void onPaymentError(int i, String s) {
        Toast.makeText(getApplicationContext(),s,Toast.LENGTH_SHORT).show();
        Log.e("Payment error",s);
    }

    static String usingRandomUUID() {

        UUID randomUUID = UUID.randomUUID();
        return randomUUID.toString().replaceAll("_", "");
    }
}