package studio.jhl.android4places.backend;

import android.os.AsyncTask;
import android.util.Log;

import java.io.IOException;

/**
 * Created by dmi3coder on 1/10/16.
 */
public class MenuLoader {

    public enum MealType {
        FIRST, SECOND, DRINK, SNACKS, BAKERY, CANDY, SEA, ETC;
        private static final String[] backendJsonTypes = {"first", "second", "drink", "snacks", "bread", "confectionery", "seaproduct", "etc"};
        public static final String[] backendRuTypes = {"Первое", "Второе", "Напитки", "Закуски", "Хлебные изделия", "Кондитерские изделия", "Морские продукты"};

        @Override
        public String toString() {
            for (int i = 0; i < MealType.values().length; i++) {
                if (this == MealType.values()[i]) {
                    return MealType.backendRuTypes[i];
                }
            }
            return null;
        }

        public String toJson() {
            for (int i = 0; i < MealType.values().length; i++) {
                if (this == MealType.values()[i]) {
                    return MealType.backendJsonTypes[i];
                }
            }
            return null;
        }
    }

    private static final String TAG = "MenuLoader";
    private static final String API_URL = "http://codylab.net/api";
    private OnMenuLoadListener onMenuLoadListener;
    private String result;


    public String getResult() {
        return result;
    }

    public interface OnMenuLoadListener {
        void onEvent(String result);
    }

    public void setOnMenuLoadListener(OnMenuLoadListener onMenuLoadListener) {
        this.onMenuLoadListener = onMenuLoadListener;
    }


    public MenuLoader(int menuId) {
        new MenuLoadAsyncTask().execute(API_URL + "/getmenu/" + menuId);
    }


    private class MenuLoadAsyncTask extends AsyncTask<String, Void, String> {
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
            super.onPostExecute(s);
            result = s;
            getRestMenuData();
        }


    }

    public void getRestMenuData() {
        if (onMenuLoadListener != null)
            onMenuLoadListener.onEvent(result);
    }
}

