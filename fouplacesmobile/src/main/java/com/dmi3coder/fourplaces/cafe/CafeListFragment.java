package com.dmi3coder.fourplaces.cafe;


import android.app.Fragment;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.malinskiy.superrecyclerview.SuperRecyclerView;

import java.util.ArrayList;

import com.dmi3coder.fourplaces.R;
import com.dmi3coder.fourplaces.backend.CafeLoader;
import com.dmi3coder.fourplaces.backend.type.CafeType;
import com.dmi3coder.fourplaces.backend.url.URLCafeLoader;

public class CafeListFragment extends Fragment {
    SuperRecyclerView recyclerView;



    public void setRecyclerView(CafeAdapter cafeAdapter){
        recyclerView.setAdapter(cafeAdapter);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_cafe_list, container, false);
        recyclerView = (SuperRecyclerView)v.findViewById(R.id.list);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity().getApplicationContext());
        recyclerView.setLayoutManager(layoutManager);
        defineRecyclerViewAndLoad(CafeType.ALL);
        return v;
    }

    public void defineRecyclerViewAndLoad(CafeType choosedCafeType) {
        recyclerView.showProgress();

        new URLCafeLoader(choosedCafeType).setOnCafesLoadListener(new CafeLoader.OnCafesLoadListener() {
            @Override
            public void onEvent(ArrayList<Cafe> cafes) {
                recyclerView.setAdapter(new CafeAdapter(cafes, getActivity().getApplicationContext()));
            }

        });
    }


}
