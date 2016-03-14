package studio.jhl.android4places;

import android.annotation.SuppressLint;
import android.app.Fragment;
import android.app.FragmentTransaction;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.widget.FrameLayout;

import studio.jhl.android4places.fragment.CafeFragment;
import studio.jhl.android4places.fragment.ReviewFragment;


@SuppressLint("NewApi")
public class CafeActivity extends FragmentActivity {


    public static final boolean NEEDS_PROXY = Integer.valueOf(Build.VERSION.SDK_INT).intValue() < 11;
    public static String result;
    private FrameLayout frame;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        frame = new FrameLayout(this);
        frame.setTag(android.R.id.content);
        setContentView(frame, new FrameLayout.LayoutParams(
                FrameLayout.LayoutParams.MATCH_PARENT,
                FrameLayout.LayoutParams.MATCH_PARENT));
        fillWithMenu(savedInstanceState);
    }

    protected void fillWithMenu(Bundle savedInstanceState){
        CafeFragment cafeFragment;
        cafeFragment = CafeFragment.newInstance(savedInstanceState);
        replaceFragment(cafeFragment);
    }


    protected void replaceFragment(Fragment fragment){
        FragmentTransaction ft = getFragmentManager().beginTransaction();
        ft.replace(android.R.id.content, fragment).commit();
    }

    public void onHeaderMenuClick(View view) {

    }

    public void onHeaderReviewsClick(View view) {
    }
}