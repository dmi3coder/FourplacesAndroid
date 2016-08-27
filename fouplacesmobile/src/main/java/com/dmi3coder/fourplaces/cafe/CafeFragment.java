package com.dmi3coder.fourplaces.cafe;

import android.content.res.Configuration;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.dmi3coder.fourplaces.MainApplication;
import com.dmi3coder.fourplaces.R;
import com.dmi3coder.fourplaces.databinding.FragmentCafeBinding;
import com.etiennelawlor.quickreturn.library.enums.QuickReturnViewType;
import com.etiennelawlor.quickreturn.library.listeners.QuickReturnRecyclerViewOnScrollListener;
import com.kinvey.android.AsyncAppData;
import com.kinvey.android.callback.KinveyListCallback;
import com.malinskiy.superrecyclerview.SuperRecyclerView;

import java.util.Arrays;
import java.util.List;


public class CafeFragment extends Fragment {
    FragmentCafeBinding binding;
    public static final String TAG = "MenuFragment";

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_cafe,container,false);
        defineRecyclerView();
        return binding.getRoot();
    }

    private void defineRecyclerView() {
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext());
        binding.list.setLayoutManager(layoutManager);
        defineStickyHeader();
        AsyncAppData<Cafe> data = MainApplication.client.appData("cafe",Cafe.class);
        data.get(new KinveyListCallback<Cafe>() {
            @Override
            public void onSuccess(Cafe[] cafes) {
                setupAdapter(binding.list,Arrays.asList(cafes));
            }

            @Override
            public void onFailure(Throwable throwable) {
                Log.e(TAG, "onFailure: ",throwable);
            }
        });
    }

    private void defineStickyHeader() {
        if(getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT) {
            binding.list.setOnScrollListener(new QuickReturnRecyclerViewOnScrollListener.Builder(QuickReturnViewType.HEADER)
                    .header(binding.cafeHeader)
                    .minHeaderTranslation(-binding.cafeHeader.getLayoutParams().height)
                    .isSnappable(true)
                    .build());
        }
    }

    private void setupAdapter(SuperRecyclerView recyclerView, List<Cafe> cafes){
        recyclerView.setAdapter(new CafeAdapter(cafes, getContext()));

    }
}
