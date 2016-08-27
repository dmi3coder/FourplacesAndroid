package com.dmi3coder.fourplaces.cafe;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.api.client.util.Key;

import com.google.api.client.json.GenericJson;
import com.kinvey.java.model.KinveyMetaData;

public class Cafe extends GenericJson implements Parcelable { // For Serialization

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
    @Key("_kmd")
    private KinveyMetaData meta;
    @Key("_acl")
    private KinveyMetaData.AccessControlList acl;

    public Cafe() {}


    protected Cafe(Parcel in) {
        id = in.readString();
        name = in.readString();
        type = in.readString();
        description = in.readString();
        address = in.readString();
        phoneNumber = in.readString();
        latitude = in.readByte() == 0x00 ? null : in.readDouble();
        imageUrl = in.readString();
        longitude = in.readByte() == 0x00 ? null : in.readDouble();
        workTime = in.readString();
        meta = (KinveyMetaData) in.readValue(KinveyMetaData.class.getClassLoader());
        acl = (KinveyMetaData.AccessControlList) in.readValue(KinveyMetaData.AccessControlList.class.getClassLoader());
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(id);
        dest.writeString(name);
        dest.writeString(type);
        dest.writeString(description);
        dest.writeString(address);
        dest.writeString(phoneNumber);
        if (latitude == null) {
            dest.writeByte((byte) (0x00));
        } else {
            dest.writeByte((byte) (0x01));
            dest.writeDouble(latitude);
        }
        dest.writeString(imageUrl);
        if (longitude == null) {
            dest.writeByte((byte) (0x00));
        } else {
            dest.writeByte((byte) (0x01));
            dest.writeDouble(longitude);
        }
        dest.writeString(workTime);
        dest.writeValue(meta);
        dest.writeValue(acl);
    }

    @SuppressWarnings("unused")
    public static final Parcelable.Creator<Cafe> CREATOR = new Parcelable.Creator<Cafe>() {
        @Override
        public Cafe createFromParcel(Parcel in) {
            return new Cafe(in);
        }

        @Override
        public Cafe[] newArray(int size) {
            return new Cafe[size];
        }

    };

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

    public KinveyMetaData getMeta() {
        return meta;
    }

    public void setMeta(KinveyMetaData meta) {
        this.meta = meta;
    }

    public KinveyMetaData.AccessControlList getAcl() {
        return acl;
    }

    public void setAcl(KinveyMetaData.AccessControlList acl) {
        this.acl = acl;
    }
}