package goldenbyte.codemonkeys.android4places.backend;

import android.os.AsyncTask;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;

import goldenbyte.codemonkeys.android4places.bean.Meal;

/**
 * Created by dmi3coder on 1/10/16.
 */
public class MenuLoader {
    public enum MealType {FIRST,SECOND,DRINK,SNACKS,BAKERY,CANDY,SEA;// TODO: 1/13/16 add etc
        private static final String[] backendJsonTypes = {"first","second","drink","snacks","bread","confectionery","seaproduct","etc"};
        private static final String[] backendRuTypes={"Первое","Второе","Напитки","Закуски","Хлебные изделия","Кондитерские изделия","Морские продукты","Другое"};
        @Override
        public String toString() {
            for (int i = 0; i < MealType.values().length; i++) {
                if(this == MealType.values()[i]){
                    return MealType.backendRuTypes[i];
                }
            }
            return null;
        }

        public String toJson(){
            for (int i = 0; i < MealType.values().length; i++) {
                if(this == MealType.values()[i]){
                    return MealType.backendJsonTypes[i];
                }
            }
            return null;
        }
    }
    private static final String TAG = "MenuLoader";
    private static final String API_URL = "http://goldenbyteproject.esy.es/api";
    private JSONObject data_array;
    private OnMenuLoadListener onMenuLoadListener;



    public interface OnMenuLoadListener{
        void onEvent();
    }
    public void setOnMenuLoadListener(OnMenuLoadListener onMenuLoadListener){
        this.onMenuLoadListener = onMenuLoadListener;
    }


    public MenuLoader(int menuId){
        new MenuLoadAsyncTask().execute(API_URL + "/getmenu/" + menuId);
    }


    private class MenuLoadAsyncTask extends AsyncTask<String,Void,String> {
        @Override
        protected String doInBackground(String... urls) {
            try {
                return new InputStreamProcessor(urls[0]).GET();
            } catch (IOException e) {
                Log.e(TAG, "doInBackground: error to GET", e);
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
        data_array = new JSONObject(result).getJSONObject("data");// TODO: 1/12/16 make normal input data
        Log.d(TAG, "parseJsonMenu: " + data_array);
    }

    public void getRestMenuData(){
        if(onMenuLoadListener != null)
            onMenuLoadListener.onEvent();
    }

    public ArrayList<Meal> getMeals(MealType mealType) throws JSONException {
        ArrayList<Meal> result = new ArrayList<>();
        JSONArray mealArray = data_array.getJSONArray(mealType.toJson());
        Meal currentMeal;
        JSONObject currentObject;
        for(int i = 0; i < mealArray.length();i++){
            currentObject = mealArray.getJSONObject(i);
            currentMeal = new Meal();
            currentMeal.setName(currentObject.getString("name"));
            currentMeal.setDescription(currentObject.getString("description"));
            currentMeal.setPrice(currentObject.getInt("price"));
            currentMeal.setImageUrl("imgpath");
            result.add(currentMeal);
        }
        return result;

    }
}
