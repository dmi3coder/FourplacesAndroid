package studio.jhl.android4places.fragment;

import android.app.Fragment;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentActivity;
import android.support.v4.util.SparseArrayCompat;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

import org.parceler.Parcels;

import butterknife.Bind;
import butterknife.ButterKnife;
import studio.jhl.android4places.CafeActivity;
import studio.jhl.android4places.R;
import studio.jhl.android4places.ScrollTabHolder;
import studio.jhl.android4places.adapters.PagerAdapter;
import studio.jhl.android4places.backend.URLMenuLoader;
import studio.jhl.android4places.backend.MenuParser;
import studio.jhl.android4places.bean.Cafe;
import studio.jhl.android4places.views.PagerSlidingTabStrip;


/**
 * Created by dmi3coder on 3/5/16 11:46 AM.
 */

public class CafeFragment extends Fragment implements ScrollTabHolder, ViewPager.OnPageChangeListener{
    public static final boolean NEEDS_PROXY = Integer.valueOf(Build.VERSION.SDK_INT).intValue() < 11;
    private View mHeader;
    private PagerSlidingTabStrip mPagerSlidingTabStrip;
    private ViewPager mViewPager;
    private PagerAdapter mPagerAdapter;
    private int mMinHeaderHeight;
    private int mHeaderHeight;
    private int mMinHeaderTranslation;
    private TextView info;
    private int mLastY;
    public URLMenuLoader menuLoader;
    private int menu_id;
    private MenuParser menuParser;
    public static String result;
    private static Cafe cafe;

    @Bind(R.id.progressBar) ProgressBar progressBar;
    @Bind(R.id.cafeImage) ImageView cafeImage;
    @Bind(R.id.cafeName) TextView cafeName;
    @Bind(R.id.header_text_telephone) TextView callView;
    @Bind(R.id.header_text_map)TextView mapView;


    public static final CafeFragment newInstance(Bundle savedInstanceState){
        CafeFragment fragment = new CafeFragment();
        fragment.setArguments(savedInstanceState);
        return fragment;

    }
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.activity_cafe,container,false);
        cafe = Parcels.unwrap(getActivity().getIntent().getParcelableExtra("currentCafe"));
        mMinHeaderHeight = getResources().getDimensionPixelSize(R.dimen.min_header_height);
        mHeaderHeight = getResources().getDimensionPixelSize(R.dimen.header_height);
        mMinHeaderTranslation = -mMinHeaderHeight;

        mHeader = root.findViewById(R.id.header);
        info = (TextView) root.findViewById(R.id.info);
        ButterKnife.bind(this,root);
        defineHeader();
        mPagerSlidingTabStrip = (PagerSlidingTabStrip) root.findViewById(R.id.tabs);
        mViewPager = (ViewPager) root.findViewById(R.id.pager);
        mViewPager.setOffscreenPageLimit(3);

        mPagerAdapter = new PagerAdapter(((FragmentActivity)getActivity()).getSupportFragmentManager());
        mPagerAdapter.setTabHolderScrollingContent(this);
        menuLoader = new URLMenuLoader(menu_id);
        menuLoader.setOnMenuLoadListener(new URLMenuLoader.OnMenuLoadListener() {
            @Override
            public void onEvent(String result) {
                progressBar.setVisibility(View.GONE);
                CafeActivity.result = result;
                mViewPager.setAdapter(mPagerAdapter);
                mPagerSlidingTabStrip.setViewPager(mViewPager);
                mPagerSlidingTabStrip.setOnPageChangeListener(CafeFragment.this);
                mLastY=0;
            }
        });
        menuLoader.load();
        return root;
    }

    private void defineHeader() {
        menu_id = cafe.getId();
        cafeName.setText(cafe.getName());
        Glide.with(this).load(cafe.getImageUrl()).asBitmap().into(cafeImage);
        defineCallAction();
        defineMapAction();
    }
    private void defineCallAction() {
        callView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_DIAL);
                intent.setData(Uri.parse("tel:"+cafe.getPhoneNumber()));
                startIntent(intent);
            }
        });
    }
    private void defineMapAction(){
        mapView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_VIEW);
                intent.setData(Uri.parse("geo:0,0?q="+cafe.getLat()+","+cafe.getLng()+"("+cafe.getName()+")"));
                startIntent(intent);
            }
        });
    }
    private void startIntent(Intent intent) {
        if(intent.resolveActivity(getActivity().getPackageManager())!=null){
            startActivity(intent);
        }
        else Toast.makeText(getActivity(),getResources().getString(R.string.noapp),Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

        if (positionOffsetPixels > 0) {
            int currentItem = mViewPager.getCurrentItem();

            SparseArrayCompat<ScrollTabHolder> scrollTabHolders = mPagerAdapter.getScrollTabHolders();
            ScrollTabHolder currentHolder;

            if (position < currentItem) {
                currentHolder = scrollTabHolders.valueAt(position);
            } else {
                currentHolder = scrollTabHolders.valueAt(position + 1);
            }
            if (NEEDS_PROXY) {
                // TODO is not good
                currentHolder.adjustScroll(mHeader.getHeight() - mLastY);
                mHeader.postInvalidate();
            } else {
                currentHolder.adjustScroll((int) (mHeader.getHeight() + mHeader.getTranslationY()));
            }
        }
    }

    @Override
    public void onPageSelected(int position) {
        SparseArrayCompat<ScrollTabHolder> scrollTabHolders = mPagerAdapter.getScrollTabHolders();
        ScrollTabHolder currentHolder = scrollTabHolders.valueAt(position);
        if(NEEDS_PROXY){
            //TODO is not good
            currentHolder.adjustScroll(mHeader.getHeight()-mLastY);
            mHeader.postInvalidate();
        }else{
            currentHolder.adjustScroll((int) (mHeader.getHeight() +mHeader.getTranslationY()));
        }
    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }

    @Override
    public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount, int pagePosition) {
        if (mViewPager.getCurrentItem() == pagePosition) {
            int scrollY = getScrollY(view);
            if(NEEDS_PROXY){
                //TODO is not good
                mLastY=-Math.max(-scrollY, mMinHeaderTranslation);
                info.setText(String.valueOf(scrollY));
                mHeader.scrollTo(0, mLastY);
                mHeader.postInvalidate();
            }else{
                mHeader.setTranslationY(Math.max(-scrollY, mMinHeaderTranslation));
            }
        }
    }

    @Override
    public void adjustScroll(int scrollHeight) {
        // nothing
    }

    public int getScrollY(AbsListView view) {
        View c = view.getChildAt(0);
        if (c == null) {
            return 0;
        }

        int firstVisiblePosition = view.getFirstVisiblePosition();
        int top = c.getTop();

        int headerHeight = 0;
        if (firstVisiblePosition >= 1) {
            headerHeight = mHeaderHeight;
        }

        return -top + firstVisiblePosition * c.getHeight() + headerHeight;
    }

    public static float clamp(float value, float max, float min) {
        return Math.max(Math.min(value, min), max);
    }
}
