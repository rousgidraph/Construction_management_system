package com.example.third_year_project.logic;

import java.io.Serializable;

public class Transaction implements Serializable {
    int trans_id;
    int trans_date;
    int trans_item_id ;
    int trans_unit_change;
    double cost;

    public Transaction(int trans_id, int trans_date, int trans_item_id, int trans_unit_change) {
        this.trans_id = trans_id;
        this.trans_date = trans_date;
        this.trans_item_id = trans_item_id;
        this.trans_unit_change = trans_unit_change;
    }

    public double getCost() {
        return cost;
    }

    public void setCost(double cost) {
        this.cost = cost;
    }

    public Transaction(int trans_item_id, int trans_unit_change, double cost) {

        this.trans_item_id = trans_item_id;
        this.trans_unit_change = trans_unit_change;
        this.cost = cost;
    }

    public Transaction(int trans_item_id, int trans_unit_change) {
        this.trans_item_id = trans_item_id;
        this.trans_unit_change = trans_unit_change;
    }

    public Transaction(int trans_date, int trans_item_id, int trans_unit_change) {
        this.trans_date = trans_date;
        this.trans_item_id = trans_item_id;
        this.trans_unit_change = trans_unit_change;
    }

    public int getTrans_id() {
        return trans_id;
    }

    public void setTrans_id(int trans_id) {
        this.trans_id = trans_id;
    }

    public int getTrans_date() {
        return trans_date;
    }

    public void setTrans_date(int trans_date) {
        this.trans_date = trans_date;
    }


    @Override
    public String toString() {
        return "Transaction{" +
                "trans_item_id=" + trans_item_id +
                ", trans_unit_change=" + trans_unit_change +
                '}';
    }

    public String getFullDetails() {
        return "Transaction{" +
                "trans_id=" + trans_id +
                ", trans_date=" + trans_date +
                ", trans_item_id=" + trans_item_id +
                ", trans_unit_change=" + trans_unit_change +
                '}';
    }

    public int getTrans_item_id() {
        return trans_item_id;
    }

    public void setTrans_item_id(int trans_item_id) {
        this.trans_item_id = trans_item_id;
    }

    public int getTrans_unit_change() {
        return trans_unit_change;
    }

    public void setTrans_unit_change(int trans_unit_change) {
        this.trans_unit_change = trans_unit_change;
    }
}
