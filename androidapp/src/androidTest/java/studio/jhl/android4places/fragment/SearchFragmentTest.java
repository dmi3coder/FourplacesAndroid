package studio.jhl.android4places.fragment;

import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import org.junit.Before;
import org.junit.Rule;
import org.junit.runner.RunWith;

import studio.jhl.android4places.MainActivity;
import xyz.sahildave.widget.SearchViewLayout;

import static org.junit.Assert.*;

/**
 * Created by naomi on 3/10/16.
 */
@RunWith(AndroidJUnit4.class)
public class SearchFragmentTest {

    @Rule
    ActivityTestRule<MainActivity> activityTestRule = new ActivityTestRule<MainActivity>(MainActivity.class);

    @Before
    public void setUp() throws Exception {
        SearchViewLayout layout = new SearchViewLayout(activityTestRule.getActivity().getApplicationContext(),null);
    }
}