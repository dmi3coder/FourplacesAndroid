package studio.jhl.android4places.backend;

import org.json.JSONException;
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
    String json = "{\"data\":[{\"id\":\"1\",\"type\":\"Ресторан\",\"name\":\"test\",\"description\":\"test\",\"adress\":\"проспект Соборний, 164, Запоріжжя, Запорізька область, Украина\",\"work_time\":\"test\",\"img_path\":\"77363.png\",\"telephone\":\"38067254234234\",\"lat\":\"47.8388\",\"lng\":\"35.13956699999994\"},{\"id\":\"2\",\"type\":\"Суши бар\",\"name\":\"test2\",\"description\":\"test2\",\"adress\":\"ул. Урицкого, Балабине, Запорізька область, Украина\",\"work_time\":\"test2\",\"img_path\":\"22354.jpg\",\"telephone\":\"38067254234234\",\"lat\":\"47.7570754\",\"lng\":\"35.20857969999997\"},{\"id\":\"3\",\"type\":\"Фаст фуд\",\"name\":\"test3\",\"description\":\"test3\",\"adress\":\"test3\",\"work_time\":\"test3\",\"img_path\":\"85952.jpg\",\"telephone\":\"38067254234234\",\"lat\":\"\",\"lng\":\"\"},{\"id\":\"4\",\"type\":\"Суши бар\",\"name\":\"test4\",\"description\":\"test4\",\"adress\":\"проспект Леніна, 164, Запоріжжя, Запорізька область, Украина\",\"work_time\":\"test4\",\"img_path\":\"75668.jpg\",\"telephone\":\"38067254234234\",\"lat\":\"47.8388\",\"lng\":\"35.13956699999994\"},{\"id\":\"5\",\"type\":\"Суши бар\",\"name\":\"test5\",\"description\":\"test5\",\"adress\":\"ул. Урицкого, Балабине, Запорізька область, Украина\",\"work_time\":\"test5\",\"img_path\":\"37297.jpg\",\"telephone\":\"38067254234234\",\"lat\":\"47.7570754\",\"lng\":\"35.20857969999997\"},{\"id\":\"6\",\"type\":\"Суши бар\",\"name\":\"test6\",\"description\":\"test6\",\"adress\":\"ул. Урицкого, Балабине, Запорізька область, Украина\",\"work_time\":\"test6\",\"img_path\":\"39960.jpg\",\"telephone\":\"38067254234234\",\"lat\":\"47.7570754\",\"lng\":\"35.20857969999997\"},{\"id\":\"7\",\"type\":\"Суши бар\",\"name\":\"test7\",\"description\":\"test7\",\"adress\":\"ул. Опытная станция, 3А, Запоріжжя, Запорізька область, Украина, 69000\",\"work_time\":\"test7\",\"img_path\":\"32577.jpg\",\"telephone\":\"38067254234234\",\"lat\":\"47.769121999999996\",\"lng\":\"35.209521300000006\"},{\"id\":\"8\",\"type\":\"Суши бар\",\"name\":\"test8\",\"description\":\"test8\",\"adress\":\"ул. Опытная станция, 3А, Запоріжжя, Запорізька область, Украина, 69000\",\"work_time\":\"test8\",\"img_path\":\"61549.jpg\",\"telephone\":\"38067254234234\",\"lat\":\"47.769121999999996\",\"lng\":\"35.209521300000006\"},{\"id\":\"9\",\"type\":\"Суши бар\",\"name\":\"test9\",\"description\":\"test9\",\"adress\":\"\",\"work_time\":\"test9\",\"img_path\":\"54699.jpg\",\"telephone\":\"38067254234234\",\"lat\":\"\",\"lng\":\"\"},{\"id\":\"10\",\"type\":\"Ресторан\",\"name\":\"test10\",\"description\":\"test10\",\"adress\":\"проспект Леніна, 164, Запоріжжя, Запорізька область, Украина\",\"work_time\":\"test10\",\"img_path\":\"44948.jpg\",\"telephone\":\"38067254234234\",\"lat\":\"47.8388\",\"lng\":\"35.13956699999994\"},{\"id\":\"11\",\"type\":\"Ресторан\",\"name\":\"test11\",\"description\":\"test11\",\"adress\":\"test11\",\"work_time\":\"test11\",\"img_path\":\"25104.jpg\",\"telephone\":\"38067254234234\",\"lat\":\"\",\"lng\":\"\"},{\"id\":\"12\",\"type\":\"Ресторан\",\"name\":\"test12\",\"description\":\"test12\",\"adress\":\"test12\",\"work_time\":\"test12\",\"img_path\":\"37299.jpg\",\"telephone\":\"38067254234234\",\"lat\":\"\",\"lng\":\"\"},{\"id\":\"13\",\"type\":\"Суши бар\",\"name\":\"newtestregistration\",\"description\":\"newtestregistration\",\"adress\":\"newtestregistration\",\"work_time\":\"newtestregistration\",\"img_path\":\"\",\"telephone\":\"38067254234234\",\"lat\":\"\",\"lng\":\"\"},{\"id\":\"14\",\"type\":\"Суши бар\",\"name\":\"newtestreg\",\"description\":\"newtestreg\",\"adress\":\"\",\"work_time\":\"newtestreg\",\"img_path\":\"\",\"telephone\":\"380677252946\",\"lat\":\"\",\"lng\":\"\"},{\"id\":\"15\",\"type\":\"Ресторан\",\"name\":\"Chicago Blues\",\"description\":\"Вкусная и сытная кухня, выдержанный в каждой детали интерьер, непринужденная обстановка заведения, которая притягивает в стейк-паб \\\"Чикаго Блюз\\\" успешных и приятных людей, непременно понравится и вам.\",\"adress\":\"Charivna St, 131, Zaporizhzhia, Zaporiz'ka oblast, Ukraine\",\"work_time\":\"24\\/7\",\"img_path\":\"79620.gif\",\"telephone\":\"(095)6706226\",\"lat\":\"47.839378599999996\",\"lng\":\"35.21949659999996\"}]}";

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
        ArrayList<Cafe> cafes = ((URLCafeLoader)cafeloader).parseJsonArray(json);
        assertEquals("test",cafes.get(0).getName());

    }




}