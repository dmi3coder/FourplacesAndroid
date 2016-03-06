package studio.jhl.android4places;

import android.annotation.SuppressLint;
import android.app.FragmentTransaction;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.widget.FrameLayout;

import studio.jhl.android4places.fragment.CafeFragment;


@SuppressLint("NewApi")
public class CafeActivity extends FragmentActivity {

    public static final boolean NEEDS_PROXY = Integer.valueOf(Build.VERSION.SDK_INT).intValue() < 11;
    public static String result;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FrameLayout frame = new FrameLayout(this);
        frame.setTag(android.R.id.content);
        setContentView(frame, new FrameLayout.LayoutParams(
                FrameLayout.LayoutParams.MATCH_PARENT,
                FrameLayout.LayoutParams.MATCH_PARENT));
            FragmentTransaction ft = getFragmentManager().beginTransaction();
            ft.replace(android.R.id.content, CafeFragment.newInstance(savedInstanceState)).commit();
    }
}