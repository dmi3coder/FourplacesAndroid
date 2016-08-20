package com.dmi3coder.fourplaces.backend.url;


import android.os.AsyncTask;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;

import com.dmi3coder.fourplaces.MainActivity;
import com.dmi3coder.fourplaces.backend.CafeLoader;
import com.dmi3coder.fourplaces.backend.InputStreamProcessor;
import com.dmi3coder.fourplaces.backend.type.CafeType;
import com.dmi3coder.fourplaces.cafe.Cafe;


@Deprecated
public class URLCafeLoader extends CafeLoader {
    private static final String TAG = "dmi3debug";



    public URLCafeLoader(CafeType choosedCafeType) {
        super(choosedCafeType);
    }

    @Override
    public void load() {
        new CafeLoadAsyncTask().execute(MainActivity.API_URL + currentCafeType.toOldBackendString());
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
                URLCafeLoader.super.getRestCafesData(parseJsonArray(restResponseResult));
            } catch (JSONException e) {
                Log.d(TAG, "onPostExecute: parseJsonError"+e);
            }
        }
    }

    protected ArrayList<Cafe> parseJsonArray(String result) throws JSONException {
        JSONArray data_array = new JSONObject(result).getJSONArray("data");
        Log.d(TAG, "parseJsonArray: json length "+data_array.length());
        JSONObject restCafeData;
        ArrayList<Cafe> restCafesData = new ArrayList<>();
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
            cafe.setLat(restCafeData.getString("lat"));
            cafe.setLng(restCafeData.getString("lng"));
            cafe.setPhoneNumber(restCafeData.getString("telephone"));
            restCafesData.add(cafe);
        }
        return restCafesData;
    }
}