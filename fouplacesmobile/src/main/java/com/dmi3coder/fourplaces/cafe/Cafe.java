package com.dmi3coder.fourplaces.cafe;

import com.google.api.client.util.Key;
import com.google.gson.annotations.SerializedName;
import com.kinvey.java.model.KinveyFile;


public class Cafe extends KinveyFile {

    @Key
    private String name;    //Name of cafe, in backend - name
    @Key
    private String type;    //Type of cafe, in backend - type
    @Key
    private String description;
    @Key
    private String workTime;    //Time when the cafe is working, in backend - workTime
    @Key
    private String address;    //World address of cafe, in backend - adress
    @Key
    private String imageUrl;    //URL location of image, in backend - imgpath
    @Key
    private String phoneNumber;
    @Key
    private String lat,lng;

    public String getName() {
        return name;
    }
    public String getType() {
        return type;
    }
    public String getWorkTime() {
        return workTime;
    }
    public String getAddress() {
        return address;
    }
    public String getImageUrl() {
        return imageUrl;
    }

    public void setName(String name) {
        this.name = name;
    }
    public void setType(String type) {
        this.type = type;
    }
    public void setWorkTime(String workTime) {
        this.workTime = workTime;
    }
    public void setAddress(String address) {
        this.address = address;
    }
    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getLat() {
        return lat;
    }

    public void setLat(String lat) {
        this.lat = lat;
    }

    public String getLng() {
        return lng;
    }

    public void setLng(String lng) {
        this.lng = lng;
    }
}
