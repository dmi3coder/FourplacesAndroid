package studio.jhl.android4places.backend;

import java.util.List;

import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;
import retrofit2.http.Path;
import studio.jhl.android4places.MainActivity;
import studio.jhl.android4places.backend.type.CafeType;
import studio.jhl.android4places.bean.Cafe;

/**
 * Created by dmi3coder on 3/7/16 11:19 AM.
 */
public class RetrofitCafeLoader extends CafeLoader {
    public interface FourplaceService{
        @GET("{api}")
        Call<List<Cafe>> cafeList(@Path("api") String apiUrl);
    }
    private Retrofit retrofit;


    public RetrofitCafeLoader(CafeType type) {
        super(type);
        retrofit = new Retrofit.Builder()
                .baseUrl(MainActivity.API_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }

    @Override
    public void load() {

    }

    public  FourplaceService defineService(){
        return retrofit.create(FourplaceService.class);
    }
}
