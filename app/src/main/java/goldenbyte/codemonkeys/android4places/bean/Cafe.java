package goldenbyte.codemonkeys.android4places.bean;

/**
 * Created by dmi3coder on 28.12.2015 20:11.
 */
public class Cafe {
    private String name;    //Name of cafe, in backend - name
    private String type;    //Type of cafe, in backend - type
    private String description;
    private String workTime;    //Time when the cafe is working, in backend - worktime
    private String position;    //World position of cafe, in backend - adress
    private String imageUrl;    //URL location of image, in backend - imgpath
    private int id; //id of menu items, in backend - menu_id

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
    public void setId(int id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
