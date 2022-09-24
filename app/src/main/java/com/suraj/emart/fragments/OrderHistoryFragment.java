package com.suraj.emart.fragments;

import android.annotation.SuppressLint;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.content.res.AppCompatResources;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;
import com.suraj.emart.R;
import com.suraj.emart.adapters.OrderHistoryAdapter;
import com.suraj.emart.databinding.FragmentOrderHistroyBinding;
import com.suraj.emart.models.UserModel;
import com.suraj.emart.models.orderHistory;

import java.util.ArrayList;
import java.util.Objects;

public class OrderHistoryFragment extends Fragment {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    FragmentOrderHistroyBinding fragmentOrderHistroyBinding;
    FirebaseAuth firebaseAuth;
    FirebaseFirestore firebaseFirestore;
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        fragmentOrderHistroyBinding = FragmentOrderHistroyBinding.inflate(inflater,container,false);
        firebaseAuth = FirebaseAuth.getInstance();
        firebaseFirestore = FirebaseFirestore.getInstance();

        String userId = Objects.requireNonNull(firebaseAuth.getCurrentUser()).getUid();
        firebaseFirestore.collection("orderList").document(userId).collection("orderList").get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                ArrayList<orderHistory> orderList = new ArrayList<>();
                OrderHistoryAdapter orderHistoryAdapter = new OrderHistoryAdapter(orderList,getActivity());
                fragmentOrderHistroyBinding.orderHisRecyclerView.setAdapter(orderHistoryAdapter);
                LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
                DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(requireActivity(), linearLayoutManager.getOrientation());
                dividerItemDecoration.setDrawable(Objects.requireNonNull(AppCompatResources.getDrawable(requireActivity(), R.drawable.irem_divider)));
                fragmentOrderHistroyBinding.orderHisRecyclerView.setLayoutManager(linearLayoutManager);
                fragmentOrderHistroyBinding.orderHisRecyclerView.addItemDecoration(dividerItemDecoration);

                for(DocumentSnapshot documentSnapshot:queryDocumentSnapshots.getDocuments())
                {
                    orderHistory orderNumber = documentSnapshot.toObject(orderHistory.class);
                    orderHistory odObj = new orderHistory();
                    assert orderNumber != null;
                    odObj.setOrderId(orderNumber.getOrderId());
                    odObj.setPaymentID(orderNumber.getPaymentID());
                    orderList.add(odObj);
                    Log.e("Order num",orderNumber.toString());
                    Log.e("AO",orderList.size()+"");
                }
            }
        });

        return fragmentOrderHistroyBinding.getRoot();
    }

}