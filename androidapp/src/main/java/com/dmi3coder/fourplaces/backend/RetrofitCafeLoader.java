package com.dmi3coder.fourplaces.backend;

import java.util.List;

import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;
import retrofit2.http.Path;
import com.dmi3coder.fourplaces.MainActivity;
import com.dmi3coder.fourplaces.backend.type.CafeType;
import com.dmi3coder.fourplaces.cafe.Cafe;

@Deprecated
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
