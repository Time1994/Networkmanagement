package com.eroadcar.networkmanagement.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.eroadcar.networkmanagement.R;

public class AboutActivity extends BaseActivity {
    private TextView title_tv;
    private Button back_btn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);
        title_tv = (TextView) findViewById(R.id.title_tv);
        back_btn = (Button) findViewById(R.id.back_btn);
        title_tv.setText("关于我们");
        back_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }
}
