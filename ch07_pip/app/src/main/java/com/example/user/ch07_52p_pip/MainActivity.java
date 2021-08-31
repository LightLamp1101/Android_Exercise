package com.example.user.ch07_52p_pip;

import android.app.PictureInPictureParams;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Rational;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    static private boolean binPictureMode = false;
    private Button button;
    private FrameLayout player;
    private TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        player = findViewById(R.id.frame);
        button = findViewById(R.id.pip);
        textView = findViewById(R.id.textView);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (android.os.Build.VERSION.SDK_INT>=26) {
                    Rational rational = new Rational(player.getWidth(), player.getHeight());
                    PictureInPictureParams mParams = new PictureInPictureParams.Builder().setAspectRatio(rational).build();
                    enterPictureInPictureMode(mParams);
                }
            }
        });
        if(binPictureMode) {
            button.setVisibility(View.GONE);
            textView.setTextSize(20);
        }
    }

    @Override
    public void onPictureInPictureModeChanged(boolean isInPictureInPictureMode) {
        super.onPictureInPictureModeChanged(isInPictureInPictureMode);
        binPictureMode = isInPictureInPictureMode;
    }
}
