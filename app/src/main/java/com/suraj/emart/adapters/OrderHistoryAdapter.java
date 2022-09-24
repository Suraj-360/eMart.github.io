package com.suraj.emart.adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.suraj.emart.R;
import com.suraj.emart.activities.OrderHisDet;
import com.suraj.emart.databinding.OrdersListHisBinding;
import com.suraj.emart.models.orderHistory;

import java.util.ArrayList;

public class OrderHistoryAdapter extends RecyclerView.Adapter<OrderHistoryAdapter.OrderHistoryViewHolder> {
    ArrayList<orderHistory> orderNumber;
    Context context;


    public OrderHistoryAdapter(ArrayList<orderHistory> orderNumber, Context context) {
        this.orderNumber = orderNumber;
        this.context = context;
    }

    @NonNull
    @Override
    public OrderHistoryAdapter.OrderHistoryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new OrderHistoryAdapter.OrderHistoryViewHolder(LayoutInflater.from(context).inflate(R.layout.orders_list_his, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull OrderHistoryViewHolder holder, int position) {
        orderHistory num = orderNumber.get(position);
        holder.ordersListHisBinding.orderNumberHis.setText(num.getOrderId());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent goHis = new Intent(context, OrderHisDet.class);
                goHis.putExtra("orderId",num.getOrderId());
                context.startActivity(goHis);
            }
        });
    }


    @Override
    public int getItemCount() {
        return orderNumber.size();
    }

    public static class OrderHistoryViewHolder extends RecyclerView.ViewHolder {

        OrdersListHisBinding ordersListHisBinding;

        public OrderHistoryViewHolder(@NonNull View itemView) {
            super(itemView);
            ordersListHisBinding = OrdersListHisBinding.bind(itemView);
        }
    }

}
