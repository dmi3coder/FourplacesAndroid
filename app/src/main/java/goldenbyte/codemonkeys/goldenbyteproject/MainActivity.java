package goldenbyte.codemonkeys.goldenbyteproject;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;

import com.github.ksoichiro.android.observablescrollview.ObservableListView;

import java.util.ArrayList;
import java.util.List;

import goldenbyte.codemonkeys.goldenbyteproject.backend.CafeLoader;
import goldenbyte.codemonkeys.goldenbyteproject.fragment.SearchFragment;
import xyz.sahildave.widget.SearchViewLayout;

public class MainActivity extends AppCompatActivity {
    ArrayList<String> ruTypesInArray;
    String[] ruTypesInStringArray = {"Кофейня", "Ночной клуб", "Развлечения", "Ресторан", "Фаст фуд", "Суши бар", "Что то другое"};
    private static final String TAG = "dmi3debug";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ruTypesInArray = new ArrayList<>();
        setContentView(R.layout.activity_main);
        for(String s : ruTypesInStringArray){
            ruTypesInArray.add(s);
            Log.d(TAG, "onCreate: "+ruTypesInArray.indexOf(s));
        }
        SearchViewLayout searchViewLayout = (SearchViewLayout) findViewById(R.id.search_view);
        searchViewLayout.setExpandedContentFragment(this, new SearchFragment());
        new CafeLoader();

    }

}
