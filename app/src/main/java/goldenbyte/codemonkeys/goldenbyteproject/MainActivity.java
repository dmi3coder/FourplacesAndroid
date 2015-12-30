package goldenbyte.codemonkeys.goldenbyteproject;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import goldenbyte.codemonkeys.goldenbyteproject.fragment.SearchFragment;
import xyz.sahildave.widget.SearchViewLayout;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        SearchViewLayout searchViewLayout = (SearchViewLayout)findViewById(R.id.search_view);
        searchViewLayout.setExpandedContentFragment(this, new SearchFragment());

    }
}
