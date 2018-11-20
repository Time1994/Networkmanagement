package com.eroadcar.networkmanagement.activity;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.gson.GsonRequest;
import com.android.volley.toolbox.gson.RequesListener;
import com.eroadcar.networkmanagement.R;
import com.eroadcar.networkmanagement.adapter.FunctionAdapter;
import com.eroadcar.networkmanagement.bean.CommonBean;
import com.eroadcar.networkmanagement.bean.HomeBean;
import com.eroadcar.networkmanagement.bean.RankBean;
import com.eroadcar.networkmanagement.bean.UserBean;
import com.eroadcar.networkmanagement.utils.Constant;
import com.eroadcar.networkmanagement.utils.ToastUtils;

import java.time.Month;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;

import cn.sharp.android.ncr.NameCardRec;
import q.rorbin.badgeview.Badge;
import q.rorbin.badgeview.QBadgeView;

public class MainActivity extends BaseActivity implements View.OnClickListener {
    private GridView function_gv;
    private ImageView service_iv;
    private Button back_btn, other_btn;
    private TextView title_tv, call_tv, date_tv, sale_tv, salemoney_tv, service_tv, servicenum_tv, rent_tv, rentmoney_tv, add_tv, addnum_tv;
    private RadioGroup as_rg;
    private RadioButton day_rb, month_rb;
    private LinearLayout sale_ll, service_ll, rent_ll, add_ll;
    private String TYPE = "date", time = "";
    private CommonBean<RankBean> bean;
    private Calendar c = Calendar.getInstance();
    private int Year;
    private int Day;
    private int Month;
    private long exitTime = 0;
    private ArrayList<HomeBean> homeBeans;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        function_gv = (GridView) findViewById(R.id.function_gv);
        service_iv=(ImageView)findViewById(R.id.service_iv);
        back_btn = (Button) findViewById(R.id.back_btn);
        other_btn = (Button) findViewById(R.id.other_btn);
        title_tv = (TextView) findViewById(R.id.title_tv);
        call_tv = (TextView) findViewById(R.id.call_tv);
        date_tv = (TextView) findViewById(R.id.date_tv);
        sale_tv = (TextView) findViewById(R.id.sale_tv);
        salemoney_tv = (TextView) findViewById(R.id.salemoney_tv);
        service_tv = (TextView) findViewById(R.id.service_tv);
        servicenum_tv = (TextView) findViewById(R.id.servicenum_tv);
        rent_tv = (TextView) findViewById(R.id.rent_tv);
        rentmoney_tv = (TextView) findViewById(R.id.rentmoney_tv);
        add_tv = (TextView) findViewById(R.id.add_tv);
        addnum_tv = (TextView) findViewById(R.id.addnum_tv);
        as_rg = (RadioGroup) findViewById(R.id.as_rg);
        day_rb = (RadioButton) findViewById(R.id.day_rb);
        month_rb = (RadioButton) findViewById(R.id.month_rb);
        sale_ll = (LinearLayout) findViewById(R.id.sale_ll);
        service_ll = (LinearLayout) findViewById(R.id.service_ll);
        rent_ll = (LinearLayout) findViewById(R.id.rent_ll);
        add_ll = (LinearLayout) findViewById(R.id.add_ll);
        Year = c.get(Calendar.YEAR);
        Month = c.get(Calendar.MONTH);
        Day = c.get(Calendar.DATE);
        back_btn.setOnClickListener(this);

        title_tv.setText(application.getUserBean().getMg_shopname().toString());
        call_tv.setText(application.getUserBean().getMg_name() + "(店长)您好！一天的工作开始了,加油！");
        time = Year + Month + Day + "";
        date_tv.setText(Year + "-" + Month + "-" + Day);
        as_rg.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup arg0, int arg1) {
                if (arg1 == R.id.day_rb) {
                    TYPE = "date";
                    time = Year + Month + Day + "";
                } else if (arg1 == R.id.month_rb) {
                    TYPE = "month";
                    time = Year + Month + "";
                }
                reqStatisticsForManager(TYPE, time, application.getUserBean().getMg_shopcode());
            }
        });
        reqStatisticsForManager(TYPE, time, application.getUserBean().getMg_shopcode());
        //拖拽。。。。。。。。
        QBadgeView badgeView = new QBadgeView(this);
        new QBadgeView(this).bindTarget(other_btn).setBadgeText("99+").setShowShadow(false).setOnDragStateChangedListener(new Badge.OnDragStateChangedListener() {
            @Override
            public void onDragStateChanged(int dragState, Badge badge, View targetView) {

            }
        });
        setHomeBean();
        function_gv.setAdapter(new FunctionAdapter(MainActivity.this,homeBeans));
        function_gv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (homeBeans.get(position).getType().equals("sale")) {
                    intent(SaleManagerActivity.class);
                }
                if (homeBeans.get(position).getType().equals("wei")) {
                    intent(NameCardRec.class);
                }
            }    });


    }

    private void reqStatisticsForManager(String type, String date, String mg_shopcode) {
        loadingDialog.setMessage("正在获取...");
        loadingDialog.dialogShow();
        String url = Constant.reqStatisticsForManager;
        System.out.println("url=" + url);
        HashMap<String, String> map = new HashMap<String, String>();
        map.put("type", type);
        map.put("date", date);
        map.put("mg_shopcode", mg_shopcode);
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
        GsonRequest<CommonBean<RankBean>> requtst = new GsonRequest<CommonBean<RankBean>>(Request.Method
                .POST,
                url, listener_reqStatisticsForManager);
        requtst.setHashmap(map);
        mRequestQueue.add(requtst);
    }

    private RequesListener<CommonBean<RankBean>> listener_reqStatisticsForManager = new
            RequesListener<CommonBean<RankBean>>() {
                @Override
                public void onResponse(CommonBean<RankBean> arg0) {
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
                    if (bean != null && bean.getState().equals("success")) {
                        sale_tv.setText("销售" + bean.getData().getXs_order_count() + " 辆");
                        salemoney_tv.setText("¥" + bean.getData().getXs_order_price() + " 元");
                        rent_tv.setText("租赁" + bean.getData().getZl_order_count() + " 辆");
                        rentmoney_tv.setText("¥" + bean.getData().getZl_order_price() + " 元");
                        service_tv.setText("微修" + bean.getData().getWx_order_count() + " 辆");
                        servicenum_tv.setText("¥" + bean.getData().getWx_order_price() + " 元");
                        addnum_tv.setText(bean.getData().getQk_person_count() + " 人");
                    } else {

                    }
                    break;

            }
        }
    };

    private void setHomeBean() {
        String role = application.getUserBean().getMg_role_ids();

        homeBeans = new ArrayList<>();

        HomeBean bean = new HomeBean();
        bean.setImageId(R.mipmap.icon_salead);
        bean.setContent("销售管理");
        bean.setNewsnum(1);
        bean.setType("sale");

        HomeBean bean1 = new HomeBean();
        bean1.setImageId(R.mipmap.icon_servicead);
        bean1.setContent("维修管理");
        bean1.setNewsnum(0);
        bean1.setType("wei");

        HomeBean bean2 = new HomeBean();
        bean2.setImageId(R.mipmap.icon_libraryadd);
        bean2.setContent("仓库管理");
        bean2.setNewsnum(0);
        bean2.setType("ku");

        HomeBean bean3 = new HomeBean();
        bean3.setImageId(R.mipmap.icon_personad);
        bean3.setContent("员工管理");
        bean3.setNewsnum(0);
        bean3.setType("employee");

        HomeBean bean4 = new HomeBean();
        bean4.setImageId(R.mipmap.icon_carad);
        bean4.setContent("车辆管理");
        bean4.setNewsnum(0);
        bean4.setType("car");

        HomeBean bean5 = new HomeBean();
        bean5.setImageId(R.mipmap.icon_rank);
        bean5.setContent("数据统计");
        bean5.setNewsnum(0);
        bean5.setType("tong");


        if (role.contains("4")) {
            service_ll.setVisibility(View.GONE);
            add_ll.setVisibility(View.GONE);
            homeBeans.add(bean);
            homeBeans.add(bean5);
        }

        if (role.contains("3")) {
            service_iv.setImageResource(R.mipmap.icon_paim);
            service_tv.setText("当前排名");
            servicenum_tv.setText("");

            homeBeans.clear();

            homeBeans.add(bean);
            homeBeans.add(bean5);
        }

        if (role.contains("2")) {
            service_ll.setVisibility(View.GONE);
           add_ll.setVisibility(View.VISIBLE);

            homeBeans.clear();

            homeBeans.add(bean);
            homeBeans.add(bean3);
            homeBeans.add(bean5);
        }

        if (role.contains("1")) {
            service_ll.setVisibility(View.VISIBLE);
           add_ll.setVisibility(View.VISIBLE);

            homeBeans.clear();

            homeBeans.add(bean);
            homeBeans.add(bean1);
            homeBeans.add(bean2);
            homeBeans.add(bean3);
            homeBeans.add(bean4);
            homeBeans.add(bean5);
        }
    }


    @Override
    public void onBackPressed() {
        exitApp();
    }

    /**
     * 退出程序
     */
    private void exitApp() {
        // 判断2次点击事件时间
        if ((System.currentTimeMillis() - exitTime) > 2000) {
            ToastUtils.showShort(R.string.exit_app);
            exitTime = System.currentTimeMillis();
        } else {
            application.exit();
        }
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.back_btn:
                intent(SettingActivity.class);
            break;
        }
    }
}
