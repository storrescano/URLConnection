package com.example.urlconnection;

import android.annotation.SuppressLint;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.urlconnection.databinding.ActivityMainBinding;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.HttpURLConnection;
import java.net.URL;

public class MainActivity extends AppCompatActivity {
    ActivityMainBinding mBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        mBinding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(mBinding.getRoot());
        mBinding.URLInsert.setText(getResources().getText(R.string.http));
        mBinding.download.setOnClickListener(this::connect);
    }

    private void connect(View view) {
        String path = String.valueOf(mBinding.URLInsert.getText());
        ConnectivityManager connMgr = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
        if(networkInfo != null && networkInfo.isConnected()) {
            new Thread(() -> {
                String response = downloadUrl(path);
                if(response != null) {
                    mBinding.web.setText(cleanText(response));
                } else {
                    mBinding.web.setText("Ocurrió un error durante la conexión");
                }

            }).start();
        } else {
            Toast.makeText(this, "No hay conexiones de red habilitadas", Toast.LENGTH_SHORT).show();
        }
    }

    public String readIt(InputStream stream) throws IOException {
        Reader reader = new InputStreamReader(stream, "UTF-8");
        StringBuilder sb = new StringBuilder();
        int k;
        while ((k = reader.read()) != -1) {
            sb.append((char)k);
        }
        return sb.toString();
    }

    private String downloadUrl(String urlCliente) {
        URL url;
        HttpURLConnection conn;
        String contentAsString = null;
        try {
            url = new URL(urlCliente);
            conn = (HttpURLConnection) url.openConnection();
            conn.setReadTimeout(10000);
            conn.setConnectTimeout(15000);
            conn.setRequestMethod("GET");
            conn.setDoInput(true);
            conn.connect();

            try(InputStream is = conn.getInputStream()) {
                contentAsString = readIt(is);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return contentAsString;
    }

    private String cleanText(String dom) {
        int initPos = dom.indexOf("articleContent");
        initPos = dom.indexOf(">", initPos) + 1;
        int endPos = dom.indexOf("</div>", initPos);
        return dom.substring(initPos, endPos).replaceAll("<[^>]*>", "");
    }
}

