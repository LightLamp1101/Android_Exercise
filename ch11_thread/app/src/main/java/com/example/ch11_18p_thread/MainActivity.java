package com.example.ch11_18p_thread;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.ImageView;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

public class MainActivity extends AppCompatActivity {

    private ImageView mImageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mImageView = findViewById(R.id.image);

        try { // test1: 쓰레드를 사용하지 않는 경우
            Bitmap b = downloadUrl("http://java.kpu.ac.kr/image.png");
            mImageView.setImageBitmap(b);
        } catch (Exception e) {
        }
        new myThread().start();
    }

    private Bitmap downloadUrl(String strUrl) throws IOException {
        Bitmap bitmap = null;
        InputStream iStream = null;
        try {
            URL url = new URL(strUrl);
            iStream = url.openConnection().getInputStream();
            bitmap = BitmapFactory.decodeStream(iStream);
        } catch (Exception e) {
            Log.i("MyApplication", e.getMessage());
        } finally {
            iStream.close();
        }
        return bitmap;
    }

    class myThread extends Thread {
        Bitmap b;
        public void run() {
            try{
                b= downloadUrl("https://www.gstatic.com/webp/gallery3/1.png");

                //test2 post()메소드를 사용하지 않는 경우
                mImageView.post(new Runnable() {
                    @Override
                    public void run() {
                        mImageView.setImageBitmap(b);
                    }
                });
            } catch (Exception e) {
                Log.i("MyApplication", e.getMessage());
            }
        }
    }
}
// 퍼미션에 인터넷부분 조정할 것


