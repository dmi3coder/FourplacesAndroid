package com.dmi3coder.fourplaces.backend;

import java.util.ArrayList;

import com.dmi3coder.fourplaces.backend.type.CafeType;
import com.dmi3coder.fourplaces.cafe.Cafe;

@Deprecated
public abstract class CafeLoader {
    private OnCafesLoadListener onCafesLoadListener;
    protected CafeType currentCafeType;

        public interface OnCafesLoadListener{
        void onEvent(ArrayList<Cafe> cafes);
    }

    public CafeLoader(CafeType choosedCafeType){
        currentCafeType = choosedCafeType;
    }

    public abstract void load();
//    public abstract void startLoading();


    public void setOnCafesLoadListener(OnCafesLoadListener listener){
        onCafesLoadListener = listener;
    }

    public void getRestCafesData(ArrayList<Cafe> cafes){
        if (onCafesLoadListener != null)
            onCafesLoadListener.onEvent(cafes);

    }
}
