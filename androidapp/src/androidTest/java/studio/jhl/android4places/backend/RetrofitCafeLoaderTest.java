package studio.jhl.android4places.backend;

import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.util.List;

import retrofit2.Call;
import studio.jhl.android4places.backend.type.CafeType;
import studio.jhl.android4places.bean.Cafe;

import static junit.framework.Assert.assertEquals;

/**
 * Created by dmi3coder on 3/7/16 12:00 PM.
 */
public class RetrofitCafeLoaderTest {
    RetrofitCafeLoader cafeLoader;
    @Before
    public void setUp() throws Exception {
         cafeLoader = new RetrofitCafeLoader(CafeType.ALL);
    }

    @Test
    public void testLoading() throws IOException {
        Call<List<Cafe>> cafes = cafeLoader.defineService().cafeList(CafeType.ALL.toOldBackendString());
        Cafe cafe = cafes.execute().body().get(1);

        assertEquals("test",cafe.getName());
    }
}