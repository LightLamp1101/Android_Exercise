package com.example.user.ch07_37p_fragment;

import android.app.Fragment;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        getFragmentManager().beginTransaction().add(R.id.fragment_container, new FragementA());
    }

    public void selectFragment(View view) {
        Fragment fr = null;
        switch (view.getId()) {
            case R.id.button1:
                fr = new FragementA();
                break;
            case R.id.button2:
                fr = new FragmentB();
                break;
        }

        getFragmentManager().beginTransaction().replace(R.id.fragment_container, fr).commit();
    }

}
