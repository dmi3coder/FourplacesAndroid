package com.dmi3coder.fourplaces;

import android.app.Activity;
import android.content.Context;
import android.content.res.Configuration;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.VisibleForTesting;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.dmi3coder.fourplaces.cafe.CafeFragment;
import com.etiennelawlor.quickreturn.library.enums.QuickReturnViewType;
import com.etiennelawlor.quickreturn.library.listeners.QuickReturnRecyclerViewOnScrollListener;
import com.malinskiy.superrecyclerview.SuperRecyclerView;

import org.parceler.Parcels;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import com.dmi3coder.fourplaces.backend.type.CafeType;
import com.dmi3coder.fourplaces.cafe.Cafe;
import com.dmi3coder.fourplaces.cafe.CafeAdapter;
import com.dmi3coder.fourplaces.cafe.ParcelCafe;
import com.dmi3coder.fourplaces.cafe.SearchFragment;
import xyz.sahildave.widget.SearchViewLayout;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "dmi3debug";
    @Deprecated
    public static final String API_URL = "http://ec2-54-191-136-74.us-west-2.compute.amazonaws.com";

    private SearchFragment searchFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        getSupportFragmentManager().beginTransaction().add(R.id.cafe_frame,new CafeFragment(),CafeFragment.TAG).commit();
    }




    @VisibleForTesting
    private boolean isNetworkAvailable() {
        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        return cm.getActiveNetworkInfo() != null;

    }







//    private void defineHeaderButtons() {
//        for(int currentButton = 0;currentButton<headerButtons.size()-1;currentButton++){
//            final int currentButtonFinal = currentButton;
//            headerButtons.get(currentButton).setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    fillRecyclerView(CafeType.values()[currentButtonFinal]);
//                }
//            });
//        }
//
//        defineMoreButton();
//    }
//
//    private void defineMoreButton() {
//        Log.d(TAG, "defineMoreButton: ");
//        headerButtons.get(headerButtons.size()-1).setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                defineChooseAnimation();
//            }
//        });
//    }


    private void defineChooseAnimation() {
        // TODO: 8/20/16 Make Navigation drawer

    }


//    private void defineChooseButtons() {
//        for(int i = 0; i< chooseButtons.size()-1;i++){
//            final int finalI = i;
//            chooseButtons.get(i).setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    fillRecyclerView(CafeType.values()[finalI]);
//                    defineChooseAnimation();
//                }
//            });
//        }
//    }


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

}
