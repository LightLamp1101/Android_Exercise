package kr.ac.kpu.ingeol.ch10_45p_broadrecvcharge;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    private BroadcastReceiver chargerReceiver;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        chargerReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                Toast.makeText(getApplicationContext(), "전원이 연결되었습니다.", Toast.LENGTH_SHORT).show();
            }
        };
        registerReceiver(chargerReceiver, new IntentFilter(Intent.ACTION_POWER_CONNECTED));
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(chargerReceiver);
    }
}
