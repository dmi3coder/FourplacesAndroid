package goldenbyte.codemonkeys.goldenbyteproject.fragment;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.malinskiy.superrecyclerview.SuperRecyclerView;

import java.util.ArrayList;

import goldenbyte.codemonkeys.goldenbyteproject.MainActivity;
import goldenbyte.codemonkeys.goldenbyteproject.R;
import goldenbyte.codemonkeys.goldenbyteproject.adapters.CafeAdapter;
import goldenbyte.codemonkeys.goldenbyteproject.backend.CafeLoader;
import goldenbyte.codemonkeys.goldenbyteproject.bean.Cafe;
import xyz.sahildave.widget.SearchViewLayout;

public class SearchFragment extends Fragment {
    SuperRecyclerView searchCafeListView;
    CafeLoader.CafeType fragmentCurrentCafeType = CafeLoader.CafeType.ALL;


    public SearchFragment(CafeLoader.CafeType newType) {
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


        return v;
    }


}
