package com.dmi3coder.fourplaces.backend;

@Deprecated
public abstract class MenuLoader {
    private OnMenuLoadListener onMenuLoadListener;

    public interface OnMenuLoadListener {
        void onEvent(String result);
    }


    public abstract void load();

    public void setOnMenuLoadListener(OnMenuLoadListener onMenuLoadListener) {
        this.onMenuLoadListener = onMenuLoadListener;
    }

    public void getRestMenuData(String result) {
        if (onMenuLoadListener != null)
            onMenuLoadListener.onEvent(result);
    }

}
