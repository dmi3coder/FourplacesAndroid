package studio.jhl.android4places.bean;

import com.google.gson.annotations.SerializedName;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;
import io.realm.annotations.RealmClass;

/**
 * Created by dmi3coder on 28.12.2015 20:11.
 */
@RealmClass
@org.parceler.Parcel(value = org.parceler.Parcel.Serialization.BEAN, analyze = { Cafe.class })
public class Cafe extends RealmObject {

    private String name;    //Name of cafe, in backend - name
    private String type;    //Type of cafe, in backend - type
    private String description;
    @SerializedName("work_time") private String workTime;    //Time when the cafe is working, in backend - workTime
    @SerializedName("adress") private String position;    //World position of cafe, in backend - adress
    @SerializedName("img_path") private String imageUrl;    //URL location of image, in backend - imgpath

    @PrimaryKey
    private long id; //id of menu items, in backend - menu_id
    @SerializedName("telephone") private String phoneNumber;
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
    public String getPosition() {
        return position;
    }
    public String getImageUrl() {
        return imageUrl;
    }
    public long getId() {
        return id;
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
    public void setPosition(String position) {
        this.position = position;
    }
    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }
    public void setId(long id) {
        this.id = id;
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
