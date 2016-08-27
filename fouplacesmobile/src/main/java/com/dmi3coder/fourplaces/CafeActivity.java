package com.dmi3coder.fourplaces;

import android.annotation.SuppressLint;
import android.app.Fragment;
import android.app.FragmentTransaction;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.FrameLayout;

import com.dmi3coder.fourplaces.cafe.Cafe;
import com.dmi3coder.fourplaces.cafe.CafeAdapter;
import com.dmi3coder.fourplaces.menu.Category;
import com.dmi3coder.fourplaces.menu.MenuFragment;


import java.util.HashMap;
import java.util.Hashtable;


@SuppressLint("NewApi")
public class CafeActivity extends AppCompatActivity {

    public static final String CATEGORIES_EXTRA = "categories_extra";
    public static final boolean NEEDS_PROXY = Integer.valueOf(Build.VERSION.SDK_INT).intValue() < 11;
    public static String result;
    private FrameLayout frame;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        frame = new FrameLayout(this);
        frame.setTag(android.R.id.content);
        setContentView(frame, new FrameLayout.LayoutParams(
                FrameLayout.LayoutParams.MATCH_PARENT,
                FrameLayout.LayoutParams.MATCH_PARENT));
        fillWithMenu(savedInstanceState);
    }

    protected void fillWithMenu(Bundle savedInstanceState){
        MenuFragment menuFragment;
        Cafe currentCafe = getIntent().getParcelableExtra(CafeAdapter.CURRENT_CAFE_TAG);

        menuFragment = MenuFragment.newInstance(currentCafe, getIntent().getStringExtra(CATEGORIES_EXTRA));
        replaceFragment(menuFragment);
    }


    protected void replaceFragment(Fragment fragment){
        FragmentTransaction ft = getFragmentManager().beginTransaction();
        ft.replace(android.R.id.content, fragment).commit();
    }

    public void onHeaderMenuClick(View view) {

    }

    public void onHeaderReviewsClick(View view) {
    }
}