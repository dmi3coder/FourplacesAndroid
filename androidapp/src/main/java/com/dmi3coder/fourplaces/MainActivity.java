package com.dmi3coder.fourplaces;

import android.app.Activity;
import android.content.Context;
import android.content.res.Configuration;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.VisibleForTesting;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.etiennelawlor.quickreturn.library.enums.QuickReturnViewType;
import com.etiennelawlor.quickreturn.library.listeners.QuickReturnRecyclerViewOnScrollListener;
import com.malinskiy.superrecyclerview.SuperRecyclerView;

import org.parceler.Parcels;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import io.realm.Realm;
import io.realm.RealmQuery;
import io.realm.RealmResults;
import com.dmi3coder.fourplaces.backend.type.CafeType;
import com.dmi3coder.fourplaces.cafe.Cafe;
import com.dmi3coder.fourplaces.cafe.CafeAdapter;
import com.dmi3coder.fourplaces.cafe.ParcelCafe;
import com.dmi3coder.fourplaces.cafe.SearchFragment;
import xyz.sahildave.widget.SearchViewLayout;

public class MainActivity extends Activity {
    private static final String TAG = "dmi3debug";
    @Deprecated
    public static final String API_URL = "http://ec2-54-191-136-74.us-west-2.compute.amazonaws.com";

    private CafeType currentCafeType;
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
        List<ParcelCafe> parcelCafes = Parcels.unwrap(savedInstanceState.getParcelable("cafeList"));
        List<Cafe> cafes = new ArrayList<>();
        for (ParcelCafe parcelCafe:
                parcelCafes) {
            cafes.add(parcelCafe.toCafe());
        }
        setupAdapter(recyclerView,cafes);
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
        if(choosedCafeType == this.currentCafeType){
            return;
        }
        this.currentCafeType = choosedCafeType;
        recyclerView.showProgress();
        setupAdapter(recyclerView, getCafesFromCache());
    }

    protected List<Cafe> getCafesFromCache(){
        Realm realm = Realm.getInstance(MainApplication.cacheConfig);
        RealmQuery<Cafe> query = realm.where(Cafe.class);
        RealmResults<Cafe> cafes;
        if(currentCafeType==CafeType.ALL){
           cafes = query.findAll();
        }
        else{
            cafes = query.contains("type",currentCafeType.toString()).findAll();
        }
        return cafes;
    }

    protected void setupAdapter(SuperRecyclerView recyclerView, List<Cafe> cafes){
        recyclerView.setAdapter(new CafeAdapter(cafes, MainActivity.this.getBaseContext()));

    }



    private void defineSearchViewLayout() {
        searchFragment = new SearchFragment();
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
        // TODO: 8/20/16 Make Navigation drawer

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
                recyclerView.setAdapter(new CafeAdapter(cafes,MainActivity.this.getApplicationContext()));
                realm.commitTransaction();
                defineChooseAnimation();
                currentCafeType =null;
            }
        });
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        try {
            Parcelable listElements = Parcels.wrap(((CafeAdapter) recyclerView.getAdapter()).getParcelCafeList());
            outState.putParcelable("cafeList", listElements);
            outState.putInt("chooserView",chooseLayout.getVisibility());
        }catch (Exception e){
            Log.e(TAG, "onSaveInstanceState: ",e);
        }
    }


    public void nothingOnClick(View view) {
    }
    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    protected void onStop(){
        super.onStop();
    }

    public SearchViewLayout getSearchViewLayout(){
        return searchViewLayout;
    }
}
