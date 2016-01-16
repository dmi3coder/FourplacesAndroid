package studio.jhl.android4places;

/**
 * Created by dmi3coder on 1/13/16.
 */


import android.os.Build;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricGradleTestRunner;
import org.robolectric.annotation.Config;

import studio.jhl.android4places.backend.MenuLoader;

@Config(constants = BuildConfig.class, sdk = Build.VERSION_CODES.LOLLIPOP)
@RunWith(RobolectricGradleTestRunner.class)
public class MenuLoaderTest {
    private MenuLoader menuLoader;

    @Before
    public void setup(){
        menuLoader = new MenuLoader(26);

    }

    @Test
    public void menuLoaderTester(){
    }

}
