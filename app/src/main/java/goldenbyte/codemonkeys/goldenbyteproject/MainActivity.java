package goldenbyte.codemonkeys.goldenbyteproject;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.ListView;
import android.widget.Toast;

import com.malinskiy.superrecyclerview.SuperRecyclerView;

import java.util.ArrayList;
import java.util.List;

import de.greenrobot.event.EventBus;
import goldenbyte.codemonkeys.goldenbyteproject.adapters.CafeAdapter;
import goldenbyte.codemonkeys.goldenbyteproject.backend.CafeLoader;
import goldenbyte.codemonkeys.goldenbyteproject.bean.Cafe;
import goldenbyte.codemonkeys.goldenbyteproject.events.FailedLoadEvent;
import goldenbyte.codemonkeys.goldenbyteproject.events.LoadEvent;
import goldenbyte.codemonkeys.goldenbyteproject.fragment.SearchFragment;
import xyz.sahildave.widget.SearchViewLayout;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "dmi3debug";
    private CafeLoader.CafeType choosedCafeType;
    SuperRecyclerView recyclerView;
    CafeLoader cafeLoader;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        choosedCafeType = CafeLoader.CafeType.ALL;
        recyclerView = (SuperRecyclerView)findViewById(R.id.list);
        SearchViewLayout searchViewLayout = (SearchViewLayout) findViewById(R.id.search_view);
        searchViewLayout.setExpandedContentFragment(this, new SearchFragment());
//        ArrayList<Cafe> testCafe = new ArrayList<>();
//        testCafe.add(new Cafe());
        new CafeLoader(choosedCafeType).setOnCafesLoadListener(new CafeLoader.OnCafesLoadListener() {
            @Override
            public void onEvent(ArrayList<Cafe> cafes) {
                recyclerView.setAdapter(new CafeAdapter(cafes));
                recyclerView.setLayoutManager(new LinearLayoutManager(MainActivity.this));
            }
        });

    }

    @Override
    protected void onStop() {
        super.onStop();
        EventBus.getDefault().register(this);
    }

    @Override
    protected void onStart() {
        super.onStart();
        EventBus.getDefault().unregister(this);
    }

    public void onEvent(LoadEvent loadEvent){
        try {
            recyclerView.setAdapter(new CafeAdapter(loadEvent.cafes));
//            for(Cafe cafe: loadEvent.cafes){
//                Log.d(TAG, "onEvent: "+cafe.getName()+cafe.getDescription()+cafe.getId());
//            }
            Log.d(TAG, "onEvent: working");
        }catch (Exception e){
            Log.d(TAG, e.getLocalizedMessage());
        }
    }

    public void onEvent(FailedLoadEvent failedLoadEvent){
        Log.d(TAG, "onFailEvent: unworking");
        Toast.makeText(MainActivity.this, failedLoadEvent.message,Toast.LENGTH_LONG).show();
    }

}
