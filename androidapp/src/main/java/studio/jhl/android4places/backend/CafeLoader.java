package studio.jhl.android4places.backend;

import java.util.ArrayList;

import studio.jhl.android4places.bean.Cafe;
import studio.jhl.android4places.backend.type.CafeType;

/**
 * Created by dmi3coder on 3/5/16 2:50 PM.
 */
public abstract class CafeLoader {
    private OnCafesLoadListener onCafesLoadListener;

        public interface OnCafesLoadListener{
        void onEvent(ArrayList<Cafe> cafes);
    }

    public CafeLoader(CafeType type){
    }

//    public abstract void startLoading();


    public void setOnCafesLoadListener(OnCafesLoadListener listener){
        onCafesLoadListener = listener;
    }

    public void getRestCafesData(ArrayList<Cafe> cafes){
        if (onCafesLoadListener != null)
            onCafesLoadListener.onEvent(cafes);

    }
}
