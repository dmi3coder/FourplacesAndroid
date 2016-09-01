package com.dmi3coder.fourplaces.cafe;

import android.content.res.Configuration;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Toast;

import com.dmi3coder.fourplaces.MainActivity;
import com.dmi3coder.fourplaces.MainApplication;
import com.dmi3coder.fourplaces.R;
import com.dmi3coder.fourplaces.databinding.FragmentCafeBinding;
import com.etiennelawlor.quickreturn.library.enums.QuickReturnViewType;
import com.etiennelawlor.quickreturn.library.listeners.QuickReturnRecyclerViewOnScrollListener;
import com.kinvey.android.AsyncAppData;
import com.kinvey.android.callback.KinveyListCallback;
import com.kinvey.java.Query;
import com.malinskiy.superrecyclerview.OnMoreListener;
import com.malinskiy.superrecyclerview.SuperRecyclerView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


public class CafeFragment extends Fragment implements KinveyListCallback<Cafe>, View.OnClickListener, AdapterView.OnItemClickListener {
    FragmentCafeBinding binding;
    public static final String TAG = "MenuFragment";
    private int skip = 0;
    Query query;
    AsyncAppData<Cafe> data;
    List<Cafe> cafes = new ArrayList<>();
    CafeAdapter adapter;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_cafe,container,false);
        query = MainApplication.getClient().query();
        query.setLimit(50);
        defineRecyclerView();
        return binding.getRoot();
    }

    private void defineRecyclerView() {
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext());
        binding.list.setLayoutManager(layoutManager);
        defineStickyHeader();
        data = MainApplication.client.appData("cafe",Cafe.class);
        if(MainApplication.isNetworkAvailable()){
            data.get(query,this);
            binding.cafeHeaderMoreButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    ((MainActivity) getActivity()).openDrawer();
                }
            });
            binding.list.setOnMoreListener(new OnMoreListener() {
                @Override
                public void onMoreAsked(int overallItemsCount, int itemsBeforeMore, int maxLastVisiblePosition) {
                    skip += 50;
                    query.setSkip(skip);
                    data.get(query,CafeFragment.this);
                    Log.d(TAG, "onMoreAsked: "+overallItemsCount);
                }
            });
        }
        else
            Toast.makeText(getActivity(), R.string.nointernet, Toast.LENGTH_SHORT).show();
    }

    private void defineStickyHeader() {
        if(getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT) {
            binding.list.setOnScrollListener(new QuickReturnRecyclerViewOnScrollListener.Builder(QuickReturnViewType.HEADER)
                    .header(binding.cafeHeader)
                    .minHeaderTranslation(-binding.cafeHeader.getLayoutParams().height)
                    .isSnappable(true)
                    .build());
        }
        binding.cafeHeaderCafeButton.setOnClickListener(this);
        binding.cafeHeaderAllButton.setOnClickListener(this);
        binding.cafeHeaderFunButton.setOnClickListener(this);
        binding.cafeHeaderNightClubButton.setOnClickListener(this);
    }

    private void setupAdapter(SuperRecyclerView recyclerView, List<Cafe> cafes){
        adapter = new CafeAdapter(cafes, getContext());
        recyclerView.setAdapter(adapter);

    }

    @Override
    public void onSuccess(Cafe[] loadedCafes) {
        int previousCafesSize = cafes.size();
        for (Cafe c:
                loadedCafes) {
            cafes.add(c);
        }
        if(previousCafesSize == 0)
            setupAdapter(binding.list,cafes);
        else {
            adapter.notifyDataSetChanged();
            binding.list.hideMoreProgress();
        }
    }

    @Override
    public void onFailure(Throwable throwable) {
        Log.e(TAG, "onFailure: ", throwable);
    }

    @Override
    public void onClick(View view) {
        if(!MainApplication.isNetworkAvailable()){
            Toast.makeText(getActivity(), R.string.nointernet, Toast.LENGTH_SHORT).show();
            return;
        }
        query = MainApplication.getClient().query();
        query.setLimit(50);
        String queryName = "";
        switch (view.getId()){
            default:
                break;
            case R.id.cafe_header_cafe_button:
                queryName = NavigationAdapter.NavigationItem.COFFEE.getName();
                break;
            case R.id.cafe_header_fun_button:
                queryName = NavigationAdapter.NavigationItem.FUN.getName();
                break;
            case R.id.cafe_header_night_club_button:
                queryName = NavigationAdapter.NavigationItem.NIGHTLIFE.getName();
                break;
        }
        skip = 0;
        cafes.removeAll(cafes);
        if(!queryName.isEmpty())
            query.equals("type",queryName);
        data.get(query,this);
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        if(!MainApplication.isNetworkAvailable()){
            Toast.makeText(getActivity(), R.string.nointernet, Toast.LENGTH_SHORT).show();
            return;
        }
        query = MainApplication.getClient().query();
        query.setLimit(50);
        skip = 0;
        cafes.removeAll(cafes);
        if(i<8)
            query.equals("type", NavigationAdapter.NavigationItem.values()[i].getName());
        else{
            // TODO: 9/1/16
        }
        data.get(query,CafeFragment.this);
    }
}
