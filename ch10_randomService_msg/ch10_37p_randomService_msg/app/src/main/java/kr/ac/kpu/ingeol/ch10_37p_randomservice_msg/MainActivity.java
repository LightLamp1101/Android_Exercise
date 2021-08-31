package kr.ac.kpu.ingeol.ch10_37p_randomservice_msg;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.os.Message;
import android.os.Messenger;
import android.os.RemoteException;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    Messenger mService = null;
    boolean mBound = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    @Override
    protected void onStart() {
        super.onStart();
        Intent intent = new Intent(this, MessageService.class);
        bindService(intent, mConnection, Context.BIND_AUTO_CREATE);
    }

    @Override
    protected void onStop() {
        super.onStop();
        if (mBound) {
            unbindService(mConnection);
            mBound = false;
        }
    }

    public void onButtonClick(View v) {
        if (mBound) {
            Message msg = Message.obtain();
            msg.what = MessageService.GET_NUM;
            try {
                mService.send(msg);
            } catch (RemoteException e) {
            }
        }
    }

    private ServiceConnection mConnection = new ServiceConnection() {
        //@Override
        public void onServiceConnected(ComponentName className, IBinder service) {
            mService = new Messenger(service);
            mBound = true;
        }

        //@Override
        public void onServiceDisconnected(ComponentName arg0) {
            mService = null;
            mBound = false;
        }
    };
}
