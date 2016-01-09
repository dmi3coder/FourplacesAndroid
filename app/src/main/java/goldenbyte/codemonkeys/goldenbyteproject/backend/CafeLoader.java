package goldenbyte.codemonkeys.goldenbyteproject.backend;


import android.os.AsyncTask;
import android.util.Log;

import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

import goldenbyte.codemonkeys.goldenbyteproject.bean.Cafe;


/**
 * Created by dmi3coder on 29.12.2015 5:17.
 */
public class CafeLoader {
    private static final String TAG = "dmi3debug";

    public interface OnCafesLoadListener{
        void onEvent(ArrayList<Cafe> cafes);
    }
    private OnCafesLoadListener onCafesLoadListener;
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
    ArrayList<Cafe> restCafesData;
    public static final String API_URL = "http://goldenbyteproject.esy.es";

    public CafeLoader(CafeType choosedCafeType) {
        new HttpAsyncTask().execute(API_URL+choosedCafeType.toString());
    }
    public void setOnCafesLoadListener(OnCafesLoadListener listener){
        onCafesLoadListener = listener;
    }

    private class HttpAsyncTask extends AsyncTask<String, Void, String> {
        @Override
        protected String doInBackground(String... urls) {
            return GET(urls[0]);
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
            cafe.setImageUrl(API_URL+"/img/"+restCafeData.getString("img_path"));
            restCafesData.add(cafe);
        }
        Log.d(TAG, "parseJsonArray: loaded size"+restCafesData.size());
        getRestCafesData();

    }

    public static String GET(String url){
        InputStream inputStream = null;
        String result = "";
        try {
            // create HttpClient
            OkHttpClient client = new OkHttpClient();

            // make GET request to the given URL
            Request request = new Request.Builder().url(url).build();
            Response response = client.newCall(request).execute();

            // receive response as inputStream
            inputStream = response.body().byteStream();
            // convert inputstream to string
            if(inputStream != null)
                result = convertInputStreamToString(inputStream);
            else
                result = "Did not work!";

        } catch (Exception e) {
            Log.d("InputStream", "error");
        }

        return result;
    }
    private static String convertInputStreamToString(InputStream inputStream) throws IOException{
        BufferedReader bufferedReader = new BufferedReader( new InputStreamReader(inputStream));
        String line = "";
        String result = "";
        while((line = bufferedReader.readLine()) != null)
            result += line;

        inputStream.close();
        return result;

    }

    public void getRestCafesData(){
        if (onCafesLoadListener != null)
            onCafesLoadListener.onEvent(restCafesData);

    }
}
