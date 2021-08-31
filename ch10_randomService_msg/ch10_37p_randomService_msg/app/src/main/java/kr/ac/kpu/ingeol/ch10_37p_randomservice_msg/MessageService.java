package kr.ac.kpu.ingeol.ch10_37p_randomservice_msg;

import android.app.Service;
import android.content.Intent;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.Messenger;
import android.widget.Toast;

import java.util.Random;

public class MessageService extends Service {
    static final int GET_NUM = 1;
    private final Random mGenerator = new Random();

    class IncomingHandler extends Handler {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case GET_NUM:
                    Toast.makeText(getApplicationContext(), "Number:" + getRandomNumber(), Toast.LENGTH_SHORT).show();
                    break;
                default:
                    super.handleMessage(msg);
            }
        }
    }

    final Messenger mMessenger = new Messenger(new IncomingHandler());

    @Override
    public IBinder onBind(Intent intent) {
        return mMessenger.getBinder();
    }

    public int getRandomNumber() {
        return mGenerator.nextInt(100);
    }
}
