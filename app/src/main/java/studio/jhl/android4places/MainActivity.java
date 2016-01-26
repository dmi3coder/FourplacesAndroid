package studio.jhl.android4places;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;
import com.etiennelawlor.quickreturn.library.enums.QuickReturnViewType;
import com.etiennelawlor.quickreturn.library.listeners.QuickReturnRecyclerViewOnScrollListener;
import com.malinskiy.superrecyclerview.SuperRecyclerView;
import com.nineoldandroids.animation.Animator;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import io.realm.Realm;
import io.realm.RealmConfiguration;
import studio.jhl.android4places.adapters.CafeAdapter;
import studio.jhl.android4places.backend.CafeLoader;
import studio.jhl.android4places.bean.Cafe;
import studio.jhl.android4places.fragment.SearchFragment;
import xyz.sahildave.widget.SearchViewLayout;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "dmi3debug";
    public static final String API_URL = "http://bookster.hol.es";
    private CafeLoader.CafeType choosedCafeType;
    @Bind(R.id.list) SuperRecyclerView recyclerView;
    @Bind(R.id.search_view)SearchViewLayout searchViewLayout;
    @Bind(R.id.cafeFooter)LinearLayout cafeFooter; // TODO: 1/10/16 make normal footer
    @Bind(R.id.cafeHeader)RelativeLayout cafeHeader;
    @Bind({R.id.header_button_all,
            R.id.header_button_cafe,
            R.id.header_button_night_club,
            R.id.header_button_fun,
            R.id.header_button_more
    })List<LinearLayout> headerButtons;
    @Bind({R.id.choose_button_all,
            R.id.choose_button_cafe,
            R.id.choose_button_night_club,
            R.id.choose_button_fun,
            R.id.choose_button_restaurant,
            R.id.choose_button_fastfood,
            R.id.choose_button_sushi,
            R.id.choose_button_etc
    }) List<Button> chooseButtons;
    @Bind(R.id.chooseLayout) LinearLayout chooseLayout;
    @Bind({
            R.id.choose_button_favourite
    }) ImageButton[] footerButtons;
    CafeLoader fragmentCafeLoader;
    CafeLoader.CafeType fragmentCurrentCafeType = CafeLoader.CafeType.ALL;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        RealmConfiguration config = new RealmConfiguration.Builder(this).build();
        Realm.setDefaultConfiguration(config);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        choosedCafeType = CafeLoader.CafeType.ALL;
        ButterKnife.bind(this);
        chooseLayout.setOnClickListener(null);
        defineRecyclerViewAndLoad(choosedCafeType);
        defineSearchViewLayout();
        defineTypeButtons();

    }



    private void defineRecyclerViewAndLoad(CafeLoader.CafeType choosedCafeType) {
        recyclerView = (SuperRecyclerView)findViewById(R.id.list);

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        QuickReturnRecyclerViewOnScrollListener scrollListener = new QuickReturnRecyclerViewOnScrollListener.Builder(QuickReturnViewType.BOTH)
                .header(cafeHeader)
                .minHeaderTranslation(-60)
                .footer(cafeFooter)
                .minFooterTranslation(60)
                .isSnappable(true)
                .build();

        recyclerView.setOnScrollListener(scrollListener);
        ArrayList<Cafe> debug = new ArrayList<Cafe>();
        for (int i = 0; i < 100; i++) {
            Cafe cafe = new Cafe();
            cafe.setName("test");
            cafe.setId(i);
            debug.add(cafe);
        }

        recyclerView.setAdapter(new CafeAdapter(debug, MainActivity.this.getBaseContext()));
//        new CafeLoader(choosedCafeType).setOnCafesLoadListener(new CafeLoader.OnCafesLoadListener() {
//            @Override
//            public void onEvent(ArrayList<Cafe> cafes) {
//                Log.d(TAG, "onEvent: working");
//                recyclerView.setAdapter(new CafeAdapter(cafes, MainActivity.this.getBaseContext()));
//            }
//
//        });
    }

    private void defineSearchViewLayout() {
        final SearchFragment searchFragment = new SearchFragment(searchViewLayout,this);
        searchViewLayout.setExpandedContentFragment(this, searchFragment);
    }


    private void defineTypeButtons() {
        defineHeaderButtons();
        defineChooseButtons();

    }


    private void defineHeaderButtons() {
        for(int currentButton = 0;currentButton<headerButtons.size()-1;currentButton++){
            final int currentButtonFinal = currentButton;
            headerButtons.get(currentButton).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    defineRecyclerViewAndLoad(CafeLoader.CafeType.values()[currentButtonFinal]);
                }
            });
        }

        defineMoreButton();
    }

    private void defineMoreButton() {
        headerButtons.get(headerButtons.size()-1).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                hideOrShowChooseLayout();
            }
        });
    }

    private void hideOrShowChooseLayout(){
        if(chooseLayout.getVisibility()==View.VISIBLE){
            YoYo.with(Techniques.FadeOut).duration(300)
                    .withListener(new Animator.AnimatorListener() {
                @Override
                public void onAnimationStart(Animator animation) {

                }

                @Override
                public void onAnimationEnd(Animator animation) {
                    chooseLayout.setVisibility(View.GONE);
                }

                @Override
                public void onAnimationCancel(Animator animation) {

                }

                @Override
                public void onAnimationRepeat(Animator animation) {

                }
            }).playOn(chooseLayout);
        }
        else  YoYo.with(Techniques.FadeIn).duration(300)
                .withListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {

                chooseLayout.setVisibility(View.VISIBLE);
            }

            @Override
            public void onAnimationEnd(Animator animation) {
            }

            @Override
            public void onAnimationCancel(Animator animation) {

            }

            @Override
            public void onAnimationRepeat(Animator animation) {

            }
        }).playOn(chooseLayout);
    }

    private void defineChooseButtons() {
        for(int i = 0; i< chooseButtons.size()-1;i++){
            final int finalI = i;
            chooseButtons.get(i).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    defineRecyclerViewAndLoad(CafeLoader.CafeType.values()[finalI]);
                    hideOrShowChooseLayout();
                }
            });
        }
    }


}
