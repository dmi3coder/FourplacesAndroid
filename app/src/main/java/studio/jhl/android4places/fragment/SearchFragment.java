package studio.jhl.android4places.fragment;

import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.malinskiy.superrecyclerview.SuperRecyclerView;

import java.util.ArrayList;

import studio.jhl.android4places.R;
import studio.jhl.android4places.adapters.CafeAdapter;
import studio.jhl.android4places.backend.CafeLoader;
import studio.jhl.android4places.bean.Cafe;
import xyz.sahildave.widget.SearchViewLayout;

public class SearchFragment extends Fragment {
    SuperRecyclerView searchCafeListView;
    CafeLoader.CafeType fragmentCurrentCafeType = CafeLoader.CafeType.ALL;
    Context context;
    SearchViewLayout searchViewLayout;


    public SearchFragment(SearchViewLayout searchViewLayout, final Context context) {
        this.context = context;
        this.searchViewLayout = searchViewLayout;

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_search, container, false);
        searchCafeListView = (SuperRecyclerView)v.findViewById(R.id.searchfragmentlist);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(context);
        searchCafeListView.setLayoutManager(layoutManager);
        searchViewLayout.setSearchListener(new SearchViewLayout.SearchListener() {
            @Override
            public void onFinished(final String searchKeyword) {
                Log.d(getClass().getSimpleName(), "onFinished: "+searchKeyword);
                searchCafeListView.setAdapter(null);
                new CafeLoader(CafeLoader.CafeType.ALL).setOnCafesLoadListener(new CafeLoader.OnCafesLoadListener() {
                    @Override
                    public void onEvent(ArrayList<Cafe> cafes) {
                        ArrayList<Cafe> cafesToSearch = new ArrayList<Cafe>();
                        for (int i = 0; i < cafes.size(); i++) {
                            Cafe cafe = cafes.get(i);
                            if((cafe.getName().contains(searchKeyword))
                                    |cafe.getType().contains(searchKeyword)
                                    |cafe.getDescription().contains(searchKeyword)
                                    ){
                                cafesToSearch.add(cafe);
                            }
                        }
                        searchCafeListView.setAdapter(new CafeAdapter(cafesToSearch,context));
                    }
                });
            }
        });

        return v;
    }


}
