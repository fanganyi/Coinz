package com.example.fanganyi.coinz;

import android.os.AsyncTask;
import android.support.annotation.NonNull;
import android.util.Log;


import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.InetAddress;
import java.net.URL;


public class DownloadFileTask extends AsyncTask<String, Void, String> {
    @Override
    protected String doInBackground(String... urls) {

        try {
            return loadFileFromNetwork(urls[0]);
        } catch (IOException e) {
            return "Unable to load content. Check your network connection"; }
    }

    private String loadFileFromNetwork(String urlString) throws IOException {

        return readStream(downloadUrl(new URL(urlString)));
    }

    // Given a string representation of a URL, sets up a connection and gets an input stream.
    private InputStream downloadUrl(URL url) throws IOException {

        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setReadTimeout(15000); // milliseconds
        conn.setConnectTimeout(15000); // milliseconds
        conn.setRequestMethod("GET");
        conn.setDoInput(true);
        conn.connect();
        return conn.getInputStream();
    }

    @NonNull
    private String readStream(InputStream stream) throws IOException {
        // Read input from stream, build result as a string
        // StandardCharsets.UTF_8.name() > JDK 7
        ByteArrayOutputStream result = new ByteArrayOutputStream();
        byte[] buffer = new byte[1024];
        int length;
        while ((length = stream.read(buffer)) != -1) {
            result.write(buffer, 0, length);
        }
        // StandardCharsets.UTF_8.name() > JDK 7
        return result.toString("UTF-8");
        }


    @Override
    protected void onPostExecute(String result) {
        super.onPostExecute(result);
        DownloadCompleteRunner.downloadComplete(result);

    }

}
