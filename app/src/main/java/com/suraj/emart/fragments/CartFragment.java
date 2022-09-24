package com.suraj.emart.fragments;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.content.res.AppCompatResources;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.hishd.tinycart.model.Cart;
import com.hishd.tinycart.model.Item;
import com.hishd.tinycart.util.TinyCartHelper;
import com.suraj.emart.R;
import com.suraj.emart.activities.CartActivity;
import com.suraj.emart.activities.CheckoutActivity;
import com.suraj.emart.adapters.CardAdapter;
import com.suraj.emart.databinding.FragmentCartBinding;
import com.suraj.emart.databinding.FragmentHomeBinding;
import com.suraj.emart.models.Product;

import java.util.ArrayList;
import java.util.Map;
import java.util.Objects;

public class CartFragment extends Fragment {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    FragmentCartBinding fragmentCartBinding;
    ArrayList<Product> productArrayList;
    CardAdapter cardAdapter;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        fragmentCartBinding = FragmentCartBinding.inflate(inflater, container, false);

        Cart cart = TinyCartHelper.getCart();

        productArrayList = new ArrayList<>();
        cardAdapter = new CardAdapter(productArrayList, getActivity(), new CardAdapter.CartListener() {
            @Override
            public void onQuantityChanged() {
                Log.e("Quantity", productArrayList.get(0).getQuantity() + "");
                fragmentCartBinding.totalPrices.setText(String.format("INR %s", cart.getTotalPrice()));
            }
        });

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(requireActivity(), linearLayoutManager.getOrientation());
        dividerItemDecoration.setDrawable(Objects.requireNonNull(AppCompatResources.getDrawable(requireActivity(), R.drawable.irem_divider)));
        fragmentCartBinding.recyclerViewCart.setLayoutManager(linearLayoutManager);
        fragmentCartBinding.recyclerViewCart.addItemDecoration(dividerItemDecoration);
        fragmentCartBinding.recyclerViewCart.setAdapter(cardAdapter);

        fragmentCartBinding.totalPrices.setText(String.format("INR %s", cart.getTotalPrice()));

        for (Map.Entry<Item, Integer> item : cart.getAllItemsWithQty().entrySet()) {
            Product product = (Product) item.getKey();
            int quantity = item.getValue();
            product.setQuantity(quantity);
            productArrayList.add(product);
        }

        fragmentCartBinding.continueBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent orderIntent = new Intent(getActivity(), CheckoutActivity.class);
                startActivity(orderIntent);
            }

        });
        return  fragmentCartBinding.getRoot();
    }
}