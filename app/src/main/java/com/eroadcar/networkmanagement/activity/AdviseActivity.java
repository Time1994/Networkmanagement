package com.eroadcar.networkmanagement.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.gson.GsonRequest;
import com.android.volley.toolbox.gson.RequesListener;
import com.eroadcar.networkmanagement.R;
import com.eroadcar.networkmanagement.bean.CommonBean;
import com.eroadcar.networkmanagement.bean.RankBean;
import com.eroadcar.networkmanagement.utils.Constant;
import com.eroadcar.networkmanagement.utils.ToastUtils;

import java.util.HashMap;

public class AdviseActivity extends BaseActivity implements View.OnClickListener {
    private Button sure_btn, back_btn;
    private TextView title_tv;
    private EditText pingjia_et;
    private CommonBean bean;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_advise);
        sure_btn = (Button) findViewById(R.id.sure_btn);
        back_btn = (Button) findViewById(R.id.back_btn);
        title_tv = (TextView) findViewById(R.id.title_tv);
        pingjia_et = (EditText) findViewById(R.id.pingjia_et);
        sure_btn.setOnClickListener(this);
        back_btn.setOnClickListener(this);
        title_tv.setText("意见反馈");


        pingjia_et.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                if (pingjia_et.getText().toString().equals("")) {
                    pingjia_et.requestFocus();
                }
                if (s.length() > 0) {
                    sure_btn.setEnabled(true);
                } else {
                    sure_btn.setEnabled(false);
                }
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }


    private void sendSuggest(String suggestion) {
        loadingDialog.setMessage("正在提交...");
        loadingDialog.dialogShow();
        String url = Constant.sendSuggest;
        System.out.println("url=" + url);
        HashMap<String, String> map = new HashMap<String, String>();
        map.put("suggestion", suggestion);
        map.put("mg_shopcode", application.getUserBean().getMg_shopcode());
        map.put("app_code", IMEI);
        map.put("app_type", Constant.APPTYPE);
        map.put("mg_id", application.getUserBean().getMg_id());
        map.put("mg_name", application.getUserBean().getMg_name());
        map.put("mg_shopsid", application.getUserBean().getMg_shopsid());
        map.put("mg_groupid", application.getUserBean().getMg_groupid());
        map.put("mg_shopname", application.getUserBean().getMg_shopname());
        map.put("mg_groupname", application.getUserBean().getMg_groupname());
        map.put("mg_posid", application.getUserBean().getMg_posid());
        map.put("mg_posname", application.getUserBean().getMg_posname());
        GsonRequest<CommonBean> requtst = new GsonRequest<CommonBean>(Request.Method
                .POST,
                url, listener_sendSuggest);
        requtst.setHashmap(map);
        mRequestQueue.add(requtst);
    }

    private RequesListener<CommonBean> listener_sendSuggest = new
            RequesListener<CommonBean>() {
                @Override
                public void onResponse(CommonBean arg0) {
                    // TODO Auto-generated method stub
                    bean = arg0;
                    mHandler.sendEmptyMessage(1);
                    loadingDialog.dismiss();
                }

                @Override
                public void onErrorResponse(VolleyError arg0) {
                    // TODO Auto-generated method stub
                    mHandler.sendEmptyMessage(-1);
                    loadingDialog.dismiss();
                }

            };
    Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 1:
                    if (bean.getState().equals("success")) {
                        intent(SettingActivity.class);
                        ToastUtils.showShort(bean.getMsg());
                    }
                    break;
            }
        }
    };

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.back_btn:
                onBackPressed();
                break;
            case R.id.sure_btn:
                if (pingjia_et.getText().toString().equals("")) {
                    ToastUtils.showShort("请反馈您的宝贵意见");
                    return;
                }
                sendSuggest(pingjia_et.getText().toString());
                break;
        }

    }
}
