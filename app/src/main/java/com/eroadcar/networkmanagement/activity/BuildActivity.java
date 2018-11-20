package com.eroadcar.networkmanagement.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.gson.GsonRequest;
import com.android.volley.toolbox.gson.RequesListener;
import com.eroadcar.networkmanagement.R;
import com.eroadcar.networkmanagement.bean.CommonBean;
import com.eroadcar.networkmanagement.utils.Constant;
import com.eroadcar.networkmanagement.utils.ToastUtils;
import com.eroadcar.networkmanagement.view.ClearEditText;

import java.util.HashMap;

public class BuildActivity extends BaseActivity implements View.OnClickListener {
    private TextView title_tv;
    private Button sure_btn, back_btn;
    private ClearEditText name_cet, phone_cet, pwd_cet;
    private CommonBean bean;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_build);
        sure_btn = (Button) findViewById(R.id.sure_btn);
        back_btn = (Button) findViewById(R.id.back_btn);
        title_tv = (TextView) findViewById(R.id.title_tv);
        back_btn = (Button) findViewById(R.id.back_btn);
        name_cet = (ClearEditText) findViewById(R.id.name_cet);
        phone_cet = (ClearEditText) findViewById(R.id.phone_cet);
        pwd_cet = (ClearEditText) findViewById(R.id.pwd_cet);
        sure_btn.setOnClickListener(this);
        back_btn.setOnClickListener(this);
        title_tv.setText("新建子账号");
    }

    private void addUser(String mg_add_name, String mg_add_cellphone, String mg_add_pwd) {
        loadingDialog.setMessage("正在提交...");
        loadingDialog.dialogShow();
        String url = Constant.addUser;
        System.out.println("url=" + url);
        HashMap<String, String> map = new HashMap<String, String>();
        map.put("mg_add_name", mg_add_name);
        map.put("mg_add_cellphone", mg_add_cellphone);
        map.put("mg_add_pwd", mg_add_pwd);
        map.put("app_code", IMEI);
        map.put("app_type", Constant.APPTYPE);
        map.put("mg_id", application.getUserBean().getMg_id());
        map.put("mg_name", application.getUserBean().getMg_name());
        map.put("mg_shopcode", application.getUserBean().getMg_shopcode());
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
                        intent(PermissionsActivity.class);

                    } else {
                    }
                    ToastUtils.showShort(bean.getMsg());
                    break;
            }
        }
    };

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.sure_btn:
                if (name_cet.getText().toString().equals("")) {
                    ToastUtils.showShort("请输入姓名");
                    return;
                }
                if (phone_cet.getText().toString().equals("")) {
                    ToastUtils.showShort("请输入手机号");
                    return;
                }
                if (pwd_cet.getText().toString().equals("")) {
                    ToastUtils.showShort("请输入要新建的密码");
                    return;
                }
                addUser(name_cet.getText().toString(), phone_cet.getText().toString(),
                        pwd_cet.getText().toString());
                break;
            case R.id.back_btn:
                onBackPressed();
                break;
        }

    }
}
