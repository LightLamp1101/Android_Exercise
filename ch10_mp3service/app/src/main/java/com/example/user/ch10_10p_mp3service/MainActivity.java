package com.example.user.ch10_10p_mp3service;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private static final String TAG = "MusicServiceTest";
    Button start, stop;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        start = (Button) findViewById(R.id.start);
        stop = (Button) findViewById(R.id.stop);
        start.setOnClickListener(this);
        stop.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        
    }
}