package studio.jhl.android4places;

import android.app.Application;

import io.realm.Realm;
import io.realm.RealmConfiguration;

/**
 * Created by dmi3coder on 2/4/16.
 */
public class MainApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        Realm.setDefaultConfiguration(new RealmConfiguration.Builder(this).build());
    }

}
