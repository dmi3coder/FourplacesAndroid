package studio.jhl.android4places.backend;

import android.os.AsyncTask;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

import studio.jhl.android4places.MainActivity;
import studio.jhl.android4places.bean.Meal;

/**
 * Created by dmi3coder on 1/14/16.
 */
public class MenuParser {
    private JSONObject data_array;
    private static final String TAG = "MenuParser";

    private OnMenuParseListener onMenuParseListener;
    public interface OnMenuParseListener {
        void onEvent();
    }
    public MenuParser(String result){
        try {
            data_array = new JSONObject(result).getJSONObject("data");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        Log.d(TAG, "parseJsonMenu: " + data_array);
    }

    private class MenuAsyncTask extends AsyncTask<String,Void,JSONObject>{

        @Override
        protected JSONObject doInBackground(String... params){
            try {
                return new JSONObject(params[0]).getJSONObject("data");
            } catch (JSONException e) {
                Log.d(TAG, "doInBackground: failed: "+e);
            }
            return null;
        }

        @Override
        protected void onPostExecute(JSONObject jsonObject) {
            data_array = jsonObject;
        }
    }

    public ArrayList<Meal> getMeals(MenuLoader.MealType mealType) throws JSONException{

        try {
            return new MealAsyncTask().execute(mealType).get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
return null;
    }

    private class MealAsyncTask extends AsyncTask<MenuLoader.MealType,Void,ArrayList<Meal>>{
        ArrayList<Meal> result = new ArrayList<>();

        @Override
        protected ArrayList<Meal> doInBackground(MenuLoader.MealType... params) {
            try {
                JSONArray mealArray = data_array.getJSONArray(params[0].toJson());

                for(int i = 0; i < mealArray.length();i++){
                    result.add(addMeal(mealArray,i));
                }
                return result;
            }catch (JSONException e){

            }
            return  null;
        }

        private Meal addMeal(JSONArray mealArray,int i) throws JSONException {
            Meal currentMeal;
            JSONObject currentObject;
            currentObject = mealArray.getJSONObject(i);
            currentMeal = new Meal();
            currentMeal.setName(currentObject.getString("name"));
            currentMeal.setDescription(currentObject.getString("description"));
            currentMeal.setPrice(currentObject.getString("price"));
            currentMeal.setImageUrl(MainActivity.API_URL+"/img/"+currentObject.getString("imgpath"));
            return currentMeal;

        }

        @Override
        protected void onPostExecute(ArrayList<Meal> meals) {
            super.onPostExecute(meals);
            getParseMenuData();

        }
    }

    public void setOnMenuParseListener(OnMenuParseListener onMenuParseListener) {
        this.onMenuParseListener = onMenuParseListener;
    }

    public void getParseMenuData() {
        if (onMenuParseListener != null)
            onMenuParseListener.onEvent();
    }
}
