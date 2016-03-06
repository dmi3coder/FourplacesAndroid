package studio.jhl.android4places.adapters;

import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.test.suitebuilder.annotation.SmallTest;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.ArrayList;
import java.util.List;

import studio.jhl.android4places.MainActivity;
import studio.jhl.android4places.bean.Cafe;
import studio.jhl.android4places.bean.ParcelCafe;

import static junit.framework.Assert.assertEquals;

/**
 * Created by dmi3coder on 3/6/16 8:09 PM.
 */
@RunWith(AndroidJUnit4.class)
@SmallTest
public class CafeAdapterTest {
    CafeAdapter adapter;
    List<Cafe> cafes;

    @Rule
    public ActivityTestRule<MainActivity> mainActivity =
            new ActivityTestRule<MainActivity>(MainActivity.class);

    @Before
    public void setUp() throws Exception {
        cafes = new ArrayList<>();
        for (int i = 0; i < 56; i++) {
            ParcelCafe parcelCafe = new ParcelCafe("Cafeteria Name"+i,
                                                    "Banan"+i,
                                                    "small description"+i,
                                                    i+"00",
                                                    "localhost"+i,
                                                    "null",
                                                    i,
                                                    "+380969259602",
                                                    "loh,pidr");

            cafes.add(parcelCafe.toCafe());
        }
        adapter = new CafeAdapter(cafes,mainActivity.getActivity().getApplicationContext());
    }

    @Test
    public void testGetCafeList() throws Exception {
        assertEquals(cafes,adapter.getCafeList());
    }

    @Test
    public void testGetParcelCafe() throws Exception {
        List<ParcelCafe> parcelCafes = adapter.getParcelCafeList();
        List<Cafe> cafes = new ArrayList<>();
        for (ParcelCafe parcelCafe:
             parcelCafes) {
            cafes.add(parcelCafe.toCafe());
        }
        assertEquals(this.cafes.get(1).getName(),cafes.get(1).getName());
    }

    @Test
    public void testOnBindViewHolder() throws Exception {

    }

    @Test
    public void testOnCreateViewHolder() throws Exception {

    }

    @Test
    public void testGetItemCount() throws Exception {

    }
}