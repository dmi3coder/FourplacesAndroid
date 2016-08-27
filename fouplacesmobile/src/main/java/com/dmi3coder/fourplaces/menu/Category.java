package com.dmi3coder.fourplaces.menu;


import android.os.Parcel;
import android.os.Parcelable;

import com.google.api.client.json.GenericJson;
import com.google.api.client.util.Key;
import com.kinvey.java.model.KinveyMetaData;

import java.io.Serializable;
import java.util.HashMap;

public class Category extends GenericJson implements Serializable { // For Serialization

    @Key("_id")
    private String id;
    @Key
    private String name;
    @Key
    private String description;
    @Key
    private Double position;
    @Key
    private String cafeId;

    public Category(){}

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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

    public Double getPosition() {
        return position;
    }

    public void setPosition(Double position) {
        this.position = position;
    }

    public String getCafeId() {
        return cafeId;
    }

    public void setCafeId(String cafeId) {
        this.cafeId = cafeId;
    }

    public Category(String id, String name, String description, Double position, String cafeId) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.position = position;
        this.cafeId = cafeId;
    }

    public Category(HashMap<String, Object> map) {

        for (String key : map.keySet()) {
            this.put(key, map.get(key));
        }
    }

}
