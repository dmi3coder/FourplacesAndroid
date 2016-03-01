package studio.jhl.android4places.backend;


import android.os.AsyncTask;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;

import studio.jhl.android4places.MainActivity;
import studio.jhl.android4places.bean.Cafe;


/**
 * Created by dmi3coder on 29.12.2015 5:17.
 */
public class CafeLoader {
    private static final String TAG = "dmi3debug";
    private OnCafesLoadListener onCafesLoadListener;
    ArrayList<Cafe> restCafesData;
    public interface OnCafesLoadListener{
        void onEvent(ArrayList<Cafe> cafes);
    }
    public enum CafeType{ALL,CAFE,NIGHT_CLUB,FUN,RESTAURANT,FASTFOOD,SUSHI,ETC;
        String[] backendRuTypes = {"","Кофейня", "Ночной клуб", "Развлечения", "Ресторан", "Фаст фуд", "Суши бар", "Что то другое"};

        @Override
        public String toString() {
            CafeType[] cafeTypes = CafeType.values();
            for(int i = 0 ;i<cafeTypes.length;i++) {
                if (this == cafeTypes[i]){
                    if(i==0){
                        return "/api/getallcafe";
                    }
                    return "/api/getcafebytype/"+backendRuTypes[i];
                }
            }
            throw new NullPointerException("Cafe type not found");
            }
    }


    public CafeLoader(CafeType choosedCafeType) {
        new CafeLoadAsyncTask().execute(MainActivity.API_URL + choosedCafeType.toString());
    }
    public void setOnCafesLoadListener(OnCafesLoadListener listener){
        onCafesLoadListener = listener;
    }

    private class CafeLoadAsyncTask extends AsyncTask<String, Void, String> {
        @Override
        protected String doInBackground(String... urls) {
            try {
                return new InputStreamProcessor(urls[0]).GET();
            } catch (IOException e) {
                Log.e(TAG, "doInBackground: error to GET",e);
                return null;
            }
        }

        @Override
        protected void onPostExecute(String restResponseResult) {
            try {
                parseJsonArray(restResponseResult);
            } catch (JSONException e) {
                Log.d(TAG, "onPostExecute: parseJsonError"+e);
            }
        }
    }

    private void parseJsonArray(String result) throws JSONException {
        JSONArray data_array = new JSONObject(result).getJSONArray("data");
        Log.d(TAG, "parseJsonArray: json length "+data_array.length());
        JSONObject restCafeData;
        restCafesData = new ArrayList<>();
        Cafe cafe;
        for(int i = 0;i<data_array.length();i++){
            cafe = new Cafe();
            restCafeData = data_array.getJSONObject(i);
            cafe.setId(restCafeData.getInt("id"));
            cafe.setType(restCafeData.getString("type"));
            cafe.setName(restCafeData.getString("name"));
            cafe.setDescription(restCafeData.getString("description"));
            cafe.setPosition(restCafeData.getString("adress"));
            cafe.setWorkTime(restCafeData.getString("work_time"));
            cafe.setImageUrl(MainActivity.API_URL+"/img/"+restCafeData.getString("img_path"));
            cafe.setCoordinates(restCafeData.getString("lat")+","+restCafeData.getString("lng"));
            cafe.setPhoneNumber(restCafeData.getString("telephone"));
            restCafesData.add(cafe);
        }
        getRestCafesData();

    }

    public void getRestCafesData(){
        if (onCafesLoadListener != null)
            onCafesLoadListener.onEvent(restCafesData);

    }
}
