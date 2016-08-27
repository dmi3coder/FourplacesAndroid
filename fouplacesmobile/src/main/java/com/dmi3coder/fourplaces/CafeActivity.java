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
    public static final String CAFE_EXTRA = "cafe_extra";
    public static final boolean NEEDS_PROXY = Integer.valueOf(Build.VERSION.SDK_INT).intValue() < 11;
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

        menuFragment = MenuFragment.newInstance(getIntent().getStringExtra(CAFE_EXTRA), getIntent().getStringExtra(CATEGORIES_EXTRA));
        replaceFragment(menuFragment);
    }


    protected void replaceFragment(Fragment fragment){
        FragmentTransaction ft = getFragmentManager().beginTransaction();
        ft.replace(android.R.id.content, fragment).commit();
    }

}