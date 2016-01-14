package goldenbyte.codemonkeys.android4places.bean;

/**
 * Created by dmi3coder on 1/10/16.
 */
public class Meal {
    private String name = "";
    private String description = "";
    private String price = "";
    private String imageUrl = "";
    private boolean debugMode = false;

    public String getImageUrl() {
        if(debugMode)return "https://upload.wikimedia.org/wikipedia/commons/1/1b/Square_200x200.png";
        return imageUrl;
    }
    public String getPrice() {
        if(debugMode)return "300";
        return price;
    }
    public String getDescription() {
        if(debugMode)return "test description test description test description test description test description test description test description test description test description test description test description test description ";
        return description;
    }
    public String getName() {
        if(debugMode)return "Зефирыч";
        return name;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }
    public void setPrice(String price) {
        this.price = price;
    }
    public void setDescription(String description) {
        this.description = description;
    }
    public void setName(String name) {
        this.name = name;
    }
}
