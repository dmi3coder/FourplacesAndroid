package com.dmi3coder.fourplaces.menu;

import android.app.Fragment;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v4.util.SparseArrayCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.dmi3coder.fourplaces.CafeActivity;
import com.dmi3coder.fourplaces.R;
import com.dmi3coder.fourplaces.cafe.Cafe;
import com.dmi3coder.fourplaces.cafe.CafeAdapter;
import com.dmi3coder.fourplaces.databinding.FragmentMenuBinding;
import com.google.gson.Gson;


import java.util.HashMap;
import java.util.Hashtable;

import butterknife.Bind;
import butterknife.ButterKnife;


public class MenuFragment extends Fragment implements ScrollTabHolder, ViewPager.OnPageChangeListener{
    public static final boolean NEEDS_PROXY = Integer.valueOf(Build.VERSION.SDK_INT).intValue() < 11;
    private static final String TAG = "MenuFragment";

    private FragmentMenuBinding binding;

    private PagerAdapter mPagerAdapter;
    private int mMinHeaderHeight;
    private int mHeaderHeight;
    private int mMinHeaderTranslation;
    private TextView info;
    private int mLastY;
    private static Cafe cafe;
    private Category[] cafeCategories;




    public static MenuFragment newInstance(String currentCafe, String categories){
        Bundle args = new Bundle();
        MenuFragment fragment = new MenuFragment();
        args.putString(CafeActivity.CAFE_EXTRA, currentCafe);
        args.putString(CafeActivity.CATEGORIES_EXTRA, categories);
        fragment.setArguments(args);
        return fragment;

    }
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_menu,container,false);
        parseArguments(getArguments());
        mMinHeaderHeight = getResources().getDimensionPixelSize(R.dimen.min_header_height);
        mHeaderHeight = getResources().getDimensionPixelSize(R.dimen.header_height);
        mMinHeaderTranslation = -mMinHeaderHeight;
        defineHeader();

        binding.pager.setOffscreenPageLimit(3);

        mPagerAdapter = new PagerAdapter(((AppCompatActivity) getActivity()).getSupportFragmentManager(),cafeCategories);
        mPagerAdapter.setTabHolderScrollingContent(this);
        binding.pager.setAdapter(mPagerAdapter);
        binding.tabs.setViewPager(binding.pager);
        binding.tabs.setOnPageChangeListener(MenuFragment.this);
        mLastY=0;
        return binding.getRoot();
    }

    private void parseArguments(Bundle args){
        Gson gson = new Gson();
        cafeCategories = gson.fromJson(args.getString(CafeActivity.CATEGORIES_EXTRA),Category[].class);
        cafe = gson.fromJson(args.getString(CafeActivity.CAFE_EXTRA),Cafe.class);
        Log.d(TAG, "parseArguments: "+cafeCategories.length);
    }

    private void defineHeader() {
        binding.cafeName.setText(cafe.getName());
        Glide.with(this).load(cafe.getImageUrl()).placeholder(R.drawable.no_image).into(binding.cafeImage);
        setupToolbar();
    }

    private void setupToolbar() {
        ((AppCompatActivity) getActivity()).setSupportActionBar(binding.toolbar);
        getActivity().setTitle(cafe.getName());
        binding.toolbar.setNavigationIcon(R.drawable.ic_arrow_back);
        binding.toolbar.setTitleTextColor(ContextCompat.getColor(getActivity(),android.R.color.white));
        binding.toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getActivity().finish();
            }
        });
    }


    private void startIntent(Intent intent) {
        if(intent.resolveActivity(getActivity().getPackageManager())!=null){
            startActivity(intent);
        }
        else Toast.makeText(getActivity(),getResources().getString(R.string.noapp),Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

        if (positionOffsetPixels > 0) {
            int currentItem = binding.pager.getCurrentItem();

            SparseArrayCompat<ScrollTabHolder> scrollTabHolders = mPagerAdapter.getScrollTabHolders();
            ScrollTabHolder currentHolder;

            if (position < currentItem) {
                currentHolder = scrollTabHolders.valueAt(position);
            } else {
                currentHolder = scrollTabHolders.valueAt(position + 1);
            }
            if (NEEDS_PROXY) {
                // TODO is not good
                currentHolder.adjustScroll(binding.header.getHeight() - mLastY);
                binding.header.postInvalidate();
            } else {
                currentHolder.adjustScroll((int) (binding.header.getHeight() + binding.header.getTranslationY()));
            }
        }
    }

    @Override
    public void onPageSelected(int position) {
        SparseArrayCompat<ScrollTabHolder> scrollTabHolders = mPagerAdapter.getScrollTabHolders();
        ScrollTabHolder currentHolder = scrollTabHolders.valueAt(position);
        if(NEEDS_PROXY){
            //TODO is not good
            currentHolder.adjustScroll(binding.header.getHeight()-mLastY);
            binding.header.postInvalidate();
        }else{
            currentHolder.adjustScroll((int) (binding.header.getHeight() +binding.header.getTranslationY()));
        }
    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }

    @Override
    public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount, int pagePosition) {
        if (binding.pager.getCurrentItem() == pagePosition) {
            int scrollY = getScrollY(view);
            if(NEEDS_PROXY){
                //TODO is not good
                mLastY=-Math.max(-scrollY, mMinHeaderTranslation);
                info.setText(String.valueOf(scrollY));
                binding.header.scrollTo(0, mLastY);
                binding.header.postInvalidate();
            }else{
                binding.header.setTranslationY(Math.max(-scrollY, mMinHeaderTranslation));
            }
        }
    }

    @Override
    public void adjustScroll(int scrollHeight) {
        // nothing
    }

    public int getScrollY(AbsListView view) {
        View c = view.getChildAt(0);
        if (c == null) {
            return 0;
        }

        int firstVisiblePosition = view.getFirstVisiblePosition();
        int top = c.getTop();

        int headerHeight = 0;
        if (firstVisiblePosition >= 1) {
            headerHeight = mHeaderHeight;
        }

        return -top + firstVisiblePosition * c.getHeight() + headerHeight;
    }

    public static float clamp(float value, float max, float min) {
        return Math.max(Math.min(value, min), max);
    }
}
