package com.example.user.ch10_22p_intentserv;

import android.app.Activity;
import android.app.IntentService;
import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.os.Messenger;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;

public class MainActivity extends AppCompatActivity {

    private Handler handler = new Handler() {
        public void handleMessage(Message message ) {
            Object path = message.obj;

            if(message.arg1 == RESULT_OK && path != null) {
                Toast.makeText(getApplicationContext(), path.toString() + "을 다운로드 하였음", Toast.LENGTH_LONG).show();
            } else {
                Toast.makeText(getApplicationContext(), "다운로드 실패", Toast.LENGTH_LONG).show();
            }
        };
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void onClick(View view) {
        Intent intent = new Intent(this, DownloadService.class);
        Messenger messenger = new Messenger(handler);
        intent.putExtra("MESSENGER",messenger);
        intent.putExtra("urlpath", "https://ezone.kpu.ac.kr/images/icon_114.png");
        startService(intent);
    }
}


