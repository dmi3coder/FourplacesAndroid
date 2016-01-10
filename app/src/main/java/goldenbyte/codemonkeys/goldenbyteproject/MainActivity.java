package goldenbyte.codemonkeys.goldenbyteproject;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.util.Log;

import com.malinskiy.superrecyclerview.SuperRecyclerView;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;
import goldenbyte.codemonkeys.goldenbyteproject.adapters.CafeAdapter;
import goldenbyte.codemonkeys.goldenbyteproject.backend.CafeLoader;
import goldenbyte.codemonkeys.goldenbyteproject.bean.Cafe;
import goldenbyte.codemonkeys.goldenbyteproject.fragment.SearchFragment;
import xyz.sahildave.widget.SearchViewLayout;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "dmi3debug";
    private CafeLoader.CafeType choosedCafeType;
    @Bind(R.id.list) SuperRecyclerView recyclerView;
    @Bind(R.id.search_view)SearchViewLayout searchViewLayout;
    CafeLoader fragmentCafeLoader;
    CafeLoader.CafeType fragmentCurrentCafeType = CafeLoader.CafeType.ALL;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        choosedCafeType = CafeLoader.CafeType.ALL;
        ButterKnife.bind(this);
        defineRecyclerViewAndLoad(choosedCafeType);
        defineSearchViewLayout();
        defineTypeButtons();


    }



    private void defineRecyclerViewAndLoad(CafeLoader.CafeType choosedCafeType) {
        recyclerView = (SuperRecyclerView)findViewById(R.id.list);
        new CafeLoader(choosedCafeType).setOnCafesLoadListener(new CafeLoader.OnCafesLoadListener() {
            @Override
            public void onEvent(ArrayList<Cafe> cafes) {
                Log.d(TAG, "onEvent: working");
                recyclerView.setAdapter(new CafeAdapter(cafes,getBaseContext()));
                recyclerView.setLayoutManager(new LinearLayoutManager(MainActivity.this));
            }
        });
    }

    private void defineSearchViewLayout() {
        final SearchFragment searchFragment = new SearchFragment(choosedCafeType);
        searchViewLayout.setExpandedContentFragment(this, searchFragment);
    }


    private void defineTypeButtons() {
//        Button[] typeButtons ={
//                (Button)findViewById(R.id.button_all),
//                (Button)findViewById(R.id.button_cafe),
//                (Button)findViewById(R.id.button_night_club),
//                (Button)findViewById(R.id.button_fun),
//                (Button)findViewById(R.id.button_restaurant),
//                (Button)findViewById(R.id.button_fastfood),
//                (Button)findViewById(R.id.button_sushi),
//                (Button)findViewById(R.id.button_etc)
//
//        };
//        for(int currentButton = 0;currentButton<typeButtons.length;currentButton++){
//            final int currentButtonFinal = currentButton;
//            typeButtons[currentButton].setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    Log.d(TAG, "onClick: working");
//                    defineRecyclerViewAndLoad(CafeLoader.CafeType.values()[currentButtonFinal]);
//                }
//            });
//        }

    }

}
