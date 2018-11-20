package com.eroadcar.networkmanagement.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.preference.PreferenceManager;

import com.eroadcar.networkmanagement.R;

public class LoadingActivity extends BaseActivity {
    private Thread t;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loading);
        t = new Thread() {
            public void run() {
                try {
                    // Looper.prepare();include_toptitlet.xml
                    Thread.sleep(2000);
                } catch (Exception e) {
                    e.printStackTrace();
                }

                tmHandler.post(tmUpdateResults);

            }
        };
        t.start();
    }
    final Handler tmHandler = new Handler();
    final Runnable tmUpdateResults = new Runnable() {
        @Override
        public void run() {
            Intent intent = new Intent(LoadingActivity.this, LoginActivity.class);
            startActivity(intent);

        }
    };
}
