package studio.jhl.android4places.backend;

import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;

import studio.jhl.android4places.backend.type.CafeType;
import studio.jhl.android4places.bean.Cafe;

import static org.junit.Assert.assertEquals;

/**
 * Created by dmi3coder on 3/7/16 10:49 AM.
 */
public class URLCafeLoaderTest {
    CafeLoader cafeloader;
    public static ArrayList<Cafe> cafes;

    @Before
    public void setUp() throws InterruptedException {
        cafeloader = new URLCafeLoader(CafeType.ALL);
        cafeloader.setOnCafesLoadListener(new CafeLoader.OnCafesLoadListener() {
            @Override
            public void onEvent(ArrayList<Cafe> cafes) {
                URLCafeLoaderTest.cafes = cafes;
            }
        });
        cafeloader.load();
        Thread.sleep(5000);
    }

    @Test
    public void testLoad(){
        assertEquals("test",cafes.get(0).getName());
    }


}