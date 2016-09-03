package com.dmi3coder.fourplaces.menu;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AbsListView.OnScrollListener;
import android.widget.ListView;

import com.dmi3coder.fourplaces.CafeActivity;
import com.dmi3coder.fourplaces.MainApplication;
import com.dmi3coder.fourplaces.R;
import com.kinvey.android.AsyncAppData;
import com.kinvey.android.callback.KinveyListCallback;
import com.kinvey.java.AppData;
import com.kinvey.java.Query;

import org.json.JSONException;

import java.util.ArrayList;
import java.util.Arrays;

public class SampleListFragment extends ScrollTabHolderFragment {
    private static final String TAG = "SampleListFragment";
    private static final String ARG_POSITION = "com.dmi3coder.fourplaces.position";
    private static final String  ARG_JSON = "com.dmi3coder.fourplaces.json";
    public static final String ARG_CAFE_ID = "com.dmi3coder.fourplaces.cafeId";
    public static final String ARG_CATEGORY_ID = "com.dmi3coder.fourplaces.categoryId";

    private ListView mListView;
    private ArrayList<Meal> mListItems;
    private int mPosition;
    private String menuLoaderResult;
    private String cafeId;
    private String categoryId;

    public static Fragment newInstance(int position,String cafeId, String categoryId) {
        SampleListFragment f = new SampleListFragment();
        Bundle b = new Bundle();
        //fix of stack error, if remove position - food position'll be incremented
        b.putInt(ARG_POSITION, position);
        b.putString(ARG_CAFE_ID, cafeId);
        b.putString(ARG_CATEGORY_ID, categoryId);
        f.setArguments(b);
        return f;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mPosition = getArguments().getInt(ARG_POSITION);
        cafeId = getArguments().getString(ARG_CAFE_ID);
        categoryId = getArguments().getString(ARG_CATEGORY_ID);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_list, null);
        mListView = (ListView) v.findViewById(R.id.listView);
        View placeHolderView = inflater.inflate(R.layout.view_header_placeholder, mListView, false);
        placeHolderView.setBackgroundColor(0xFFFFFFFF);
        mListView.addHeaderView(placeHolderView);
        mListView.setOnScrollListener(new OnScroll());
        AsyncAppData<Meal> mealAppData = MainApplication.client.appData("meal",Meal.class);
        Log.d(TAG, "onActivityCreated: ");
        Query query = mealAppData.query();
        query.equals("categoryId",categoryId);
        mealAppData.get(new KinveyListCallback<Meal>() {
            @Override
            public void onSuccess(Meal[] meals) {

                mListView.setAdapter(new MenuAdapter(Arrays.asList(meals),getContext()));

            }

            @Override
            public void onFailure(Throwable throwable) {
                Log.e(TAG, "onFailure: ", throwable);
            }
        });
        if(CafeActivity.NEEDS_PROXY){//in my moto phone(android 2.1),setOnScrollListener do not work well
            mListView.setOnTouchListener(new View.OnTouchListener() {
                @Override
                public boolean onTouch(View v, MotionEvent event) {
                    if (mScrollTabHolder != null)
                        mScrollTabHolder.onScroll(mListView, 0, 0, 0, mPosition);
                    return false;
                }
            });
        }

        return v;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    @Override
    public void adjustScroll(int scrollHeight) {
        if (scrollHeight == 0 && mListView.getFirstVisiblePosition() >= 1) {
            return;
        }

        mListView.setSelectionFromTop(1, scrollHeight);

    }

    public class OnScroll implements OnScrollListener{

        @Override
        public void onScrollStateChanged(AbsListView view, int scrollState) {

        }

        @Override
        public void onScroll(AbsListView view, int firstVisibleItem,
                             int visibleItemCount, int totalItemCount) {
            if (mScrollTabHolder != null)
                mScrollTabHolder.onScroll(view, firstVisibleItem, visibleItemCount, totalItemCount, mPosition);
        }

    }


    @Override
    public void onScroll(AbsListView view, int firstVisibleItem,
                         int visibleItemCount, int totalItemCount, int pagePosition) {
    }

}