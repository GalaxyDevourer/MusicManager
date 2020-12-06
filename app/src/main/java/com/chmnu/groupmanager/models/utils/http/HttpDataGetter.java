package com.chmnu.groupmanager.models.utils.http;

import com.chmnu.groupmanager.models.entities.http.UserData;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Scanner;

public class HttpDataGetter implements Runnable {

    private String data;
    private String url;

    public HttpDataGetter(String url) {
        this.url = url + "&token=" + UserData.token;
    }

    public String getData () {
        Thread thread = new Thread(this);
        thread.start();

        try {
            thread.join();
        }
        catch (InterruptedException ex) {
            ex.printStackTrace();
        }

        return data;
    }

    @Override
    public void run() {
        try {
            URL obj = new URL(url);

            HttpURLConnection conn = (HttpURLConnection) obj.openConnection();
            conn.setRequestMethod("GET");
            conn.setRequestProperty("User-Agent", "Mozilla/5.0");
            conn.setRequestProperty("Accept-Charset", "UTF-8");

            InputStream response = conn.getInputStream();
            Scanner s = new Scanner(response).useDelimiter("\\A");

            data = s.hasNext() ? s.next() : "";
        }
        catch (Exception ex) {
            data = ex.getMessage();
            ex.printStackTrace();
        }
    }
}
