package goldenbyte.codemonkeys.android4places;

/**
 * Created by dmi3coder on 1/13/16.
 */


import android.os.Build;

import org.json.JSONException;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricGradleTestRunner;
import org.robolectric.annotation.Config;

import goldenbyte.codemonkeys.android4places.backend.MenuLoader;

import static junit.framework.Assert.assertEquals;

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
        try {
            assertEquals("nj", (menuLoader.getMeals(MenuLoader.MealType.DRINK)).get(0).getName());
            assertEquals(122, (menuLoader.getMeals(MenuLoader.MealType.DRINK)).get(0).getPrice());
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

}
