package com.dmi3coder.fourplaces.menu;


import com.google.api.client.json.GenericJson;
import com.google.api.client.util.Key;

import java.io.Serializable;

public class Meal extends GenericJson implements Serializable {

    @Key("_id")
    private String id;
    @Key
    private String cafeId;
    @Key
    private String categoryID;
    @Key
    private String name;
    @Key
    private String description;
    @Key
    private Double price;
    @Key
    private Integer kcal;
    @Key
    private String imageUlr;

    public Meal(){}

    public Meal(String id, String cafeId, String categoryID, String name, String description, Double price, Integer kcal, String imageUlr) {
        this.id = id;
        this.cafeId = cafeId;
        this.categoryID = categoryID;
        this.name = name;
        this.description = description;
        this.price = price;
        this.kcal = kcal;
        this.imageUlr = imageUlr;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCafeId() {
        return cafeId;
    }

    public void setCafeId(String cafeId) {
        this.cafeId = cafeId;
    }

    public String getCategoryID() {
        return categoryID;
    }

    public void setCategoryID(String categoryID) {
        this.categoryID = categoryID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public Integer getKcal() {
        return kcal;
    }

    public void setKcal(Integer kcal) {
        this.kcal = kcal;
    }

    public String getImageUlr() {
        return imageUlr;
    }

    public void setImageUlr(String imageUlr) {
        this.imageUlr = imageUlr;
    }
}
