package com.example.to_do.model;

public class Item {
    private int id;
    private String item_name;
    private int item_quantity;
    private String item_brand;
    private String item_size;



    private String dateItem;

    public Item() {
    }

    public Item(int id, String item_name, int item_quantity, String item_brand, String item_size , String dateItem) {
        this.id = id;
        this.item_name = item_name;
        this.item_quantity = item_quantity;
        this.item_brand = item_brand;
        this.item_size = item_size;
        this.dateItem = dateItem;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getItem_name() {
        return item_name;
    }

    public void setItem_name(String item_name) {
        this.item_name = item_name;
    }

    public int getItem_quantity() {
        return item_quantity;
    }

    public void setItem_quantity(int item_quantity) {
        this.item_quantity = item_quantity;
    }

    public String getItem_brand() {
        return item_brand;
    }

    public void setItem_brand(String item_brand) {
        this.item_brand = item_brand;
    }

    public String getItem_size() {
        return item_size;
    }

    public void setItem_size(String item_size) {
        this.item_size = item_size;
    }
    public String getDateItem() {
        return dateItem;
    }

    public void setDateItem(String dateItem) {
        this.dateItem = dateItem;
    }
}
