package studio.jhl.android4places.backend;

import java.util.ArrayList;

import studio.jhl.android4places.bean.Cafe;
import studio.jhl.android4places.backend.type.CafeType;


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
