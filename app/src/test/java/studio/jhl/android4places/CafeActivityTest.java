package studio.jhl.android4places;

import android.os.Build;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricGradleTestRunner;
import org.robolectric.annotation.Config;

import static org.junit.Assert.assertTrue;

/**
 * Created by naomi on 1/13/16.
 */

@Config(constants = BuildConfig.class, sdk = Build.VERSION_CODES.LOLLIPOP)
@RunWith(RobolectricGradleTestRunner.class)
public class CafeActivityTest {
    MainActivity mainActivity;
    @Before
    public void setup(){
        mainActivity = Robolectric.setupActivity(MainActivity.class);
    }

    @Test
    public void cafeActivityTest(){
        assertTrue(mainActivity.cafeFooter!=null);
    }
}
