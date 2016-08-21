package com.dmi3coder.fourplaces.backend.url;

import android.os.AsyncTask;
import android.util.Log;

import com.dmi3coder.fourplaces.MainActivity;
import com.dmi3coder.fourplaces.backend.InputStreamProcessor;
import com.dmi3coder.fourplaces.backend.MenuLoader;

import java.io.IOException;

@Deprecated
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

