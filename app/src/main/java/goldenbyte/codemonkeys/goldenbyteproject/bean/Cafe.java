package goldenbyte.codemonkeys.goldenbyteproject.bean;

import io.realm.RealmObject;

/**
 * Created by dmi3coder on 28.12.2015 20:11.
 */
public class Cafe extends RealmObject {
    private String name;    //Name of cafe, in backend - name
    private String type;    //Type of cafe, in backend - type
    private String workTime;    //Time when the cafe is working, in backend - worktime
    private String position;    //World position of cafe, in backend - adress
    private String imageUrl;    //URL location of image, in backend - imgpath
    private int menuId; //id of menu items, in backend - menu_id


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
    public int getMenuId() {
        return menuId;
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
    public void setMenuId(int menuId) {
        this.menuId = menuId;
    }
}
