package com.dmi3coder.fourplaces;

import android.app.Application;
import android.content.Context;
import android.net.ConnectivityManager;
import android.support.annotation.VisibleForTesting;

import com.kinvey.android.Client;
import com.kinvey.java.User;
import com.kinvey.java.core.KinveyCancellableCallback;


public class MainApplication extends Application {
    public static Client client;
    public static ConnectivityManager cm;

    @Override
    public void onCreate() {
        super.onCreate();
        client = new Client.Builder("kid_ZJxeg--71W","ad74211eb6a0421794a5e7ec32944724",getApplicationContext()).build();
        client.user().login("test", "test", new KinveyCancellableCallback<User>() {
            @Override
            public boolean isCancelled() {
                return false;
            }

            @Override
            public void onCancelled() {

            }

            @Override
            public void onSuccess(User user) {

            }

            @Override
            public void onFailure(Throwable throwable) {

            }
        });
        cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
    }


    public static Client getClient(){
        return client;
    }



    @VisibleForTesting
    public static boolean isNetworkAvailable() {
        return cm.getActiveNetworkInfo() != null;

    }
}
