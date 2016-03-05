package studio.jhl.android4places;

import android.content.Context;
import android.content.res.Configuration;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.VisibleForTesting;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;
import com.etiennelawlor.quickreturn.library.enums.QuickReturnViewType;
import com.etiennelawlor.quickreturn.library.listeners.QuickReturnRecyclerViewOnScrollListener;
import com.malinskiy.superrecyclerview.SuperRecyclerView;

import org.parceler.Parcels;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import io.realm.Realm;
import studio.jhl.android4places.Animations.ChooserAnimatorListener;
import studio.jhl.android4places.adapters.CafeAdapter;
import studio.jhl.android4places.backend.URLCafeLoader;
import studio.jhl.android4places.backend.type.CafeType;
import studio.jhl.android4places.bean.Cafe;
import studio.jhl.android4places.fragment.SearchFragment;
import xyz.sahildave.widget.SearchViewLayout;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "dmi3debug";
    public static final String API_URL = "http://fourplaces.pp.ua";
    private CafeType choosedCafeType;
    @Bind(R.id.list) SuperRecyclerView recyclerView;
    @Bind(R.id.search_view)SearchViewLayout searchViewLayout;
    @Bind(R.id.cafeFooter)LinearLayout cafeFooter;
    @Bind(R.id.cafeHeader)RelativeLayout cafeHeader;
    @Bind({R.id.header_button_all,
            R.id.header_button_cafe,
            R.id.header_button_night_club,
            R.id.header_button_fun,
            R.id.header_button_more
    })List<LinearLayout> headerButtons;
    @Bind({R.id.choose_button_all,
            R.id.choose_button_cafe,
            R.id.choose_button_night_club,
            R.id.choose_button_fun,
            R.id.choose_button_restaurant,
            R.id.choose_button_fastfood,
            R.id.choose_button_sushi,
            R.id.choose_button_etc
    }) List<Button> chooseButtons;
    @Bind(R.id.chooseLayout) LinearLayout chooseLayout;
    @Bind(R.id.choose_button_favourite) Button chooseFavouriteButton;
    private SearchFragment searchFragment;
    private Realm realm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        realm = Realm.getDefaultInstance();
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        defineRecyclerView();
        if(savedInstanceState!=null){
            loadSavedInstance(savedInstanceState);
        }
        else if(isNetworkAvailable()) {
            fillRecyclerView(CafeType.ALL);
        }
        defineSearchViewLayout();
        defineTypeButtons();
    }


    private void defineRecyclerView() {
        recyclerView = (SuperRecyclerView)findViewById(R.id.list);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        defineStickyHeader();
    }
    private void defineStickyHeader() {
        if(getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT) {
            recyclerView.setOnScrollListener(new QuickReturnRecyclerViewOnScrollListener.Builder(QuickReturnViewType.HEADER)
                    .header(cafeHeader)
                    .minHeaderTranslation(-cafeHeader.getLayoutParams().height)
                    .isSnappable(true)
                    .build());
        }
    }
    private void loadSavedInstance(Bundle savedInstanceState) {
        List<Cafe> cafes = Parcels.unwrap(savedInstanceState.getParcelable("cafeList"));
        recyclerView.setAdapter(new CafeAdapter(cafes,this));
        if(savedInstanceState.getInt("chooserView",View.GONE) == View.VISIBLE) {
            chooseLayout.setVisibility(View.VISIBLE);
        }
        else chooseLayout.setVisibility(View.GONE);

    }


    @VisibleForTesting
    private boolean isNetworkAvailable() {
        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        return cm.getActiveNetworkInfo() != null;

    }

    private void fillRecyclerView(CafeType choosedCafeType) {
        if(choosedCafeType == this.choosedCafeType){
            return;
        }
        this.choosedCafeType = choosedCafeType;
        recyclerView.showProgress();

        new URLCafeLoader(choosedCafeType).setOnCafesLoadListener(new URLCafeLoader.OnCafesLoadListener() {
            @Override
            public void onEvent(ArrayList<Cafe> cafes) {
                Log.d(TAG, "onEvent: working");
                recyclerView.setAdapter(new CafeAdapter(cafes, MainActivity.this.getBaseContext()));
            }

        });
    }

    private void defineSearchViewLayout() {
        searchFragment = new SearchFragment(searchViewLayout,this);
        searchViewLayout.setExpandedContentFragment(this, searchFragment);
    }


    private void defineTypeButtons() {
        defineHeaderButtons();
        defineChooseButtons();
    }


    private void defineHeaderButtons() {
        for(int currentButton = 0;currentButton<headerButtons.size()-1;currentButton++){
            final int currentButtonFinal = currentButton;
            headerButtons.get(currentButton).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    fillRecyclerView(CafeType.values()[currentButtonFinal]);
                }
            });
        }

        defineMoreButton();
    }

    private void defineMoreButton() {
        Log.d(TAG, "defineMoreButton: ");
        headerButtons.get(headerButtons.size()-1).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                defineChooseAnimation();
            }
        });
    }


    private void defineChooseAnimation() {
        Log.d(TAG, "defineChooseAnimation: ");
        switch (chooseLayout.getVisibility()){
            case View.GONE:
                setChooseAnimation(View.VISIBLE,Techniques.FadeIn);
                return;
            case View.VISIBLE:
                setChooseAnimation(View.GONE,Techniques.FadeOut);
        }

    }
    private void setChooseAnimation(int visibility,Techniques animationTechnique){
        YoYo.with(animationTechnique).duration(300).withListener(new ChooserAnimatorListener(visibility,chooseLayout)).playOn(chooseLayout);
    }


    private void defineChooseButtons() {
        for(int i = 0; i< chooseButtons.size()-1;i++){
            final int finalI = i;
            chooseButtons.get(i).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    fillRecyclerView(CafeType.values()[finalI]);
                    defineChooseAnimation();
                }
            });
        }
        chooseFavouriteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                realm.beginTransaction();
                List<Cafe> cafes = realm.where(Cafe.class).findAll();
                recyclerView.setAdapter(new CafeAdapter(cafes,MainActivity.this));
                realm.commitTransaction();
                defineChooseAnimation();
                choosedCafeType=null;
            }
        });
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        try {
            Parcelable listElements = Parcels.wrap(((CafeAdapter) recyclerView.getAdapter()).getCafeList());
            outState.putParcelable("cafeList", listElements);
            outState.putInt("chooserView",chooseLayout.getVisibility());
        }catch (Exception e){}
    }


    public void nothingOnClick(View view) {
    }
}
