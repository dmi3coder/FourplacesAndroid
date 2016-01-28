package studio.jhl.android4places.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import studio.jhl.android4places.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class CafeListFragment extends Fragment {


    public CafeListFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_cafe_list, container, false);


        return v;
    }

}
