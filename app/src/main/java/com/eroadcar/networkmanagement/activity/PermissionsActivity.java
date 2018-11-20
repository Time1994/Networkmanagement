package com.eroadcar.networkmanagement.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.gson.GsonRequest;
import com.android.volley.toolbox.gson.RequesListener;
import com.eroadcar.networkmanagement.R;
import com.eroadcar.networkmanagement.adapter.PermissionsAdapter;
import com.eroadcar.networkmanagement.bean.CommonBean;
import com.eroadcar.networkmanagement.bean.PermissionsBean;
import com.eroadcar.networkmanagement.utils.Constant;
import com.eroadcar.networkmanagement.utils.ToastUtils;

import java.util.ArrayList;
import java.util.HashMap;

public class PermissionsActivity extends BaseActivity implements View.OnClickListener {
    private Button back_btn;
    private TextView title_tv;
    private ListView permissions_lv;
    private CommonBean<ArrayList<PermissionsBean>> bean;
    private PermissionsAdapter permissionsAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_permissions);
        back_btn = (Button) findViewById(R.id.back_btn);
        title_tv = (TextView) findViewById(R.id.title_tv);
        permissions_lv = (ListView) findViewById(R.id.permissions_lv);
        back_btn.setOnClickListener(this);
        title_tv.setText("权限分配");
        getUsers();

    }

    @Override
    protected void onResume() {
        super.onResume();

        getUsers();
    }

    private void getUsers() {
        loadingDialog.setMessage("正在获取列表...");
        loadingDialog.dialogShow();
        String url = Constant.getUsers;
        System.out.println("url=" + url);
        HashMap<String, String> map = new HashMap<String, String>();
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
        GsonRequest<CommonBean<ArrayList<PermissionsBean>>> requtst = new GsonRequest<CommonBean<ArrayList<PermissionsBean>>>(Request.Method
                .POST,
                url, listener_getUsers);
        requtst.setHashmap(map);
        mRequestQueue.add(requtst);
    }

    private RequesListener<CommonBean<ArrayList<PermissionsBean>>> listener_getUsers = new
            RequesListener<CommonBean<ArrayList<PermissionsBean>>>() {
                @Override
                public void onResponse(CommonBean<ArrayList<PermissionsBean>> arg0) {
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
                        permissionsAdapter = new PermissionsAdapter(PermissionsActivity.this, bean.getData());
                        permissions_lv.setAdapter(permissionsAdapter);
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
        }
    }
}