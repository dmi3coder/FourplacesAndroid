package com.dmi3coder.fourplaces.menu;

import android.support.v4.app.Fragment;


public abstract class ScrollTabHolderFragment extends Fragment implements ScrollTabHolder {

    protected ScrollTabHolder mScrollTabHolder;

    public void setScrollTabHolder(ScrollTabHolder scrollTabHolder) {
        mScrollTabHolder = scrollTabHolder;
    }
}