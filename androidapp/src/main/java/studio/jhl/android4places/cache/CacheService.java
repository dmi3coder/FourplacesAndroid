package studio.jhl.android4places.cache;

import android.app.IntentService;
import android.content.Context;
import android.content.Intent;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;

import io.realm.Realm;
import io.realm.RealmResults;
import io.realm.Sort;
import studio.jhl.android4places.MainApplication;
import studio.jhl.android4places.backend.CafeLoader;
import studio.jhl.android4places.backend.URLCafeLoader;
import studio.jhl.android4places.backend.type.CafeType;
import studio.jhl.android4places.bean.Cafe;

/**
 * An {@link IntentService} subclass for handling asynchronous task requests in
 * a service on a separate handler thread.
 * <p/>
 * TODO: Customize class - update intent actions, extra parameters and static
 * helper methods.
 */
public class CacheService extends IntentService {
    private static final String TAG = "CacheService";

    private static final String ACTION_LOAD = "studio.jhl.android4places.cache.action.LOAD";
    private static final String ACTION_UPDATE = "studio.jhl.android4places.cache.action.UPDATE";

    // TODO: Rename parameters
    private static final String EXTRA_PARAM1 = "studio.jhl.android4places.cache.extra.PARAM1";
    private static final String EXTRA_PARAM2 = "studio.jhl.android4places.cache.extra.PARAM2";

    public CacheService() {
        super("CacheService");
    }

    /**
     * Starts this service to perform action Foo with the given parameters. If
     * the service is already performing a task this action will be queued.
     *
     * @see IntentService
     */
    // TODO: Customize helper method
    public static void startActionLoad(Context context) {
        Intent intent = new Intent(context, CacheService.class);
        intent.setAction(ACTION_LOAD);
        context.startService(intent);
    }

    /**
     * Starts this service to perform action Baz with the given parameters. If
     * the service is already performing a task this action will be queued.
     *
     * @see IntentService
     */
    // TODO: Customize helper method
//    public static void startActionBaz(Context context, String param1, String param2) {
//        Intent intent = new Intent(context, CacheService.class);
//        intent.setAction(ACTION_UPDATE);
//        intent.putExtra(EXTRA_PARAM1, param1);
//        intent.putExtra(EXTRA_PARAM2, param2);
//        context.startService(intent);
//    }

    @Override
    protected void onHandleIntent(Intent intent) {
        if (intent != null) {
            final String action = intent.getAction();
            if (ACTION_LOAD.equals(action)) {
                final String param1 = intent.getStringExtra(EXTRA_PARAM1);
                final String param2 = intent.getStringExtra(EXTRA_PARAM2);
                handleActionLoad();
            }
        }
    }

    /**
     * Handle action Foo in the provided background thread with the provided
     * parameters.
     */
    private void handleActionLoad() {
        CafeLoader loader = new URLCafeLoader(CafeType.ALL);
        loader.setOnCafesLoadListener(new CafeLoader.OnCafesLoadListener() {
            @Override
            public void onEvent(ArrayList<Cafe> cafes) {
                final Realm realm = Realm.getInstance(MainApplication.cacheConfig);
                realm.beginTransaction();
                realm.copyToRealmOrUpdate(cafes);
                realm.commitTransaction();
                RealmResults<Cafe> resultCafes = realm.where(Cafe.class).findAllSorted("id", Sort.ASCENDING);
                realm.close();
                EventBus.getDefault().post(new CacheEvent(CacheEvent.RESULT_DONE));
            }
        });
    }

}
