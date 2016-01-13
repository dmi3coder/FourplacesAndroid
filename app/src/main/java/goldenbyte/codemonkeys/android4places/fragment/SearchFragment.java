package goldenbyte.codemonkeys.android4places.fragment;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.malinskiy.superrecyclerview.SuperRecyclerView;

import goldenbyte.codemonkeys.android4places.R;
import goldenbyte.codemonkeys.android4places.backend.CafeLoader;

public class SearchFragment extends Fragment {
    SuperRecyclerView searchCafeListView;
    CafeLoader.CafeType fragmentCurrentCafeType = CafeLoader.CafeType.ALL;

    public SearchFragment(CafeLoader.CafeType choosedCafeType) {

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
