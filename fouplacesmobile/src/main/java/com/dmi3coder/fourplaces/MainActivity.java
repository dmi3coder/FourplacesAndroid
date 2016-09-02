package com.dmi3coder.fourplaces;

import android.content.Context;
import android.net.ConnectivityManager;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.VisibleForTesting;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.dmi3coder.fourplaces.cafe.CafeFragment;
import com.dmi3coder.fourplaces.cafe.SearchFragment;
import com.dmi3coder.fourplaces.cafe.NavigationAdapter;
import com.dmi3coder.fourplaces.cafe.NavigationAdapter.NavigationItem;

import butterknife.ButterKnife;
import xyz.sahildave.widget.SearchViewLayout;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "dmi3debug";
    private DrawerLayout drawerLayout;
    private ListView listView;
    private SearchViewLayout searchViewLayout;
    CafeFragment fragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().setStatusBarColor(getResources().getColor(android.R.color.transparent));
        }
        setContentView(R.layout.activity_main);
        drawerLayout = (DrawerLayout) findViewById(R.id.drawer);
        searchViewLayout = (SearchViewLayout)findViewById(R.id.search_view);
        searchViewLayout.setExpandedContentFragment(this,new SearchFragment());
        listView = (ListView)findViewById(R.id.right_drawer);
        setupNavigationDrawer();
        ButterKnife.bind(this);
        fragment = new CafeFragment();
        getSupportFragmentManager().beginTransaction().add(R.id.cafe_frame,fragment,CafeFragment.TAG).commit();
        defineChooseButtons();
    }

    private void setupNavigationDrawer() {
        NavigationAdapter adapter = new NavigationAdapter(this);
        listView.setAdapter(adapter);
    }



    public void openDrawer(){
        drawerLayout.openDrawer(GravityCompat.END,true);
    }



    private void defineChooseButtons() {
        listView.setOnItemClickListener(fragment);
    }

    public SearchViewLayout getSearchViewLayout(){
        return searchViewLayout;
    }

}
