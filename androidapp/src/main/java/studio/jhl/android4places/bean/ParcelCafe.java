package studio.jhl.android4places.bean;

import org.parceler.Parcel;
import org.parceler.ParcelConstructor;

/**
 * Created by dmi3coder on 3/6/16 8:04 PM.
 */

@Parcel
public class ParcelCafe {
    private String name;    //Name of cafe, in backend - name
    private String type;    //Type of cafe, in backend - type
    private String description;
    private String workTime;    //Time when the cafe is working, in backend - worktime
    private String position;    //World position of cafe, in backend - adress
    private String imageUrl;    //URL location of image, in backend - imgpath

    private int id; //id of menu items, in backend - menu_id
    private String phoneNumber;
    private String lat,lng;

    @ParcelConstructor
    public ParcelCafe(String name,
                      String type,
                      String description,
                      String workTime,
                      String position,
                      String imageUrl,
                      int id,
                      String phoneNumber,
                      String lat,
                      String lng){

        this.name = name;
        this.type = type;
        this.description = description;
        this.workTime = workTime;
        this.position = position;
        this.imageUrl = imageUrl;
        this.id = id;
        this.phoneNumber = phoneNumber;
        this.lat = lat;
        this.lng = lng;
    }

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
    public int getId() {
        return id;
    }

    public String getDescription() {
        return description;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }





    public Cafe toCafe(){
        Cafe cafe = new Cafe();
        cafe.setName(name);
        cafe.setType(type);
        cafe.setWorkTime(workTime);
        cafe.setPosition(position);
        cafe.setImageUrl(imageUrl);
        cafe.setId(id);
        cafe.setDescription(description);
        cafe.setPhoneNumber(phoneNumber);
        cafe.setLat(lat);
        cafe.setLng(lng);
        return cafe;
    }

    public String getLat() {
        return lat;
    }


    public String getLng() {
        return lng;
    }

}
