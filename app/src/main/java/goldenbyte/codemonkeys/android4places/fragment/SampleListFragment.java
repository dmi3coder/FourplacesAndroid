package goldenbyte.codemonkeys.android4places.fragment;

/**
 * Created by naomi on 1/12/16.
 */

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AbsListView.OnScrollListener;
import android.widget.ListView;

import org.json.JSONException;

import java.util.ArrayList;

import goldenbyte.codemonkeys.android4places.R;
import goldenbyte.codemonkeys.android4places.adapters.MenuAdapter;
import goldenbyte.codemonkeys.android4places.backend.MenuLoader;
import goldenbyte.codemonkeys.android4places.backend.MenuParser;
import goldenbyte.codemonkeys.android4places.bean.Meal;

public class SampleListFragment extends ScrollTabHolderFragment {

    private static final String ARG_POSITION = "position";
    private static final String  ARG_JSON = "json";

    private ListView mListView;
    private ArrayList<Meal> mListItems;
    private int mPosition;
    private String menuLoaderResult;
    private MenuParser menuParser;

    public static Fragment newInstance(int position,String result) {
        SampleListFragment f = new SampleListFragment();
        Bundle b = new Bundle();
        b.putString(ARG_JSON,result);
        b.putInt(ARG_POSITION, position);
        f.setArguments(b);
        return f;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mPosition = getArguments().getInt(ARG_POSITION);
        menuLoaderResult = getArguments().getString(ARG_JSON);
        menuParser = new MenuParser(menuLoaderResult); // TODO: 1/14/16 remove constructor from initialisation
        try {
            mListItems = menuParser.getMeals(MenuLoader.MealType.values()[mPosition+1]);
        } catch (JSONException e) {
            e.printStackTrace();
        }
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
//        Log.d("dmi3debug", "onActivityCreated: arraylist"+mListItems.get(0).getName().toString());
        mListView.setAdapter(new MenuAdapter(mListItems,getContext()));

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