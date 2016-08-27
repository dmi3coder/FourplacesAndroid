package com.dmi3coder.fourplaces.menu;


public class Meal {
    private String name;
    private String description;
    private String price ;
    private String imageUrl;

    public Meal() {
    }

    public Meal(String name, String description, String price, String imageUrl) {
        this.name = name;
        this.description = description;
        this.price = price;
        this.imageUrl = imageUrl;
    }

    public String getImageUrl() {
        return imageUrl;
    }
    public String getPrice() {
        return price;
    }
    public String getDescription() {
        return description;
    }
    public String getName() {
        return name;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }
    public void setPrice(String price) {
        this.price = price;
    }
    public void setDescription(String description) {
        this.description = description;
    }
    public void setName(String name) {
        this.name = name;
    }
}
