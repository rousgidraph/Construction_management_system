package com.example.third_year_project.logic;

import java.io.Serializable;
import java.util.ArrayList;

public class Reciept implements Serializable {
    public Reciept(int receipt_id, int date, String vendor, String receipt_number) {
        this.receipt_id = receipt_id;
        this.date = date;
        this.vendor = vendor;
        this.receipt_number = receipt_number;
    }

    public Reciept(int date, String vendor) {
        this.date = date;
        this.vendor = vendor;
    }

    public Reciept(long date, String vendor, String receipt_number, double total_cost) {
        this.date = date;
        this.vendor = vendor;
        this.receipt_number = receipt_number;
        this.total_cost = total_cost;
    }

    public Reciept(long date, String vendor, String receipt_number) {
        this.date = date;
        this.vendor = vendor;
        this.receipt_number = receipt_number;
    }

    public int getReceipt_id() {
        return receipt_id;
    }

    public void setReceipt_id(int receipt_id) {
        this.receipt_id = receipt_id;
    }

    public long getDate() {
        return date;
    }

    public void setDate(int date) {
        this.date = date;
    }

    public String getVendor() {
        return vendor;
    }

    public void setVendor(String vendor) {
        this.vendor = vendor;
    }

    public String getReceipt_number() {
        return receipt_number;
    }

    public void setReceipt_number(String receipt_number) {
        this.receipt_number = receipt_number;
    }

    public void setDate(long date) {
        this.date = date;
    }

    public double getTotal_cost() {
        return total_cost;
    }

    public void setTotal_cost(double total_cost) {
        this.total_cost = total_cost;
    }

    //String TABLE_RECEIPT;
     int receipt_id;
     long date;
     String vendor;
     String receipt_number;
     double total_cost;
    ArrayList<Transaction> transactions;

    public ArrayList<Transaction> getTransactions() {
        return transactions;
    }

    public void setTransactions(ArrayList<Transaction> transactions) {
        this.transactions = transactions;
    }
}
