package studio.jhl.android4places.backend.url;

import android.os.AsyncTask;
import android.util.Log;

import java.io.IOException;

import studio.jhl.android4places.MainActivity;
import studio.jhl.android4places.backend.InputStreamProcessor;
import studio.jhl.android4places.backend.MenuLoader;

/**
 * Created by dmi3coder on 1/10/16.
 */
public class URLMenuLoader extends MenuLoader {
    private long menuId;

    private static final String TAG = "URLMenuLoader";

    public void setOnMenuLoadListener(MenuLoader.OnMenuLoadListener onMenuLoadListener) {
        super.setOnMenuLoadListener(onMenuLoadListener);
    }


    public URLMenuLoader(long menuId) {
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

