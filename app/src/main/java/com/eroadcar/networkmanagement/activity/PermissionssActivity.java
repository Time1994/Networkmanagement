package com.eroadcar.networkmanagement.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.GridView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.gson.GsonRequest;
import com.android.volley.toolbox.gson.RequesListener;
import com.eroadcar.networkmanagement.R;
import com.eroadcar.networkmanagement.adapter.PermissionssAdapter;
import com.eroadcar.networkmanagement.bean.CommonBean;
import com.eroadcar.networkmanagement.bean.PermissionssBean;
import com.eroadcar.networkmanagement.utils.Constant;
import com.eroadcar.networkmanagement.utils.ToastUtils;

import java.util.ArrayList;
import java.util.HashMap;

public class PermissionssActivity extends BaseActivity implements View.OnClickListener {
    private Button back_btn, logout_btn;
    private TextView title_tv;
    private GridView home_gv;
    private CommonBean<ArrayList<PermissionssBean>> bean;
    public ArrayList<PermissionssBean> permissionssBean = new ArrayList<>();
    private String mg_distri_id = "", mg_distri_shopsid = "", mg_role_ids = "";
    public String ids = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_permissionss);
        back_btn = (Button) findViewById(R.id.back_btn);
        title_tv = (TextView) findViewById(R.id.title_tv);

        back_btn = (Button) findViewById(R.id.back_btn);
        title_tv = (TextView) findViewById(R.id.title_tv);

        logout_btn = (Button) findViewById(R.id.logout_btn);

        home_gv = (GridView) findViewById(R.id.home_gv);

        title_tv.setText("权限分配");

        back_btn.setOnClickListener(this);
        logout_btn.setOnClickListener(this);

        mg_distri_id = getIntent().getStringExtra("ID");
        mg_distri_shopsid = getIntent().getStringExtra("SHOPSID");
        ids = getIntent().getStringExtra("IDS");
        getRoles();
    }

    private void getRoles() {
        loadingDialog.setMessage("正在获取数据...");
        loadingDialog.dialogShow();
        String url = Constant.getRoles;
        HashMap<String, String> map = new HashMap<String, String>();
        map.put("appcode", IMEI);
        map.put("apptype", Constant.APPTYPE);
        map.put("mg_id", application.getUserBean().getMg_id());
        map.put("mg_name", application.getUserBean().getMg_name());
        map.put("mg_shopsid", application.getUserBean().getMg_shopsid());
        map.put("mg_groupid", application.getUserBean().getMg_groupid());
        map.put("mg_shopname", application.getUserBean().getMg_shopname());
        map.put("mg_shopcode", application.getUserBean().getMg_shopcode());
        map.put("mg_code", application.getUserBean().getMg_code());
        map.put("mg_groupname", application.getUserBean().getMg_groupname());
        map.put("mg_posid", application.getUserBean().getMg_posid());
        map.put("mg_posname", application.getUserBean().getMg_posname());

        GsonRequest<CommonBean<ArrayList<PermissionssBean>>> requtst = new GsonRequest<CommonBean<ArrayList<PermissionssBean>>>(
                Request.Method.POST, url, listener_getInshopCars);
        requtst.setHashmap(map);
        mRequestQueue.add(requtst);
    }

    private RequesListener<CommonBean<ArrayList<PermissionssBean>>> listener_getInshopCars = new RequesListener<CommonBean<ArrayList<PermissionssBean>>>() {
        @Override
        public void onResponse(CommonBean<ArrayList<PermissionssBean>> arg0) {
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

    private void distributeRole(String mg_distri_id, String mg_distri_shopsid, String mg_role_ids) {
        loadingDialog.setMessage("正在保存权限数据...");
        loadingDialog.dialogShow();
        String url = Constant.distributeRole;
        HashMap<String, String> map = new HashMap<String, String>();
        map.put("appcode", IMEI);
        map.put("apptype", Constant.APPTYPE);
        map.put("mg_id", application.getUserBean().getMg_id());
        map.put("mg_name", application.getUserBean().getMg_name());
        map.put("mg_shopsid", application.getUserBean().getMg_shopsid());
        map.put("mg_groupid", application.getUserBean().getMg_groupid());
        map.put("mg_shopname", application.getUserBean().getMg_shopname());
        map.put("mg_shopcode", application.getUserBean().getMg_shopcode());
        map.put("mg_code", application.getUserBean().getMg_code());
        map.put("mg_groupname", application.getUserBean().getMg_groupname());
        map.put("mg_posid", application.getUserBean().getMg_posid());
        map.put("mg_posname", application.getUserBean().getMg_posname());
        map.put("mg_distri_id", mg_distri_id);
        map.put("mg_distri_shopsid", mg_distri_shopsid);
        map.put("mg_role_ids", mg_role_ids);

        GsonRequest<CommonBean<ArrayList<PermissionssBean>>> requtst = new GsonRequest<CommonBean<ArrayList<PermissionssBean>>>(
                Request.Method.POST, url, listener_distributeRole);
        requtst.setHashmap(map);
        mRequestQueue.add(requtst);
    }

    private RequesListener<CommonBean<ArrayList<PermissionssBean>>> listener_distributeRole = new RequesListener<CommonBean<ArrayList<PermissionssBean>>>() {
        @Override
        public void onResponse(CommonBean<ArrayList<PermissionssBean>> arg0) {
            // TODO Auto-generated method stub
            bean = arg0;
            mHandler.sendEmptyMessage(2);
            loadingDialog.dismiss();
        }

        @Override
        public void onErrorResponse(VolleyError arg0) {
            // TODO Auto-generated method stub
            mHandler.sendEmptyMessage(-1);
            loadingDialog.dismiss();
        }

    };
    private Handler mHandler = new Handler() {
        public void handleMessage(android.os.Message msg) {
            switch (msg.what) {
                case 1:
                    if (bean.getState().equals("success")) {
                        home_gv.setAdapter(new PermissionssAdapter(PermissionssActivity.this, bean.getData()));
                    }

                    break;
                case 2:
                    if (bean.getState().equals("success")) {
                        onBackPressed();
                    }
                    ToastUtils.showShort(bean.getMsg());
                    break;
                case -1:
                    break;
                default:
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
            case R.id.logout_btn:
//                if (roleBeans.size() == 0) {
//                    ToastUtils.showShort("请选择需要分配的权限");
//                    return;
//                }

                for (int i = 0; i < permissionssBean.size(); i++) {
                    mg_role_ids += permissionssBean.get(i).getRole_app_id() + ",";
                }

                distributeRole(mg_distri_id, mg_distri_shopsid, mg_role_ids.substring(0, mg_role_ids.length() - 1));
                break;
            default:
                break;
        }

    }
}
