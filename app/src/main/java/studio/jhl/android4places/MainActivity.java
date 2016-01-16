package studio.jhl.android4places;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;

import com.etiennelawlor.quickreturn.library.enums.QuickReturnViewType;
import com.etiennelawlor.quickreturn.library.listeners.QuickReturnRecyclerViewOnScrollListener;
import com.malinskiy.superrecyclerview.SuperRecyclerView;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import studio.jhl.android4places.adapters.CafeAdapter;
import studio.jhl.android4places.backend.CafeLoader;
import studio.jhl.android4places.bean.Cafe;
import studio.jhl.android4places.fragment.SearchFragment;
import xyz.sahildave.widget.SearchViewLayout;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "dmi3debug";
    private CafeLoader.CafeType choosedCafeType;
    @Bind(R.id.list) SuperRecyclerView recyclerView;
    @Bind(R.id.search_view)SearchViewLayout searchViewLayout;
    @Bind(R.id.cafeFooter)LinearLayout cafeFooter; // TODO: 1/10/16 make normal footer
    @Bind(R.id.cafeHeader)LinearLayout cafeHeader;
    @Bind({R.id.header_button_all,R.id.header_button_cafe,R.id.header_button_night_club,R.id.header_button_fun,R.id.header_button_more})List<LinearLayout> headerButtons;

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

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        QuickReturnRecyclerViewOnScrollListener scrollListener = new QuickReturnRecyclerViewOnScrollListener.Builder(QuickReturnViewType.BOTH)
                .header(cafeHeader)
                .minHeaderTranslation(-60)
                .footer(cafeFooter)
                .minFooterTranslation(60)
                .isSnappable(true)
                .build();

        recyclerView.setOnScrollListener(scrollListener);

        new CafeLoader(choosedCafeType).setOnCafesLoadListener(new CafeLoader.OnCafesLoadListener() {
            @Override
            public void onEvent(ArrayList<Cafe> cafes) {
                Log.d(TAG, "onEvent: working");
                recyclerView.setAdapter(new CafeAdapter(cafes, getBaseContext()));
            }
        });
    }

    private void defineSearchViewLayout() {
        final SearchFragment searchFragment = new SearchFragment(choosedCafeType);
        searchViewLayout.setExpandedContentFragment(this, searchFragment);
    }


    private void defineTypeButtons() {
        defineHeaderButtons();
        defineMoreButton();

    }

    private void defineHeaderButtons() {
        for(int currentButton = 0;currentButton<headerButtons.size()-1;currentButton++){
            final int currentButtonFinal = currentButton;
            headerButtons.get(currentButton).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    defineRecyclerViewAndLoad(CafeLoader.CafeType.values()[currentButtonFinal]);
                }
            });
        }
    }

    private void defineMoreButton() {
        headerButtons.get(headerButtons.size()-1).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }


}
