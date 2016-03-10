package studio.jhl.android4places.fragment;

import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.malinskiy.superrecyclerview.SuperRecyclerView;

import java.util.ArrayList;
import java.util.List;

import io.realm.Realm;
import studio.jhl.android4places.MainApplication;
import studio.jhl.android4places.R;
import studio.jhl.android4places.adapters.CafeAdapter;
import studio.jhl.android4places.backend.type.CafeType;
import studio.jhl.android4places.bean.Cafe;
import xyz.sahildave.widget.SearchViewLayout;

public class SearchFragment extends Fragment {
    SuperRecyclerView searchCafeListView;
    CafeType fragmentCurrentCafeType = CafeType.ALL;
    Context context;
    SearchViewLayout searchViewLayout;


    public SearchFragment(SearchViewLayout searchViewLayout, final Context context) {
        this.context = context;
        this.searchViewLayout = searchViewLayout;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_search, container, false);
        searchCafeListView = (SuperRecyclerView)v.findViewById(R.id.searchfragmentlist);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(context);
        searchCafeListView.setLayoutManager(layoutManager);
        final Realm realm = Realm.getInstance(MainApplication.cacheConfig);
        searchViewLayout.setSearchBoxListener(new SearchViewLayout.SearchBoxListener() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                List<Cafe> cafes = new ArrayList<Cafe>();
                cafes = realm.where(Cafe.class).contains("name", s.toString()).findAll();
                searchCafeListView.setAdapter(new CafeAdapter(cafes,context));
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        searchViewLayout.setSearchListener(new SearchViewLayout.SearchListener() {
            @Override
            public void onFinished(final String searchKeyword) {



            }
        });

        return v;
    }


}
