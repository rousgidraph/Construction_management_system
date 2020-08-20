package com.example.third_year_project.logic;

import java.io.Serializable;

public class Stock implements Serializable {

    //this class is not directly on the database
    /***
     * it will combine data from two seperate tables
     * from item_definition => item_name , units of measure , item description
     * from transaction => item_id , current_units
     * seperate method from receipts => date
     */
    int item_id;
    int current_units;
    String Item_name;
    String unit_of_measure;
    String date;
    String type_of_transaction;

    public Stock(String unit_of_measure, String date, String type_of_transaction) {
        this.unit_of_measure = unit_of_measure;
        this.date = date;
        this.type_of_transaction = type_of_transaction;
    }

    @Override
    public String toString() {
        return "Stock{" +
                "item_id=" + item_id +
                ", current_units=" + current_units +
                ", Item_name='" + Item_name + '\'' +
                ", unit_of_measure='" + unit_of_measure + '\'' +
                ", date='" + date + '\'' +
                '}';
    }

    public Stock(int item_id, int current_units, String item_name, String unit_of_measure, String date) {
        this.item_id = item_id;
        this.current_units = current_units;
        Item_name = item_name;
        this.unit_of_measure = unit_of_measure;
        this.date = date;
    }

    public int getItem_id() {
        return item_id;
    }

    public void setItem_id(int item_id) {
        this.item_id = item_id;
    }

    public int getCurrent_units() {
        return current_units;
    }

    public void setCurrent_units(int current_units) {
        this.current_units = current_units;
    }

    public String getItem_name() {
        return Item_name;
    }

    public void setItem_name(String item_name) {
        Item_name = item_name;
    }

    public String getUnit_of_measure() {
        return unit_of_measure;
    }

    public void setUnit_of_measure(String unit_of_measure) {
        this.unit_of_measure = unit_of_measure;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }


}
