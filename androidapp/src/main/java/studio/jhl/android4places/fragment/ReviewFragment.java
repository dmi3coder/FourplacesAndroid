package studio.jhl.android4places.fragment;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import org.parceler.Parcels;

import studio.jhl.android4places.R;
import studio.jhl.android4places.bean.Cafe;
import studio.jhl.android4places.bean.ParcelCafe;

/**
 * Created by dmi3coder on 3/7/16 9:16 PM.
 */
public class ReviewFragment extends Fragment {
    public static final String TAG = "ReviewFragment";
    public static final String ARG_CAFE = "studio.jhl.android4places.fragment.CAFE";

    public static ReviewFragment newInstance(Cafe cafe) {
        Bundle args = new Bundle();

        args.putParcelable(ARG_CAFE,Parcels.wrap(ParcelCafe.generate(cafe)));
        ReviewFragment fragment = new ReviewFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_reviews,container,false);
        ParcelCafe parcelCafe = Parcels.unwrap(savedInstanceState.getParcelable(ARG_CAFE));

        return view;
    }
}
