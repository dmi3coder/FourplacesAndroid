package com.dmi3coder.fourplaces;

import android.app.Application;

import io.realm.Realm;
import io.realm.RealmConfiguration;


public class MainApplication extends Application {
    public static RealmConfiguration cacheConfig;
    public static RealmConfiguration favoriteConfig;


    @Override
    public void onCreate() {
        super.onCreate();
        cacheConfig = new RealmConfiguration.Builder(this)
                .name("cache.realm")
                .schemaVersion(1)
                .build();
        favoriteConfig = new RealmConfiguration.Builder(this)
                .name("favorite.realm")
                .schemaVersion(1)
                .build();

        Realm.setDefaultConfiguration(favoriteConfig);
    }

}
