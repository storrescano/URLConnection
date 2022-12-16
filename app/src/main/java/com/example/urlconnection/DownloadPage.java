package com.example.urlconnection;

import android.annotation.SuppressLint;
import android.os.AsyncTask;
import android.widget.TextView;


import org.jsoup.Jsoup;
import org.w3c.dom.Document;

import java.io.IOException;


public class DownloadPage extends AsyncTask {

    @SuppressLint("StaticFieldLeak")
    TextView result;
    public static String contentAsString = "";

    public String getContentAsString() {
        return contentAsString;
    }

    public void setContentAsString(String contentAsString) {
        this.contentAsString = contentAsString;
    }

    @Override
    protected void onPostExecute(Object o) {
        super.onPostExecute(o);
    }

    @Override
    protected Object doInBackground(Object[] objects) {
        try {
            return downloadUrl((String) objects[0]);
        } catch (IOException e) {
            return "Unable to retrieve web page. URL may be invalid.";
        }

    }
    private String downloadUrl(String myurl) throws IOException {
        Document doc = (Document) Jsoup.connect(myurl).get();
        String texto = String.valueOf(doc.getElementById("articleContent"));
        System.out.println(texto);
        contentAsString = texto;
        return contentAsString;
    }
}