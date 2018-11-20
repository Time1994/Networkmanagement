package com.eroadcar.networkmanagement.activity;

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
import com.eroadcar.networkmanagement.bean.RankBean;
import com.eroadcar.networkmanagement.utils.Constant;
import com.eroadcar.networkmanagement.utils.ToastUtils;
import com.eroadcar.networkmanagement.view.ClearEditText;

import java.util.HashMap;

public class ChangeNameActivity extends BaseActivity implements View.OnClickListener {
    private Button back_btn;
    private TextView title_tv, other_btn;
    private ClearEditText mobile_cet;
    private CommonBean bean;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_name);
        back_btn = (Button) findViewById(R.id.back_btn);
        title_tv = (TextView) findViewById(R.id.title_tv);
        other_btn = (TextView) findViewById(R.id.other_btn);
        mobile_cet = (ClearEditText) findViewById(R.id.mobile_cet);
        back_btn.setOnClickListener(this);
        other_btn.setOnClickListener(this);

    }


    private void modifyUser(String mg_code, String mg_mdf_name,
                            String mg_mdf_cellphone, String mg_mdf_pwd,
                            String mg_shopsid, String mg_shopcode) {
        loadingDialog.setMessage("正在获取...");
        loadingDialog.dialogShow();
        String url = Constant.modifyUser;
        System.out.println("url=" + url);
        HashMap<String, String> map = new HashMap<String, String>();
        map.put("mg_mdf_pwd", mg_mdf_pwd);
        map.put("app_code", IMEI);
        map.put("app_type", Constant.APPTYPE);
        map.put("mg_mdf_name", mg_mdf_name);
        map.put("mg_mdf_cellphone", mg_mdf_cellphone);
        map.put("mg_code", application.getUserBean().getMg_code());
        map.put("mg_shopcode", application.getUserBean().getMg_shopcode());
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
                url, listener_modifyUser);
        requtst.setHashmap(map);
        mRequestQueue.add(requtst);
    }

    private RequesListener<CommonBean> listener_modifyUser = new
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
                        application.getUserBean().setMg_name(mobile_cet.getText().toString());
                        ToastUtils.showShort(bean.getMsg());
                        intent(DataInfoActivity.class);
                        finish();
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
            case R.id.other_btn:
                modifyUser(application.getUserBean().getMg_code(), mobile_cet.getText().toString(),
                        "", "", application.getUserBean().getMg_shopsid(),
                        application.getUserBean().getMg_shopcode());
                break;
        }
    }
}
