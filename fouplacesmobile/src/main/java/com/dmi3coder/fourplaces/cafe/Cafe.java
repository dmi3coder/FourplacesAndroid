package com.dmi3coder.fourplaces.cafe;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.api.client.util.Key;

import com.google.api.client.json.GenericJson;
import com.kinvey.java.model.KinveyMetaData;

import java.io.Serializable;

public class Cafe extends GenericJson implements Serializable { // For Serialization

    @Key("_id")
    private String id;
    @Key
    private String name;
    @Key
    private String type;
    @Key
    private String description;
    @Key
    private String address;
    @Key
    private String phoneNumber;
    @Key
    private Double latitude;
    @Key
    private String imageUrl;
    @Key
    private Double longitude;
    @Key
    private String workTime;

    public Cafe() {
    }

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

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public Double getLatitude() {
        return latitude;
    }

    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public Double getLongitude() {
        return longitude;
    }

    public void setLongitude(Double longitude) {
        this.longitude = longitude;
    }

    public String getWorkTime() {
        return workTime;
    }

    public void setWorkTime(String workTime) {
        this.workTime = workTime;
    }

}