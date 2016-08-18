package studio.jhl.android4places.backend.url;

import org.json.JSONException;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;

import studio.jhl.android4places.TestConstants;
import studio.jhl.android4places.backend.CafeLoader;
import studio.jhl.android4places.backend.type.CafeType;
import studio.jhl.android4places.cafe.Cafe;

import static org.junit.Assert.assertEquals;

/**
 * Created by dmi3coder on 3/7/16 10:49 AM.
 */
public class URLCafeLoaderTest {
    CafeLoader cafeloader;
    public static ArrayList<Cafe> cafes;

    @Before
    public void setUp(){
        cafeloader = new URLCafeLoader(CafeType.ALL);
    }

    @Test
    public void testLoading() throws InterruptedException {
        cafeloader.setOnCafesLoadListener(new CafeLoader.OnCafesLoadListener() {
            @Override
            public void onEvent(ArrayList<Cafe> cafes) {
                URLCafeLoaderTest.cafes = cafes;
            }
        });
        cafeloader.load();
        Thread.sleep(5000);
        assertEquals("test",cafes.get(0).getName());
    }

    @Test
    public void testParsing() throws JSONException {
        ArrayList<Cafe> cafes = ((URLCafeLoader)cafeloader).parseJsonArray(TestConstants.JSON);
        assertEquals("test",cafes.get(0).getName());

    }




}