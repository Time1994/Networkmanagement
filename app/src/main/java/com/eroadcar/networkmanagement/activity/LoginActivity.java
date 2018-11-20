package com.eroadcar.networkmanagement.activity;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.preference.PreferenceManager;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.gson.GsonRequest;
import com.android.volley.toolbox.gson.RequesListener;
import com.eroadcar.networkmanagement.R;
import com.eroadcar.networkmanagement.bean.CommonBean;
import com.eroadcar.networkmanagement.bean.UserBean;
import com.eroadcar.networkmanagement.utils.Constant;
import com.eroadcar.networkmanagement.utils.MD5;
import com.eroadcar.networkmanagement.utils.ToastUtils;
import com.eroadcar.networkmanagement.utils.Tool;
import com.eroadcar.networkmanagement.view.ClearEditText;

import java.text.SimpleDateFormat;
import java.util.HashMap;

import cn.jpush.android.api.JPushInterface;

public class LoginActivity extends BaseActivity implements View.OnClickListener {
    private ClearEditText shopcode_cet, mobile_cet, pwd_cet;
    private Button login_btn;
    private CheckBox remember_cb;
    private TextView forget_tv;
    private CommonBean<UserBean> bean;
    private SharedPreferences prefs;
    private boolean Remember = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        shopcode_cet = (ClearEditText) findViewById(R.id.shopcode_cet);
        mobile_cet = (ClearEditText) findViewById(R.id.mobile_cet);
        pwd_cet = (ClearEditText) findViewById(R.id.pwd_cet);
        login_btn = (Button) findViewById(R.id.login_btn);
        remember_cb = (CheckBox) findViewById(R.id.remember_cb);
        forget_tv = (TextView) findViewById(R.id.forget_tv);
        login_btn.setOnClickListener(this);
        forget_tv.setOnClickListener(this);
        prefs = getSharedPreferences("eroadcar", Context.MODE_PRIVATE);
        if (!prefs.getString("SHOPNUM", "").equals("")) {
            shopcode_cet.setText(prefs.getString("SHOPNUM", ""));
        }
        if (!prefs.getString("INPUTNUM", "").equals("")) {
            mobile_cet.setText(prefs.getString("INPUTNUM", ""));
        }
        if (!prefs.getString("PWDNUM", "").equals("")) {
            pwd_cet.setText(prefs.getString("PWDNUM", ""));
            login_btn.setEnabled(true);
        }
        mobile_cet.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    if (shopcode_cet.getText().toString().equals("")) {
                        mobile_cet.clearFocus();
                        shopcode_cet.requestFocus();
                        ToastUtils.showShort("请输入门店代码");
                    }
                }
            }
        });
        mobile_cet.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                if (shopcode_cet.getText().toString().equals("")) {
                    shopcode_cet.requestFocus();
                    ToastUtils.showShort("请输入门店代码");
                }
//                else if (shopcode_cet.getText().toString().length() != 11) {
//                    shopcode_cet.requestFocus();
//                    ToastUtils.showShort("请输入正确的代码");
//                }

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        pwd_cet.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    if (mobile_cet.getText().toString().equals("")) {
                        pwd_cet.clearFocus();
                        mobile_cet.requestFocus();
                        ToastUtils.showShort("请输入账号");
                    }
                }
            }
        });
        pwd_cet.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                if (mobile_cet.getText().toString().equals("")) {
                    mobile_cet.requestFocus();
                    ToastUtils.showShort("请输入账号");
                }
//                else if (mobile_cet.getText().toString().length() != 11) {
//                    mobile_cet.requestFocus();
//                    ToastUtils.showShort("请输入正确的账号");
//                }

                if (s.length() > 0) {
                    login_btn.setEnabled(true);
                } else {
                    login_btn.setEnabled(false);
                }
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        remember_cb.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    Remember = true;
                } else {
                    Remember = false;
                }

            }
        });

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();

        application.exit();
    }

    private void login(String shopcode, String usercode, String pwd) {
        loadingDialog.setMessage("正在登录...");
        loadingDialog.dialogShow();
        String url = Constant.login;
        HashMap<String, String> map = new HashMap<String, String>();
        map.put("shopcode", shopcode);
        map.put("usercode", usercode);
        map.put("password", Tool.encrypt(pwd, "1234567812345678"));
        map.put("checkstr",
                MD5.Md5((new SimpleDateFormat("yyyyMMdd"))
                        .format(new java.util.Date()) + "eroadcar"));
        map.put("appcode", IMEI);
        map.put("apptype", "android");
        map.put("mg_registerid",
                JPushInterface.getRegistrationID(LoginActivity.this));
        GsonRequest<CommonBean<UserBean>> requtst = new GsonRequest<CommonBean<UserBean>>(
                Request.Method.POST, url, listener_login);
        requtst.setRetryPolicy(new DefaultRetryPolicy(100 * 1000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        requtst.setHashmap(map);
        mRequestQueue.add(requtst);
    }

    private RequesListener<CommonBean<UserBean>> listener_login = new RequesListener<CommonBean<UserBean>>() {
        public void onResponse(CommonBean<UserBean> arg0) {
            // TODO Auto-generated method stub
            bean = arg0;
            mHandler.sendEmptyMessage(1);
            loadingDialog.dismiss();
        }

        public void onErrorResponse(VolleyError arg0) {
            // TODO Auto-generated method stub
            mHandler.sendEmptyMessage(-1);
            loadingDialog.dismiss();
        }

    };
    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 1:
                    if (bean.getState().equals("success")) {


                        prefs.edit().putString("SHOPNUM", shopcode_cet.getText().toString()).commit();
                        prefs.edit().putString("INPUTNUM", mobile_cet.getText().toString()).commit();
                        if (Remember) {
                            prefs.edit().putString("PWDNUM", pwd_cet.getText().toString()).commit();
                        } else {
                            prefs.edit().putString("PWDNUM", "").commit();
                        }

                        application.setUserBean(bean.getData());
                        intent(MainActivity.class);
                        finish();
                    } else {
                        ToastUtils.showLong(bean.getMsg());
                    }
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
            case R.id.login_btn:
                if (!checkNetworkAvailable()) {
                    ToastUtils.showShort("没有网络，请检查网络...");
                }
                login(shopcode_cet.getText().toString(), mobile_cet.getText().toString(), pwd_cet.getText().toString());
                break;
            case R.id.forget_tv:
                intent(ChangePwdActivity.class);
                break;
        }

    }
}
