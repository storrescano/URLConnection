package com.example.urlconnection;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
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

            // Abre una conexión a la URL
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();

            // Establece el método de la solicitud como GET
            conn.setRequestMethod("GET");

            // Establece un tiempo de espera para la conexión y la lectura
            conn.setConnectTimeout(5000);
            conn.setReadTimeout(5000);

            // Conecta a la URL
            conn.connect();

            // Comprueba si la conexión fue exitosa
            int responseCode = conn.getResponseCode();
            if (responseCode != 200) {
                throw new Exception("Error al conectarse a la URL (código de respuesta " + responseCode + ")");
            }

            // Lee el código HTML de la página
            BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            StringBuilder sb = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                sb.append(line).append("\n");
            }

            // Cierra la conexión
            reader.close();
            conn.disconnect();

            // Muestra el código HTML en la consola
            System.out.println(sb.toString());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

