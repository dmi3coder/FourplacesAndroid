package com.dmi3coder.fourplaces;

import android.content.Context;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.support.annotation.VisibleForTesting;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.dmi3coder.fourplaces.cafe.CafeFragment;
import com.dmi3coder.fourplaces.cafe.SearchFragment;
import com.dmi3coder.fourplaces.menu.NavigationAdapter;

import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "dmi3debug";

    private DrawerLayout drawerLayout;
    private ListView listView;
    private SearchFragment searchFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        drawerLayout = (DrawerLayout) findViewById(R.id.drawer);
        listView = (ListView)findViewById(R.id.right_drawer);
        setupNavigationDrawer();
        ButterKnife.bind(this);
        getSupportFragmentManager().beginTransaction().add(R.id.cafe_frame,new CafeFragment(),CafeFragment.TAG).commit();
    }

    private void setupNavigationDrawer() {
        NavigationAdapter adapter = new NavigationAdapter(this);
        listView.setAdapter(adapter);
    }


    @VisibleForTesting
    private boolean isNetworkAvailable() {
        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        return cm.getActiveNetworkInfo() != null;

    }

    public void openDrawer(){
        drawerLayout.openDrawer(GravityCompat.END,true);
    }









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
}
