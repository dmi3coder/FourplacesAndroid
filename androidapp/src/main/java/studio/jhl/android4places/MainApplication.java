package studio.jhl.android4places;

import android.app.Application;
import android.util.Log;


import com.baasbox.android.BaasBox;
import com.baasbox.android.BaasHandler;
import com.baasbox.android.BaasResult;
import com.baasbox.android.BaasUser;

import io.realm.Realm;
import io.realm.RealmConfiguration;
import studio.jhl.android4places.cache.CacheService;

/**
 * Created by dmi3coder on 2/4/16.
 */
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
        CacheService service = new CacheService();
        service.startActionLoad(this);
    }

}
