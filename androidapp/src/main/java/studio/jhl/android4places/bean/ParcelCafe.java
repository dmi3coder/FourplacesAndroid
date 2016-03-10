package studio.jhl.android4places.bean;

import org.parceler.Parcel;
import org.parceler.ParcelConstructor;

/**
 * Created by dmi3coder on 3/6/16 8:04 PM.
 */

@Parcel
public class ParcelCafe {
     String name;    //Name of cafe, in backend - name
     String type;    //Type of cafe, in backend - type
     String description;
     String workTime;    //Time when the cafe is working, in backend - worktime
     String position;    //World position of cafe, in backend - adress
     String imageUrl;    //URL location of image, in backend - imgpath

     long id; //id of menu items, in backend - menu_id
     String phoneNumber;
     String lat,lng;

    @ParcelConstructor
    public ParcelCafe(String name,
                      String type,
                      String description,
                      String workTime,
                      String position,
                      String imageUrl,
                      long id,
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

    public static ParcelCafe generate(Cafe cafe){
        return new ParcelCafe(cafe.getName(),cafe.getType(),cafe.getDescription(),cafe.getWorkTime(),cafe.getPosition(),cafe.getImageUrl(),cafe.getId(),cafe.getPhoneNumber(),cafe.getLat(),cafe.getLng());
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
    public long getId() {
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
