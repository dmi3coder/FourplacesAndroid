package studio.jhl.android4places.backend;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import studio.jhl.android4places.bean.Meal;

/**
 * Created by naomi on 1/14/16.
 */
public class MenuParser {
    private JSONObject data_array;
    private static final String TAG = "MenuParser";
    public MenuParser(String result){
        try {
            data_array = new JSONObject(result).getJSONObject("data");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        Log.d(TAG, "parseJsonMenu: " + data_array);
    }


    public ArrayList<Meal> getMeals(MenuLoader.MealType mealType) throws JSONException{
        ArrayList<Meal> result = new ArrayList<>();
        JSONArray mealArray = data_array.getJSONArray(mealType.toJson());
        Meal currentMeal;
        JSONObject currentObject;
        for(int i = 0; i < mealArray.length();i++){
            currentObject = mealArray.getJSONObject(i);
            currentMeal = new Meal();
            currentMeal.setName(currentObject.getString("name"));
            currentMeal.setDescription(currentObject.getString("description"));
            currentMeal.setPrice(currentObject.getString("price"));
            currentMeal.setImageUrl("http://goldenbyteproject.esy.es/img/"+currentObject.getString("imgpath"));
            result.add(currentMeal);
        }
        return result;

    }
}
