package goldenbyte.codemonkeys.goldenbyteproject.backend;


import java.util.List;

import goldenbyte.codemonkeys.goldenbyteproject.bean.Cafe;
import retrofit.Call;
import retrofit.Retrofit;
import retrofit.http.GET;
import retrofit.http.Path;

/**
 * Created by dmi3coder on 29.12.2015 5:17.
 */
public class CafeLoader {
    private CafeService loadedCafes;
    public static final String API_URL = ""; // TODO: 29.12.2015 add api url

    public Cafe loadCafe(int arrayNumber){
        // TODO: 30.12.2015 make return real values besides non-real
        Cafe cafe = new Cafe();
        cafe.setName("testCafe"+arrayNumber);
        cafe.setType("testType"+arrayNumber);
        cafe.setImageUrl("http://pp.vk.me/c628717/v628717920/3012b/YLIhOEeyWfI.jpg");
        cafe.setMenuId(arrayNumber);
        cafe.setPosition("testPosition"+arrayNumber);
        cafe.setWorkTime("testWorkTime"+arrayNumber);

        return cafe;
    }

    public int size(){
        // TODO: 30.12.2015 make return real value of size
        return 25;
    }


    private interface CafeService{
        @GET("") // TODO: 29.12.2015 fill API adress to Cafes database
        Call<List<Cafe>> names(
                @Path("name") String name,
                @Path("type") String type,
                @Path("worktime") String workTime,
                @Path("adress") String position,
                @Path("imgpath") String imageUrl,
                @Path("menu_id") int menuId
        );

    }   //interface of retrofit call to API and get Cafes values

    public CafeLoader() {
        Retrofit retrofit = new Retrofit.Builder().baseUrl(API_URL).build();
        loadedCafes = retrofit.create(CafeService.class);
        Call<List<Cafe>> cafeNamesCall = loadedCafes.names("","","","","",0);// TODO: 30.12.2015 check how to get an retrofit values

    }
}
