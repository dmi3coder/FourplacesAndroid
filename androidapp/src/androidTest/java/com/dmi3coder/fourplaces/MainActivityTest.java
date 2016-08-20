package com.dmi3coder.fourplaces;

import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.test.suitebuilder.annotation.SmallTest;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static org.junit.Assert.assertNotNull;

/**
 * Created by dmi3coder on 3/6/16 11:19 AM.
 */
@RunWith(AndroidJUnit4.class)
@SmallTest
public class MainActivityTest {

    @Rule
    public ActivityTestRule<MainActivity> mainActivity =
            new ActivityTestRule<MainActivity>(MainActivity.class);


    @Before
    public void setUp() throws Exception {

    }

    @Test
    public void testOnCacheEvent() throws Exception {
        assertNotNull(mainActivity.getActivity().getCafesFromCache());
    }


}