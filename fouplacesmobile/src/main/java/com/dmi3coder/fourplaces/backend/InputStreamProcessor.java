package com.dmi3coder.fourplaces.backend;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

@Deprecated
public class InputStreamProcessor {
    private static String url;

    public InputStreamProcessor(String url) {
        this.url = url;
    }

    public static String GET() throws IOException {
        InputStream inputStream = null;
        String result = "";
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder().url(url).build();
        Response response = client.newCall(request).execute();
        inputStream = response.body().byteStream();
        if(inputStream!= null)
            result = convertInputStreamToString(inputStream);
        else
            result = "Did not work!";
        return result;
    }

    private static String convertInputStreamToString(InputStream inputStream) throws IOException {
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
        String line = "";
        String result = "";
        while ((line = bufferedReader.readLine()) != null)
            result += line;
        inputStream.close();
        return result;
    }
}
