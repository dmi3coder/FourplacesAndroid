package goldenbyte.codemonkeys.android4places;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.util.SparseArrayCompat;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.AbsListView;
import android.widget.TextView;

import butterknife.Bind;
import butterknife.ButterKnife;
import goldenbyte.codemonkeys.android4places.backend.MenuLoader;
import goldenbyte.codemonkeys.android4places.backend.MenuParser;
import goldenbyte.codemonkeys.android4places.fragment.ScrollTabHolderFragment;
import goldenbyte.codemonkeys.android4places.views.PagerSlidingTabStrip;


@SuppressLint("NewApi")
public class CafeActivity extends FragmentActivity implements ScrollTabHolder, ViewPager.OnPageChangeListener {



    @Bind(R.id.header) View mHeader;
    @Bind(R.id.info) TextView info;
    @Bind(R.id.tabs) PagerSlidingTabStrip mPagerSlidingTabStrip;
    @Bind(R.id.pager) ViewPager mViewPager;
    private PagerAdapter mPagerAdapter;
    private int minimalHeaderHeight;
    private int headerHeight;
    private int minimalHeaderTranslation;
    private int mLastY;
    public MenuLoader menuLoader;
    private int menu_id;
    private MenuParser menuParser;
    private static String result;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        defineValues();
        setContentView(R.layout.activity_cafe);
        ButterKnife.bind(this);
        new MenuLoader(menu_id).setOnMenuLoadListener(new MenuLoader.OnMenuLoadListener() {
            @Override
            public void onEvent(String result) {
                CafeActivity.result = result;
                mViewPager.setAdapter(mPagerAdapter);
                mPagerSlidingTabStrip.setViewPager(mViewPager);
                mPagerSlidingTabStrip.setOnPageChangeListener(CafeActivity.this);
                mLastY = 0;
            }
        });
        mViewPager.setOffscreenPageLimit(4);

        mPagerAdapter = new PagerAdapter(getSupportFragmentManager());
        mPagerAdapter.setTabHolderScrollingContent(this);


    }

    private void defineValues() {
        menu_id = getIntent().getIntExtra("menu_id", 0);
        menuLoader = new MenuLoader(menu_id);
        minimalHeaderHeight = getResources().getDimensionPixelSize(R.dimen.min_header_height);
        headerHeight = getResources().getDimensionPixelSize(R.dimen.header_height);
        minimalHeaderTranslation = -minimalHeaderHeight;
    }

    @Override
    public void onPageScrollStateChanged(int arg0) {
        // nothing
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

        }
    }

    @Override
    public void onPageSelected(int position) {
        SparseArrayCompat<ScrollTabHolder> scrollTabHolders = mPagerAdapter.getScrollTabHolders();
        ScrollTabHolder currentHolder = scrollTabHolders.valueAt(position);

            currentHolder.adjustScroll((int) (mHeader.getHeight() +mHeader.getTranslationY()));
    }

    @Override
    public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount, int pagePosition) {
        if (mViewPager.getCurrentItem() == pagePosition) {
            int scrollY = getScrollY(view);
                mHeader.setTranslationY(Math.max(-scrollY, minimalHeaderTranslation));
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
            headerHeight = this.headerHeight;
        }

        return -top + firstVisiblePosition * c.getHeight() + headerHeight;
    }

    public static float clamp(float value, float max, float min) {
        return Math.max(Math.min(value, min), max);
    }

    public class PagerAdapter extends FragmentPagerAdapter {

        private SparseArrayCompat<ScrollTabHolder> mScrollTabHolders;
        private final String[] TITLES = MenuLoader.MealType.backendRuTypes;
        private ScrollTabHolder mListener;

        public PagerAdapter(FragmentManager fm) {
            super(fm);
            mScrollTabHolders = new SparseArrayCompat<ScrollTabHolder>();
        }

        public void setTabHolderScrollingContent(ScrollTabHolder listener) {
            mListener = listener;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return TITLES[position];
        }

        @Override
        public int getCount() {
            return TITLES.length;
        }

        @Override
        public Fragment getItem(int position) {
            ScrollTabHolderFragment fragment = (ScrollTabHolderFragment) SampleListFragment.newInstance(position,result);

            mScrollTabHolders.put(position, fragment);
            if (mListener != null) {
                fragment.setScrollTabHolder(mListener);
            }

            return fragment;
        }

        public SparseArrayCompat<ScrollTabHolder> getScrollTabHolders() {
            return mScrollTabHolders;
        }

    }
}