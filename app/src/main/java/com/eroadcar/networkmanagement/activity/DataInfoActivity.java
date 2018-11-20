package com.eroadcar.networkmanagement.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.eroadcar.networkmanagement.R;

public class DataInfoActivity extends BaseActivity implements View.OnClickListener {
    private Button back_btn;
    private TextView title_tv, num_tv, name_tv, phone_tv, store_tv, status_tv;
    private LinearLayout pwd_ll, name_ll, phone_ll;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_date_info);
        back_btn = (Button) findViewById(R.id.back_btn);
        title_tv = (TextView) findViewById(R.id.title_tv);
        num_tv = (TextView) findViewById(R.id.num_tv);
        name_tv = (TextView) findViewById(R.id.name_tv);
        phone_tv = (TextView) findViewById(R.id.phone_tv);
        store_tv = (TextView) findViewById(R.id.store_tv);
        status_tv = (TextView) findViewById(R.id.status_tv);
        pwd_ll = (LinearLayout) findViewById(R.id.pwd_ll);
        name_ll = (LinearLayout) findViewById(R.id.name_ll);
        phone_ll = (LinearLayout) findViewById(R.id.phone_ll);
        back_btn.setOnClickListener(this);
        pwd_ll.setOnClickListener(this);
        name_ll.setOnClickListener(this);
        phone_ll.setOnClickListener(this);
        title_tv.setText("基本资料");
        num_tv.setText(application.getUserBean().getMg_code());
        name_tv.setText(application.getUserBean().getMg_name());
        phone_tv.setText(application.getUserBean().getMg_cellphone());
        store_tv.setText(application.getUserBean().getMg_shopname());
        status_tv.setText(application.getUserBean().getMg_posname());
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.back_btn:
                onBackPressed();
                break;
            case R.id.pwd_ll:
                intent(ChangePwdActivity.class);
                break;
            case R.id.phone_ll:
                intent(ChangePhoneActivity.class);
                break;
            case R.id.name_ll:
                intent(ChangeNameActivity.class);
                break;
        }
    }
}
