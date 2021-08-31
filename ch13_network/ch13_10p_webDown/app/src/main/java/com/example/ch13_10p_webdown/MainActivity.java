package com.example.ch13_10p_webdown;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class MainActivity extends AppCompatActivity {
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button btnDownload = findViewById(R.id.download);
        View.OnClickListener downloadListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isNetworkAvailable()) {
                    EditText url = findViewById(R.id.url);
                    DownloadTask downloadTask = new DownloadTask();
                    downloadTask.execute(url.getText().toString());
                } else {
                    Toast.makeText(getBaseContext(),
                            "Network is not Available", Toast.LENGTH_SHORT)
                            .show();
                }
            }
        };
        btnDownload.setOnClickListener(downloadListener);
    }
    private boolean isNetworkAvailable() {
        boolean available = false;
        ConnectivityManager connMgr =
                (ConnectivityManager)getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
        if (networkInfo != null && networkInfo.isAvailable())
            available = true;
        return available;
    }
    private String downloadUrl(String strUrl) throws IOException {
        String s = null;
        byte[] buffer = new byte[1000];
        InputStream iStream = null;
        try {
            URL url = new URL(strUrl);
            HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
            iStream = urlConnection.getInputStream();
            iStream.read(buffer);
            s = new String(buffer);
        } catch (Exception e) {
            Log.d("Except while down url", e.toString());
        } finally {
            iStream.close();
        }
        return s;
    }
    private class DownloadTask extends AsyncTask<String, Integer, String> {
        String s = null;
        @Override
        protected String doInBackground(String... url) {
            try {
                s = downloadUrl(url[0]);
            } catch (Exception e) {
                Log.d("Background Task", e.toString());
            }
            return s;
        }
        @Override
        protected void onPostExecute(String result) {
            TextView tView = (TextView) findViewById(R.id.text);
            tView.setText(result);
            Toast.makeText(getBaseContext(),
                    "Web page downloaded successfully", Toast.LENGTH_SHORT).show();
        }
    }
}
