package com.dmi3coder.fourplaces.menu;

import android.app.Fragment;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
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
    public static final String CURRENT_CAFE_TAG = "currentCafe";
    private static final String TAG = "MenuFragment";

    private PagerAdapter mPagerAdapter;
    private int mMinHeaderHeight;
    private int mHeaderHeight;
    private int mMinHeaderTranslation;
    private TextView info;
    private int mLastY;
    private static Cafe cafe;
    private FragmentMenuBinding binding;
    private Category[] cafeCategories;




    public static MenuFragment newInstance(Cafe currentCafe, String categories){
        Bundle args = new Bundle();
        MenuFragment fragment = new MenuFragment();
//        args.putParcelable(CURRENT_CAFE_TAG, currentCafe); // TODO: 8/27/16 implement cafes
        args.putString(CafeActivity.CATEGORIES_EXTRA, categories);
        fragment.setArguments(args);
        return fragment;

    }
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_menu,container,false);
        cafe = getArguments().getParcelable(CURRENT_CAFE_TAG);
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
//        menuLoader = new URLMenuLoader(menu_id);
//        menuLoader.setOnMenuLoadListener(new URLMenuLoader.OnMenuLoadListener() {
//            @Override
//            public void onEvent(String result) {
//                progressBar.setVisibility(View.GONE);
//                CafeActivity.result = result;
//                mViewPager.setAdapter(mPagerAdapter);
//                mPagerSlidingTabStrip.setViewPager(mViewPager);
//                mPagerSlidingTabStrip.setOnPageChangeListener(MenuFragment.this);
//                mLastY=0;
//            }
//        });
//        menuLoader.load();
        return binding.getRoot();
    }

    private void parseArguments(Bundle args){
        Gson gson = new Gson();
        cafeCategories = gson.fromJson(args.getString(CafeActivity.CATEGORIES_EXTRA),Category[].class);
        Log.d(TAG, "parseArguments: "+cafeCategories.length);
    }

    private void defineHeader() {
//        binding.cafeName.setText(cafe.getName());
//        Glide.with(this).load(cafe.getImageUrl()).asBitmap().into(binding.cafeImage);
//        defineCallAction();
//        defineMapAction();
    }

    private void defineCallAction() {
        binding.headerTextTelephone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_DIAL);
                intent.setData(Uri.parse("tel:"+cafe.getPhoneNumber()));
                startIntent(intent);
            }
        });
    }
    private void defineMapAction(){
        binding.headerTextMap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_VIEW);
                intent.setData(Uri.parse("geo:0,0?q="+cafe.getLatitude()+","+cafe.getLongitude()+"("+cafe.getName()+")"));
                startIntent(intent);
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
