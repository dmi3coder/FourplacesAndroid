package goldenbyte.codemonkeys.android4places.fragment;

import android.support.v4.app.Fragment;

import goldenbyte.codemonkeys.android4places.ScrollTabHolder;

/**
 * Created by naomi on 1/12/16.
 */
public abstract class ScrollTabHolderFragment extends Fragment implements ScrollTabHolder {

    protected ScrollTabHolder mScrollTabHolder;

    public void setScrollTabHolder(ScrollTabHolder scrollTabHolder) {
        mScrollTabHolder = scrollTabHolder;
    }
}