package studio.jhl.android4places;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.util.SparseArrayCompat;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.AbsListView;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

import butterknife.Bind;
import butterknife.ButterKnife;
import studio.jhl.android4places.backend.MenuLoader;
import studio.jhl.android4places.backend.MenuParser;
import studio.jhl.android4places.fragment.SampleListFragment;
import studio.jhl.android4places.fragment.ScrollTabHolderFragment;
import studio.jhl.android4places.views.PagerSlidingTabStrip;


@SuppressLint("NewApi")
public class CafeActivity extends FragmentActivity implements ScrollTabHolder, ViewPager.OnPageChangeListener {



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
    public MenuLoader menuLoader;
    private int menu_id;
    private MenuParser menuParser;
    public static String result;
    @Bind(R.id.progressBar) ProgressBar progressBar;
    @Bind(R.id.cafeImage) ImageView cafeImage;
    @Bind(R.id.cafeName) TextView cafeName;
    @Bind(R.id.header_text_telephone) TextView callView;
    @Bind(R.id.header_text_map)TextView mapView;

  @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cafe);

      mMinHeaderHeight = getResources().getDimensionPixelSize(R.dimen.min_header_height);
        mHeaderHeight = getResources().getDimensionPixelSize(R.dimen.header_height);
        mMinHeaderTranslation = -mMinHeaderHeight;

        mHeader = findViewById(R.id.header);
        info = (TextView) findViewById(R.id.info);
        ButterKnife.bind(this);
        defineHeader();
        mPagerSlidingTabStrip = (PagerSlidingTabStrip) findViewById(R.id.tabs);
        mViewPager = (ViewPager) findViewById(R.id.pager);
        mViewPager.setOffscreenPageLimit(3);

        mPagerAdapter = new PagerAdapter(getSupportFragmentManager());
        mPagerAdapter.setTabHolderScrollingContent(this);

      menuLoader = new MenuLoader(menu_id);
        menuLoader.setOnMenuLoadListener(new MenuLoader.OnMenuLoadListener() {
            @Override
            public void onEvent(String result) {
                progressBar.setVisibility(View.GONE);
                CafeActivity.result = result;
                mViewPager.setAdapter(mPagerAdapter);
                mPagerSlidingTabStrip.setViewPager(mViewPager);
                mPagerSlidingTabStrip.setOnPageChangeListener(CafeActivity.this);
                mLastY=0;
            }
        });
    }

    private void defineHeader() {
        menu_id = getIntent().getIntExtra("menu_id", 0);
        cafeName.setText(getIntent().getStringExtra("cafe_name"));
        Glide.with(this).load("http://cs631720.vk.me/v631720218/d34b/4Bfp-txaiTE.jpg").asBitmap().into(cafeImage);
        defineCallAction();
        defineMapAction();
    }

    private void defineCallAction() {
        callView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_DIAL);
                intent.setData(Uri.parse("tel:"+getIntent().getStringExtra("cafe_telephone")));
                startIntent(intent);
            }
        });
    }
    private void defineMapAction(){
        mapView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_VIEW);
                intent.setData(Uri.parse("geo:0,0?q=34.99,-106.61("+getIntent().getStringExtra("cafe_name")+")"));// TODO: 1/26/16 add normal data from intent form MainActivity
                startIntent(intent);
            }
        });
    }

    private void startIntent(Intent intent) {
        if(intent.resolveActivity(getPackageManager())!=null){
            startActivity(intent);
        }
        else Toast.makeText(this,getResources().getString(R.string.noapp),Toast.LENGTH_SHORT).show();
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
            ScrollTabHolderFragment fragment = (ScrollTabHolderFragment) SampleListFragment.newInstance(position);

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