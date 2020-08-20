package com.example.third_year_project.logic;

import java.io.Serializable;

public class Item implements Serializable {

    //String TABLE_items = "ITEMS_DEFINITION" ;
    int item_id ;
    String item_name ;
    String unit_of_measure;
    String item_description ;

    public Item(int item_id, String item_name, String unit_of_measure, String item_description) {
        this.item_id = item_id;
        this.item_name = item_name;
        this.unit_of_measure = unit_of_measure;
        this.item_description = item_description;
    }

    public Item(int item_id, String item_name) {
        this.item_id = item_id;
        this.item_name = item_name;
    }

    @Override
    public String toString() {
        return "Item{" +
                "item_id=" + item_id +
                ", item_name='" + item_name + '\'' +
                ", unit_of_measure='" + unit_of_measure + '\'' +
                ", item_description='" + item_description + '\'' +
                '}';
    }

    public Item(String item_name, String unit_of_measure, String item_description) {
        this.item_name = item_name;
        this.unit_of_measure = unit_of_measure;
        this.item_description = item_description;
    }

    public int getItem_id() {
        return item_id;
    }

    public void setItem_id(int item_id) {
        this.item_id = item_id;
    }

    public String getItem_name() {
        return item_name;
    }

    public void setItem_name(String item_name) {
        this.item_name = item_name;
    }

    public String getUnit_of_measure() {
        return unit_of_measure;
    }

    public void setUnit_of_measure(String unit_of_measure) {
        this.unit_of_measure = unit_of_measure;
    }

    public String getItem_description() {
        return item_description;
    }

    public void setItem_description(String item_description) {
        this.item_description = item_description;
    }
}
