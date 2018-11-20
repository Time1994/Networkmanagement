package com.eroadcar.networkmanagement.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.gson.GsonRequest;
import com.android.volley.toolbox.gson.RequesListener;
import com.eroadcar.networkmanagement.R;
import com.eroadcar.networkmanagement.bean.CommonBean;
import com.eroadcar.networkmanagement.bean.RegistBean;
import com.eroadcar.networkmanagement.utils.Constant;
import com.eroadcar.networkmanagement.utils.Logger;
import com.eroadcar.networkmanagement.utils.ToastUtils;
import com.eroadcar.networkmanagement.view.ClearEditText;

import java.util.HashMap;

public class ChangePwdActivity extends BaseActivity implements View.OnClickListener {
    private ClearEditText phone_cet, pwd_cet, pwdt_cet, code_cet;
    private TextView getcode_tv, title_tv;
    private Button sure_btn, back_btn;
    private String mobile, CODE;
    private RegistBean yzbean;
    private CommonBean cbean;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_pwd);
        phone_cet = (ClearEditText) findViewById(R.id.phone_cet);
        pwd_cet = (ClearEditText) findViewById(R.id.pwd_cet);
        pwdt_cet = (ClearEditText) findViewById(R.id.pwdt_cet);
        code_cet = (ClearEditText) findViewById(R.id.code_cet);
        getcode_tv = (TextView) findViewById(R.id.getcode_tv);
        title_tv = (TextView) findViewById(R.id.title_tv);
        sure_btn = (Button) findViewById(R.id.sure_btn);
        back_btn = (Button) findViewById(R.id.back_btn);
        getcode_tv.setOnClickListener(this);
        sure_btn.setOnClickListener(this);
        back_btn.setOnClickListener(this);
        title_tv.setText("修改密码");
        pwd_cet.setOnFocusChangeListener(new View.OnFocusChangeListener() {

            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                // TODO Auto-generated method stub
                if (hasFocus) {
                    if (phone_cet.getText().toString().length() < 6) {
                        ToastUtils.showShort("请输入手机号码");
                        phone_cet.clearFocus();
                        phone_cet.requestFocus();
                    }
                }
            }
        });

        pwdt_cet.setOnFocusChangeListener(new View.OnFocusChangeListener() {

            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                // TODO Auto-generated method stub
                if (hasFocus) {
                    if (pwd_cet.getText().toString().length() < 6) {
                        ToastUtils.showShort("请输入至少6位密码");
                        pwd_cet.clearFocus();
                        pwd_cet.requestFocus();
                    }
                }
            }
        });
        code_cet.setOnFocusChangeListener(new View.OnFocusChangeListener() {

            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                // TODO Auto-generated method stub
                if (hasFocus) {
                    if (pwdt_cet.getText().toString().length() < 6) {
                        ToastUtils.showShort("请输入至少6位密码");
                        code_cet.clearFocus();
                        pwdt_cet.requestFocus();
                    }
                }
            }
        });

        code_cet.addTextChangedListener(new TextWatcher() {

            @Override
            public void onTextChanged(CharSequence s, int start, int before,
                                      int count) {
                // TODO Auto-generated method stub
                if (s.length() == 4) {
                    sure_btn.setEnabled(true);
                } else {
                    sure_btn.setEnabled(false);
                }
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count,
                                          int after) {
                // TODO Auto-generated method stub

            }

            @Override
            public void afterTextChanged(Editable s) {
                // TODO Auto-generated method stub

            }
        });
    }

    private void getVerificateCode(String mg_cellphone) {
        // TODO Auto-generated method stub
        loadingDialog.setMessage("正在获取验证码...");
        loadingDialog.dialogShow();
        String url = Constant.getVerificateCode;
        HashMap<String, String> map = new HashMap<String, String>();
        map.put("appcode", IMEI);
        map.put("apptype", Constant.APPTYPE);
        map.put("mg_cellphone", mg_cellphone);
        GsonRequest<RegistBean> requtst = new GsonRequest<RegistBean>(
                Request.Method.POST, url, listener_getVerificateCode);
        requtst.setHashmap(map);
        mRequestQueue.add(requtst);
        Logger.getLogger().i("url==" + url);

    }

    private RequesListener<RegistBean> listener_getVerificateCode = new RequesListener<RegistBean>() {
        @Override
        public void onResponse(RegistBean arg0) {
            // TODO Auto-generated method stub
            yzbean = arg0;
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

    private void modifyPassword(String mg_cellphone, String repassword,
                                String newpassword, String verifycode) {
        // TODO Auto-generated method stub
        String url = Constant.modifyPassword;
        HashMap<String, String> map = new HashMap<String, String>();
        map.put("mg_cellphone", mg_cellphone);
        map.put("repassword", repassword);
        map.put("newpassword", newpassword);
        map.put("verifycode", verifycode);
        GsonRequest<CommonBean> requtst = new GsonRequest<CommonBean>(
                Request.Method.POST, url, listener_modifyPassword);
        requtst.setHashmap(map);
        mRequestQueue.add(requtst);
        Logger.getLogger().i("url==" + url);

    }

    private RequesListener<CommonBean> listener_modifyPassword = new RequesListener<CommonBean>() {
        @Override
        public void onResponse(CommonBean arg0) {
            // TODO Auto-generated method stub
            cbean = arg0;
            mHandler.sendEmptyMessage(2);
        }

        @Override
        public void onErrorResponse(VolleyError arg0) {
            // TODO Auto-generated method stub
            mHandler.sendEmptyMessage(-1);
        }

    };
    Handler mHandler = new Handler() {
        public void handleMessage(android.os.Message msg) {
            switch (msg.what) {
                case 1:
                    if (yzbean.getState().equals("success")) {
                        ToastUtils.showShort("验证码已发送,请注意查收短信!");
                        CODE = yzbean.getData();
                        MyCountDownTimer mc = new MyCountDownTimer(60000, 1000);
                        mc.start();
                    } else {
                        ToastUtils.showShort(yzbean.getMsg());
                    }
                    break;
                case 2:
                    if (cbean == null) {
                        ToastUtils.showShort("该用户没有注册或密码有误！");
                    } else {
                        SharedPreferences mySharedPreferences = getSharedPreferences(
                                "eroadcar", Activity.MODE_PRIVATE);
                        SharedPreferences.Editor editor = mySharedPreferences
                                .edit();
                        editor.putString("PHONE", mobile);
                        editor.commit();

                        Intent intent = new Intent(ChangePwdActivity.this,
                                LoginActivity.class);
                        intent.putExtra("PHONE", mobile);
                        startActivity(intent);
                        ToastUtils.showShort(cbean.getMsg());
                    }
                    break;
                default:
                    break;
            }
        }
//17588566985
        ;
    };

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.getcode_tv:
                if (phone_cet.getText().toString().equals("") || phone_cet.getText().toString().length() != 11) {
                    ToastUtils.showShort("请输入正确的手机号码");
                    return;
                }
                getVerificateCode(phone_cet.getText().toString());
                break;
            case R.id.sure_btn:
                if (!code_cet.getText().toString().equals(CODE)) {
                    ToastUtils.showShort("短信验证码输入错误,请查看后重新输入");
                    return;
                }
                modifyPassword(phone_cet.getText().toString(), pwdt_cet.getText().toString(), pwd_cet
                        .getText().toString(), CODE);
                break;
            case R.id.back_btn:
                onBackPressed();
                break;
        }
    }

    class MyCountDownTimer extends CountDownTimer {
        /**
         * @param millisInFuture    表示以毫秒为单位 倒计时的总数
         *                          <p>
         *                          例如 millisInFuture=1000 表示1秒
         * @param countDownInterval 表示 间隔 多少微秒 调用一次 onTick 方法
         *                          <p>
         *                          例如: countDownInterval =1000 ; 表示每1000毫秒调用一次onTick()
         */
        public MyCountDownTimer(long millisInFuture, long countDownInterval) {
            super(millisInFuture, countDownInterval);
        }

        @Override
        public void onFinish() {
            getcode_tv.setEnabled(true);
            getcode_tv.setText(R.string.getcode);
        }

        @Override
        public void onTick(long millisUntilFinished) {
            getcode_tv.setText(millisUntilFinished / 1000 + "秒后重发");
            getcode_tv.setEnabled(false);
        }
    }
}
