package com.thom.tp1.beans;

public class OrderBean {

    private String       date;
    private double       amount;
    private String       payMethod;
    private String       payStatus;
    private String       deliveryMod;
    private String       deliveryStatus;
    private CustomerBean customer;

    public String getDate() {
        return date;
    }

    public void setDate( String date ) {
        this.date = date;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount( double amount ) {
        this.amount = amount;
    }

    public String getPayMethod() {
        return payMethod;
    }

    public void setPayMethod( String payMethod ) {
        this.payMethod = payMethod;
    }

    public String getPayStatus() {
        return payStatus;
    }

    public void setPayStatus( String payStatus ) {
        this.payStatus = payStatus;
    }

    public String getDeliveryMod() {
        return deliveryMod;
    }

    public void setDeliveryMod( String deliveryMod ) {
        this.deliveryMod = deliveryMod;
    }

    public String getDeliveryStatus() {
        return deliveryStatus;
    }

    public void setDeliveryStatus( String deliveryStatus ) {
        this.deliveryStatus = deliveryStatus;
    }

    public CustomerBean getCustomer() {
        return customer;
    }

    public void setCustomer( CustomerBean customer ) {
        this.customer = customer;
    }

}
