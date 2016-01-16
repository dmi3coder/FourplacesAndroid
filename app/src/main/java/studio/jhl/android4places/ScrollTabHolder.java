package studio.jhl.android4places;

import android.widget.AbsListView;

/**
 * Created by naomi on 1/12/16.
 */
public interface ScrollTabHolder {

        void adjustScroll(int scrollHeight);

        void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount, int pagePosition);

}
