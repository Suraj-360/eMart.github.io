package com.suraj.emart.models;

import com.hishd.tinycart.model.Item;

import java.util.Map;

public class orderHistory {
    String paymentID,orderNo;

    public orderHistory(String paymentID, String orderId)
    {
        this.paymentID = paymentID;
        this.orderNo = orderId;
    }
    public orderHistory()
    {

    }

    public String getPaymentID()
    {
        return paymentID;
    }

    public void setPaymentID(String paymentID)
    {
        this.paymentID = paymentID;
    }

    public String getOrderId()
    {
        return orderNo;
    }

    public void setOrderId(String orderId)
    {
        this.orderNo = orderId;
    }

}
