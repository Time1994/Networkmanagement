package com.eroadcar.networkmanagement.activity;

import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.gson.GsonRequest;
import com.android.volley.toolbox.gson.RequesListener;
import com.eroadcar.networkmanagement.R;
import com.eroadcar.networkmanagement.bean.CommonBean;
import com.eroadcar.networkmanagement.ocrutils.OcrActivity;
import com.eroadcar.networkmanagement.utils.Constant;
import com.eroadcar.networkmanagement.utils.ToastUtils;

import java.util.HashMap;

public class SettingActivity extends BaseActivity implements View.OnClickListener {
    private LinearLayout data_ll, build_ll, power_ll, tool_ll, tel_ll, update_ll, about_ll, advise_ll;
    private Button back_btn, logout_btn;
    private TextView title_tv;
    private CommonBean commobBean;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);
        data_ll = (LinearLayout) findViewById(R.id.data_ll);
        build_ll = (LinearLayout) findViewById(R.id.build_ll);
        power_ll = (LinearLayout) findViewById(R.id.power_ll);
        tool_ll = (LinearLayout) findViewById(R.id.tool_ll);
        tel_ll = (LinearLayout) findViewById(R.id.tel_ll);
        update_ll = (LinearLayout) findViewById(R.id.update_ll);
        about_ll = (LinearLayout) findViewById(R.id.about_ll);
        advise_ll = (LinearLayout) findViewById(R.id.advise_ll);
        back_btn = (Button) findViewById(R.id.back_btn);
        logout_btn = (Button) findViewById(R.id.logout_btn);
        title_tv = (TextView) findViewById(R.id.title_tv);
        data_ll.setOnClickListener(this);
        build_ll.setOnClickListener(this);
        power_ll.setOnClickListener(this);
        tool_ll.setOnClickListener(this);
        tel_ll.setOnClickListener(this);
        update_ll.setOnClickListener(this);
        about_ll.setOnClickListener(this);
        advise_ll.setOnClickListener(this);
        back_btn.setOnClickListener(this);
        logout_btn.setOnClickListener(this);
        title_tv.setText("设置");
    }

    private void logOut() {
        loadingDialog.setMessage("正在退出登录...");
        loadingDialog.dialogShow();
        String url = Constant.logout;
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

        GsonRequest<CommonBean> requtst = new GsonRequest<CommonBean>(
                Request.Method.POST, url, listener_getInshopCars);
        requtst.setHashmap(map);
        mRequestQueue.add(requtst);
    }

    private RequesListener<CommonBean> listener_getInshopCars = new RequesListener<CommonBean>() {
        @Override
        public void onResponse(CommonBean arg0) {
            // TODO Auto-generated method stub
            commobBean = arg0;
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
    private Handler mHandler = new Handler() {
        public void handleMessage(android.os.Message msg) {
            switch (msg.what) {
                case 1:
                    if (commobBean.getState().equals("success")) {
                        intent(LoginActivity.class);
                        finish();
                    }
                    ToastUtils.showShort(commobBean.getMsg());
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
            case R.id.data_ll:
                intent(DataInfoActivity.class);
                break;
            case R.id.build_ll:
                intent(BuildActivity.class);
                break;
            case R.id.power_ll:
                intent(PermissionsActivity.class);
                break;
            case R.id.tool_ll:
                intent(OcrActivity.class);
                break;
            case R.id.tel_ll:
                if (null != popupwindow && popupwindow.isShowing()) {
                    popupwindow.dismiss();
                } else {
                    initmPopupWindowView_photo();
                    popupwindow.showAtLocation(v, Gravity.BOTTOM, 0, 0);
                }
                break;
            case R.id.update_ll:
                intent(UpdateActivity.class);
                break;
            case R.id.about_ll:
                intent(AboutActivity.class);
                break;
            case R.id.advise_ll:
                intent(AdviseActivity.class);
                break;
            case R.id.back_btn:
                onBackPressed();
                break;
            case R.id.logout_btn:
                showDialogMessage(SettingActivity.this, "确认退出账号吗？",
                        new View.OnClickListener() {

                            @Override
                            public void onClick(View v) {
                                // TODO Auto-generated method stub
                                dialogMessage.dismiss();

                                logOut();
                            }
                        });
                break;
            case R.id.btn_cancel:
                popupwindow.dismiss();
                break;
            case R.id.btn_pick_photo:
                intentCall(btn_pick_photo.getText().toString());
                popupwindow.dismiss();
                break;
        }

    }

    private View customView;
    private PopupWindow popupwindow;
    private Button btn_take_photo, btn_pick_photo, btn_cancel;

    // 照相弹出层
    public void initmPopupWindowView_photo() {
        // 获取自定义布局文件pop.xml的视图
        customView = getLayoutInflater().inflate(R.layout.pop_photo_choice,
                null, false);
        // 创建PopupWindow实例,200,150分别是宽度和高度
        popupwindow = new PopupWindow(customView,
                LinearLayout.LayoutParams.FILL_PARENT, getResources()
                .getDimensionPixelSize(R.dimen.thundred), true);
        // 设置动画效果 [R.style.AnimationFade 是自己事先定义好的
        popupwindow.setAnimationStyle(R.style.AnimationPhoto);
        // 获取控件
        btn_take_photo = (Button) customView.findViewById(R.id.btn_take_photo);// 拍照
        btn_pick_photo = (Button) customView.findViewById(R.id.btn_pick_photo);// 选中相册
        btn_cancel = (Button) customView.findViewById(R.id.btn_cancel);// 取消
        btn_take_photo.setVisibility(View.GONE);
        btn_cancel.setText("取消");
        btn_cancel.setTextColor(Color.parseColor("#00A8FF"));
        btn_pick_photo.setText("400 9200 665");
        btn_cancel.setOnClickListener(this);
        btn_pick_photo.setOnClickListener(this);

        backgroundAlpha(0.5f);
        popupwindow.setOnDismissListener(new poponDismissListener());

        // 这个是为了点击“返回Back”也能使其消失，并且并不会影响你的背景
        popupwindow.setBackgroundDrawable(new BitmapDrawable());
        // 使其聚集
        popupwindow.setFocusable(true);
        popupwindow.setTouchable(true); // 设置PopupWindow可触摸
        // 设置允许在外点击消失
        popupwindow.setOutsideTouchable(true);
        // 刷新状态
        popupwindow.update();
    }
}
