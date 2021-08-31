package com.example.user.ch10_22p_intentserv;

import android.app.Activity;
import android.app.IntentService;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Message;
import android.os.Messenger;
import android.widget.ImageView;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;

public  class DownloadService extends IntentService {
    private int result = Activity.RESULT_CANCELED;

    public DownloadService() {
        super("DownloadService");
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        String urlPath = intent.getStringExtra("urlpath");
        String buffer = " ";

        InputStream stream = null;
        try {
            URL url = new URL(urlPath);
            stream = url.openConnection().getInputStream();
            Bitmap bitmap = BitmapFactory.decodeStream(stream);
            //ImageView.setImage 집에서 해보기 모르겠음
            /*
            InputStreamReader reader = new InputStreamReader(stream);
            int i = 0, next = -1;
            while((next = reader.read()) != -1){
                buffer += " " + (char) next;
                if(++i>100) break;
            }
            */
            result = Activity.RESULT_OK;
        } catch (Exception e ) {
            e.printStackTrace();
        }finally {
            if(stream != null) {
                try{ stream.close();
                }catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        Bundle extras = intent.getExtras();
        if(extras != null) {
            Messenger messenger = (Messenger)extras.get("MESSENGER");
            Message msg = Message.obtain();
            msg.arg1 = result;
            msg.obj = buffer;
            try{
                messenger.send(msg);
            }
            catch (android.os.RemoteException e1) {

            }
        }
    }
}
