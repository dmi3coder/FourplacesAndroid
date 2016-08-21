package com.dmi3coder.fourplaces.cafe;

import android.app.Fragment;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.dmi3coder.fourplaces.R;
import com.dmi3coder.fourplaces.backend.type.CafeType;
import com.malinskiy.superrecyclerview.SuperRecyclerView;

public class SearchFragment extends Fragment {
    SuperRecyclerView searchCafeListView;
    CafeType fragmentCurrentCafeType = CafeType.ALL;
//    SearchViewLayout searchViewLayout;

    public SearchFragment() {
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
//        this.searchViewLayout = ((MainActivity)getActivity()).getSearchViewLayout();
        View v = inflater.inflate(R.layout.fragment_search, container, false);
        searchCafeListView = (SuperRecyclerView)v.findViewById(R.id.searchfragmentlist);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity().getApplicationContext());
        searchCafeListView.setLayoutManager(layoutManager);
//        searchViewLayout.setSearchBoxListener(new SearchViewLayout.SearchBoxListener() {
//            @Override
//            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
//
//            }
//
//            @Override
//            public void onTextChanged(CharSequence s, int start, int before, int count) {
//            }
//
//            @Override
//            public void afterTextChanged(Editable s) {
//
//            }
//        });
//        searchViewLayout.setSearchListener(new SearchViewLayout.SearchListener() {
//            @Override
//            public void onFinished(final String searchKeyword) {
//
//
//
//            }
//        });

        return v;
    }


}
