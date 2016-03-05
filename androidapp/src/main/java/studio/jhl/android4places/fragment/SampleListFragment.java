package studio.jhl.android4places.fragment;

/**
 * Created by naomi on 1/12/16.
 */

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AbsListView.OnScrollListener;
import android.widget.ListView;

import org.json.JSONException;

import java.util.ArrayList;

import studio.jhl.android4places.CafeActivity;
import studio.jhl.android4places.R;
import studio.jhl.android4places.adapters.MenuAdapter;
import studio.jhl.android4places.backend.MenuParser;
import studio.jhl.android4places.backend.type.MealType;
import studio.jhl.android4places.bean.Meal;

public class SampleListFragment extends ScrollTabHolderFragment {

    private static final String ARG_POSITION = "position";
    private static final String  ARG_JSON = "json";

    private ListView mListView;
    private ArrayList<Meal> mListItems;
    private int mPosition;
    private String menuLoaderResult;
    private MenuParser menuParser;

    public static Fragment newInstance(int position) {
        SampleListFragment f = new SampleListFragment();
        Bundle b = new Bundle();
        //fix of stack error, if remove position - food position'll be incremented
        b.putInt(ARG_POSITION, position);
        f.setArguments(b);
        return f;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mPosition = getArguments().getInt(ARG_POSITION);


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_list, null);
        mListView = (ListView) v.findViewById(R.id.listView);

        View placeHolderView = inflater.inflate(R.layout.view_header_placeholder, mListView, false);
        placeHolderView.setBackgroundColor(0xFFFFFFFF);
        mListView.addHeaderView(placeHolderView);

        return v;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mListView.setOnScrollListener(new OnScroll());
        menuParser = new MenuParser(CafeActivity.result);
        try {
            mListItems = menuParser.getMeals(MealType.values()[mPosition]);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        menuParser.setOnMenuParseListener(new MenuParser.OnMenuParseListener() {
            @Override
            public void onEvent() {
                try {
                    mListView.setAdapter(new MenuAdapter(mListItems,getContext()));
//            mListItems = new ArrayList<>();
//            for (int i = 0; i < 100; i++) {
//                Meal meal = new Meal();
//                meal.setName("test"+i);
//                mListItems.add(meal);
//            }
                } catch (Exception e) {
                    e.printStackTrace();
                }
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