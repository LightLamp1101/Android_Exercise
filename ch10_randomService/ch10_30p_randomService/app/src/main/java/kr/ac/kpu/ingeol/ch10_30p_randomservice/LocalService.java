package kr.ac.kpu.ingeol.ch10_30p_randomservice;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;

import java.util.Random;

public class LocalService extends Service {
    private final IBinder mBibnder = new LocalBinder();

    private final Random mGenerator = new Random();

    public class LocalBinder extends Binder {
        LocalService getService() {
            return LocalService.this;
        }
    }
        @Override
    public IBinder onBind(Intent intent) {
        return mBibnder;
    }

    public int getRandomNumber() {
        return mGenerator.nextInt(100);
    }
}
