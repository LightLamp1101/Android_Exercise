package kr.ac.kpu.ingeol.ch10_40p_cntservice;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;

import java.util.Random;

public class LocalService extends Service {
    private final IBinder mBibnder = new LocalBinder();

    private final Random mGenerator = new Random();
    Thread counter;
    int mCounter = 0;
    boolean bCount = true;

    public class LocalBinder extends Binder {
        LocalService getService() {
            return LocalService.this;
        }
    }
    @Override
    public IBinder onBind(Intent intent) {
        counter = new Thread(new Runnable() {
            @Override
            public void run() {
                while (bCount) {
                    try{
                        Thread.sleep(1000);
                        mCounter++;
                    } catch (Exception e) {

                    }
                }
            }
        });
        counter.start();
        return mBibnder;
    }

    @Override
    public boolean onUnbind(Intent intent) {
        bCount = false;
        return super.onUnbind(intent);
    }

    public int getCounter() {
        return mCounter;
    }
}
