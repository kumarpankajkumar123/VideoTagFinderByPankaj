package com.example.TagFinder;

public class ProductModal {

    String title;
    double price;
    String description;
    String category;



    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public ProductModal( String title, double price, String description, String category) {

        this.title = title;
        this.price = price;
        this.description = description;
        this.category = category;
    }
}


