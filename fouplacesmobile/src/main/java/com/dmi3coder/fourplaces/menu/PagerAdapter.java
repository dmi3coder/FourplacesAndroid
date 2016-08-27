package com.dmi3coder.fourplaces.menu;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.util.SparseArrayCompat;

import com.dmi3coder.fourplaces.backend.type.MealType;


public class PagerAdapter extends FragmentPagerAdapter {

    private final Category[] categories;
    private SparseArrayCompat<ScrollTabHolder> mScrollTabHolders;
    private ScrollTabHolder mListener;

    public PagerAdapter(FragmentManager fm,Category[] categories) {
        super(fm);
        this.categories = categories;
        mScrollTabHolders = new SparseArrayCompat<ScrollTabHolder>();
    }

    public void setTabHolderScrollingContent(ScrollTabHolder listener) {
        mListener = listener;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return categories[position].getName();
    }

    @Override
    public int getCount() {
        return categories.length;
    }

    @Override
    public Fragment getItem(int position) {
        ScrollTabHolderFragment fragment = (ScrollTabHolderFragment) SampleListFragment.newInstance(position);

        mScrollTabHolders.put(position, fragment);
        if (mListener != null) {
            fragment.setScrollTabHolder(mListener);
        }

        return fragment;
    }

    public SparseArrayCompat<ScrollTabHolder> getScrollTabHolders() {
        return mScrollTabHolders;
    }

}