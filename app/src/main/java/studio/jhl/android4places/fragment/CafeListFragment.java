package studio.jhl.android4places.fragment;


import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.malinskiy.superrecyclerview.SuperRecyclerView;

import java.util.ArrayList;

import studio.jhl.android4places.R;
import studio.jhl.android4places.adapters.CafeAdapter;
import studio.jhl.android4places.backend.CafeLoader;
import studio.jhl.android4places.bean.Cafe;

public class CafeListFragment extends Fragment {
    SuperRecyclerView recyclerView;
    Context context;


    public CafeListFragment(Context context) {
        this.context = context;
    }

    public void setRecyclerView(CafeAdapter cafeAdapter){
        recyclerView.setAdapter(cafeAdapter);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_cafe_list, container, false);
        recyclerView = (SuperRecyclerView)v.findViewById(R.id.list);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(context);
        recyclerView.setLayoutManager(layoutManager);
        defineRecyclerViewAndLoad(CafeLoader.CafeType.ALL);

        return v;
    }

    public void defineRecyclerViewAndLoad(CafeLoader.CafeType choosedCafeType) {
        recyclerView.showProgress();

        new CafeLoader(choosedCafeType).setOnCafesLoadListener(new CafeLoader.OnCafesLoadListener() {
            @Override
            public void onEvent(ArrayList<Cafe> cafes) {
                recyclerView.setAdapter(new CafeAdapter(cafes, context));
            }

        });
    }


}
