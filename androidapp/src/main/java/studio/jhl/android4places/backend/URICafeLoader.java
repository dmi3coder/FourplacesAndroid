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
import studio.jhl.android4places.bean.CafeType;


/**
 * Created by dmi3coder on 29.12.2015 5:17.
 */
public class URICafeLoader extends CafeLoader {
    private static final String TAG = "dmi3debug";
    ArrayList<Cafe> restCafesData;

    @Override
    public void setOnCafesLoadListener(CafeLoader.OnCafesLoadListener listener) {
         super.setOnCafesLoadListener(listener);
    }

    public interface OnCafesLoadListener{
        void onEvent(ArrayList<Cafe> cafes);
    }



    public URICafeLoader(CafeType choosedCafeType) {
        super(choosedCafeType);
        new CafeLoadAsyncTask().execute(MainActivity.API_URL + choosedCafeType.toString());
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
        super.getRestCafesData(restCafesData);

    }
}
