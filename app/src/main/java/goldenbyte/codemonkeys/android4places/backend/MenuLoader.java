package goldenbyte.codemonkeys.android4places.backend;

import android.os.AsyncTask;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

import goldenbyte.codemonkeys.android4places.bean.Meal;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by dmi3coder on 1/10/16.
 */
public class MenuLoader {
    private static final String TAG = "MenuLoader";
    private static final String API_URL = "http://goldenbyteproject.esy.es/api";
    private JSONObject data_array;
    private OnMenuLoadListener onMenuLoadListener;
    private String[] categories;



    public interface OnMenuLoadListener{
        void onEvent(String[] categories);
    }

    public MenuLoader(int menuId){
        new MenuLoadAsyncTast().execute(API_URL + "/getmenu/" + menuId);
    }

    public void setOnMenuLoadListener(OnMenuLoadListener onMenuLoadListener){
        this.onMenuLoadListener = onMenuLoadListener;
    }


    private class MenuLoadAsyncTast extends AsyncTask<String,Void,String> {
        @Override
        protected String doInBackground(String... urls) {
            try {
                return GET(urls[0]);
            } catch (IOException e) {
                Log.e(TAG, "doInBackground: errot to GET", e);
                return null;
            }
        }

        @Override
        protected void onPostExecute(String s) {
            try {
                parseJsonMenu(s);
            } catch (JSONException e) {
                e.printStackTrace();
            }
            super.onPostExecute(s);
        }


    }

    private void parseJsonMenu(String result) throws JSONException{
        data_array = new JSONObject(result);// TODO: 1/12/16 make normal input data

        Log.d(TAG, "parseJsonMenu: "+data_array.getJSONArray("drink").getJSONObject(0).getString("name"));
    }

    private void parseCafeCategories(JSONObject data) throws JSONException {
        categories = new String[data_array.length()];
        for (int i = 0;i<data_array.length();i++){
            //categories[i] = data_array.getJSONObject(i).toString();// FIXME: 1/10/16 set true values
            Log.d(TAG, "parseCafeCategories: "+categories[i]);

        }
        getRestMenuData();
    }

    private String GET(String url) throws IOException{
        InputStream inputStream = null;
        String result = "";
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder().url(url).build();
        Response response = client.newCall(request).execute();
        inputStream = response.body().byteStream();
        if(inputStream!= null)
            result = convertInputStreamToString(inputStream);
        else
            result = "Did not work!";
        return result;
    }

    private String convertInputStreamToString(InputStream inputStream) throws IOException {
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
        String line = "";
        String result = "";
        while ((line = bufferedReader.readLine()) != null)
            result += line;
        inputStream.close();
        return result;
    }

    public void getRestMenuData(){
        if(onMenuLoadListener != null)
            onMenuLoadListener.onEvent(categories);
    }

    private ArrayList<Meal> getMeal(int category) throws JSONException {
        ArrayList<Meal> result = new ArrayList<>();
        JSONArray mealArray = data_array.getJSONObject(null).getJSONArray("data");// FIXME: 1/12/16 remove null to category
        Meal currentMeal;
        JSONObject currentObject;
        for(int i = 0; i < mealArray.length();i++){
            currentObject = mealArray.getJSONObject(i);
            currentMeal = new Meal();
            currentMeal.setName(currentObject.getString("name"));
            currentMeal.setDescription(currentObject.getString("description"));
            currentMeal.setPrice(currentObject.getInt("price"));
            currentMeal.setImageUrl("path to image");// FIXME: 1/10/16 set real image path to meal
            result.add(currentMeal);
        }
        return result;

    }
}
