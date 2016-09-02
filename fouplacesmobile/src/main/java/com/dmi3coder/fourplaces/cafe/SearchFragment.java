package com.dmi3coder.fourplaces.cafe;

import android.app.Fragment;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.dmi3coder.fourplaces.MainActivity;
import com.dmi3coder.fourplaces.MainApplication;
import com.dmi3coder.fourplaces.R;
import com.kinvey.android.AsyncAppData;
import com.kinvey.android.callback.KinveyListCallback;
import com.kinvey.java.Query;
import com.kinvey.java.query.AbstractQuery;
import com.malinskiy.superrecyclerview.SuperRecyclerView;

import java.util.Arrays;

import xyz.sahildave.widget.SearchViewLayout;

public class SearchFragment extends Fragment {
    SuperRecyclerView searchCafeListView;
    SearchViewLayout searchViewLayout;

    public SearchFragment() {
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        this.searchViewLayout = ((MainActivity)getActivity()).getSearchViewLayout();
        View v = inflater.inflate(R.layout.fragment_search, container, false);
        searchCafeListView = (SuperRecyclerView)v.findViewById(R.id.searchfragmentlist);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity().getApplicationContext());
        searchCafeListView.setLayoutManager(layoutManager);

        searchViewLayout.setSearchBoxListener(new SearchViewLayout.SearchBoxListener() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                AsyncAppData<Cafe> cafeAppData = MainApplication.getClient().appData("cafe",Cafe.class);
                Query query = MainApplication.getClient().query();
                query.regEx("name","^"+s.toString());
                cafeAppData.get(query, new KinveyListCallback<Cafe>() {
                    @Override
                    public void onSuccess(Cafe[] cafes) {
                        searchCafeListView.setAdapter(new CafeAdapter(Arrays.asList(cafes),getActivity()));
                    }

                    @Override
                    public void onFailure(Throwable throwable) {

                    }
                });

            }
        });
        searchViewLayout.setSearchListener(new SearchViewLayout.SearchListener() {
            @Override
            public void onFinished(final String searchKeyword) {



            }
        });

        return v;
    }


}
