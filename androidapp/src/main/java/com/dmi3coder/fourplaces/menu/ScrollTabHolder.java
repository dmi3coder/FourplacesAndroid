package com.dmi3coder.fourplaces.menu;

import android.widget.AbsListView;


public interface ScrollTabHolder {

        void adjustScroll(int scrollHeight);

        void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount, int pagePosition);

}
