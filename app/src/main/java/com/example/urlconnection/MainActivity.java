package com.example.urlconnection;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        EditText url = findViewById(R.id.URLInsert);
        Button download = findViewById(R.id.download);


        download.setOnClickListener(v -> {
            String stringUrl = url.getText().toString();
            urlConnection(stringUrl);
        });
    }


    public void urlConnection(String stringUrl) {
        // Especifica la URL a la que quieres conectarte
        try {
            // Crea un objeto URL a partir de la URL especificada
            URL url = new URL(stringUrl);

            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.connect();

            InputStream inputStream = connection.getInputStream();
            BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));

            StringBuilder result = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                result.append(line);
            }

            String text = result.toString();
            System.out.println(text);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}

