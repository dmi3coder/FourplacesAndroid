package studio.jhl.android4places.backend;

import android.os.AsyncTask;
import android.util.Log;

import java.io.IOException;

import studio.jhl.android4places.MainActivity;

/**
 * Created by dmi3coder on 1/10/16.
 */
public class URLMenuLoader extends MenuLoader {
    private int menuId;

    private static final String TAG = "URLMenuLoader";

    public void setOnMenuLoadListener(MenuLoader.OnMenuLoadListener onMenuLoadListener) {
        super.setOnMenuLoadListener(onMenuLoadListener);
    }


    public URLMenuLoader(int menuId) {
        super(menuId);
        this.menuId = menuId;
    }

    @Override
    public void load() {
        new MenuLoadAsyncTask().execute(MainActivity.API_URL + "/api/getmenu/" + menuId);
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
            getRestMenuData(s);
        }


    }


}

