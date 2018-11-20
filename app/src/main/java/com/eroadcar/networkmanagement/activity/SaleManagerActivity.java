package com.eroadcar.networkmanagement.activity;

import android.animation.Animator;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.Point;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.preference.PreferenceManager;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Display;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.baidu.aip.asrwakeup3.core.recog.MyRecognizer;
import com.baidu.aip.asrwakeup3.core.recog.listener.ChainRecogListener;
import com.baidu.aip.asrwakeup3.core.recog.listener.IRecogListener;
import com.baidu.aip.asrwakeup3.core.recog.listener.MessageStatusRecogListener;
import com.baidu.aip.asrwakeup3.core.util.MyLogger;
import com.baidu.aip.asrwakeup3.uiasr.params.CommonRecogParams;
import com.baidu.aip.asrwakeup3.uiasr.params.OnlineRecogParams;
import com.baidu.voicerecognition.android.ui.BaiduASRDigitalDialog;
import com.baidu.voicerecognition.android.ui.DigitalDialogInput;
import com.eroadcar.networkmanagement.R;
import com.eroadcar.networkmanagement.adapter.ManagerAdapter;
import com.eroadcar.networkmanagement.bean.HomeBean;
import com.eroadcar.networkmanagement.chart.PieChartActivity;
import com.eroadcar.networkmanagement.pop.KickBackAnimator;
import com.eroadcar.networkmanagement.recog.ActivityUiDialog;

import java.util.ArrayList;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.schedulers.Schedulers;

public class SaleManagerActivity extends BaseActivity implements View.OnClickListener {
    private Button back_btn;
    private TextView title_tv;
    private GridView img_gv;
    private ArrayList<HomeBean> homeBeans;
    private String type;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sale_manager);
        title_tv = (TextView) findViewById(R.id.title_tv);
        back_btn = (Button) findViewById(R.id.back_btn);

        img_gv = (GridView) findViewById(R.id.img_gv);

        // DEMO集成步骤 1.1 新建一个回调类，识别引擎会回调这个类告知重要状态和识别结果
        IRecogListener listener = new MessageStatusRecogListener(null);
        // DEMO集成步骤 1.2 初始化：new一个IRecogListener示例 & new 一个 MyRecognizer 示例
        myRecognizer = new MyRecognizer(this, listener);

        title_tv.setText("销售管理");

        back_btn.setOnClickListener(this);

        setHomeBean();
        img_gv.setAdapter(new ManagerAdapter(SaleManagerActivity.this, homeBeans));

        img_gv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                if (homeBeans.get(i).getType().equals("chexing")) {
               intent(PieChartActivity.class);
////                    type = "chexing";
//                  initmPopupWindowView_photo();
//                   popupwindow.showAtLocation(view, Gravity.BOTTOM, 0, 0);
                 intent(ActivityUiDialog.class);

//                    rec();
                } else if (homeBeans.get(i).getType().equals("kehu")) {
//                    intent(ZhengxActivity.class);
                    type = "kehu";
                    initmPopupWindowView_photo();
                    popupwindow.showAtLocation(view, Gravity.BOTTOM, 0, 0);
                }
            }
        });
    }

    Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 1:

                    break;
                case -1:
                    break;
                default:
                    break;
            }
        }
    };

    private void setHomeBean() {
        String role = application.getUserBean().getMg_role_ids();

        homeBeans = new ArrayList<>();

        HomeBean bean = new HomeBean();
        bean.setImageId(R.mipmap.icon_customer);
        bean.setContent("客户管理");
        bean.setNewsnum(0);
        bean.setType("kehu");

        HomeBean bean1 = new HomeBean();
        bean1.setImageId(R.mipmap.icon_updatere);
        bean1.setContent("更新记录");
        bean1.setNewsnum(0);
        bean1.setType("更新");

        HomeBean bean2 = new HomeBean();
        bean2.setImageId(R.mipmap.icon_moneymanager);
        bean2.setContent("收银管理");
        bean2.setNewsnum(0);
        bean2.setType("shouyin");

        HomeBean bean3 = new HomeBean();
        bean3.setImageId(R.mipmap.icon_introduce);
        bean3.setContent("车型介绍");
        bean3.setNewsnum(0);
        bean3.setType("chexing");


        if (role.contains("4")) {
            homeBeans.clear();

            homeBeans.add(bean2);
            homeBeans.add(bean);
            homeBeans.add(bean3);
        }

        if (role.contains("3")) {
            homeBeans.clear();

            homeBeans.add(bean);
            homeBeans.add(bean3);
        }

        if (role.contains("2")) {
            homeBeans.clear();

            homeBeans.add(bean1);
            homeBeans.add(bean);
            homeBeans.add(bean3);
        }

        if (role.contains("1")) {
            homeBeans.clear();

            homeBeans.add(bean);
            homeBeans.add(bean1);
            homeBeans.add(bean2);
            homeBeans.add(bean3);
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.back_btn:
                onBackPressed();
                break;
            case R.id.ll_close:
                if (popupwindow.isShowing()) {
                    closeAnimation(contentView);
                }
                break;
        }
    }

    private View customView;
    private PopupWindow popupwindow;
    private RelativeLayout contentView;
    LinearLayout xinz_ll, zhengx_ll, wans_ll, kehu_ll, zulin_ll, dingd_ll, ll_close;
    ImageView xinz_iv, zhengx_iv, wans_iv, kehu_iv, zulin_iv, dingd_iv;
    private TextView xinz_tv, zhengx_tv, wans_tv, kehu_tv, zulin_tv, dingd_tv;

    // 照相弹出层
    public void initmPopupWindowView_photo() {
        // 获取自定义布局文件pop.xml的视图
        customView = getLayoutInflater().inflate(R.layout.pop_choice,
                null, false);
        // 创建PopupWindow实例,200,150分别是宽度和高度
        popupwindow = new PopupWindow(customView,
                LinearLayout.LayoutParams.FILL_PARENT, getResources()
                .getDimensionPixelSize(R.dimen.thundredf), true);
        // 获取控件
        contentView = (RelativeLayout) customView.findViewById(R.id.contentView);
        xinz_ll = (LinearLayout) customView.findViewById(R.id.xinz_ll);
        zhengx_ll = (LinearLayout) customView.findViewById(R.id.zhengx_ll);
        wans_ll = (LinearLayout) customView.findViewById(R.id.wans_ll);
        kehu_ll = (LinearLayout) customView.findViewById(R.id.kehu_ll);
        zulin_ll = (LinearLayout) customView.findViewById(R.id.zulin_ll);
        dingd_ll = (LinearLayout) customView.findViewById(R.id.dingd_ll);
        ll_close = (LinearLayout) customView.findViewById(R.id.ll_close);
        xinz_iv = (ImageView) customView.findViewById(R.id.xinz_iv);
        zhengx_iv = (ImageView) customView.findViewById(R.id.zhengx_iv);
        wans_iv = (ImageView) customView.findViewById(R.id.wans_iv);
        kehu_iv = (ImageView) customView.findViewById(R.id.kehu_iv);
        zulin_iv = (ImageView) customView.findViewById(R.id.zulin_iv);
        dingd_iv = (ImageView) customView.findViewById(R.id.dingd_iv);
        xinz_tv = (TextView) customView.findViewById(R.id.xinz_tv);
        zhengx_tv = (TextView) customView.findViewById(R.id.zhengx_tv);
        wans_tv = (TextView) customView.findViewById(R.id.wans_tv);
        kehu_tv = (TextView) customView.findViewById(R.id.kehu_tv);
        zulin_tv = (TextView) customView.findViewById(R.id.zulin_tv);
        dingd_tv = (TextView) customView.findViewById(R.id.dingd_tv);
        ll_close.setOnClickListener(this);

        dingd_ll.setVisibility(View.INVISIBLE);

        if (type.equals("kehu")) {
            xinz_iv.setImageResource(R.mipmap.icon_add);
            zhengx_iv.setImageResource(R.mipmap.icon_paim);
            wans_iv.setImageResource(R.mipmap.icon_about);
            dingd_ll.setVisibility(View.INVISIBLE);
            zulin_ll.setVisibility(View.INVISIBLE);
        }

        showAnimation(contentView);
        backgroundAlpha(1f);
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

    /**
     * 显示进入动画效果
     *
     * @param layout
     */
    private void showAnimation(ViewGroup layout) {
        //遍历根试图下的一级子试图
        for (int i = 0; i < layout.getChildCount(); i++) {
            final View child = layout.getChildAt(i);
//            //忽略关闭组件
//            if (child.getId() == R.id.ll_close) {
//                continue;
//            }
            //设置所有一级子试图的点击事件
            child.setOnClickListener(this);
            child.setVisibility(View.INVISIBLE);
            //延迟显示每个子试图(主要动画就体现在这里)
            Observable.timer(i * 50, TimeUnit.MILLISECONDS)
                    .subscribeOn(Schedulers.newThread())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new Action1<Long>() {
                        @Override
                        public void call(Long aLong) {
                            child.setVisibility(View.VISIBLE);
                            ValueAnimator fadeAnim = ObjectAnimator.ofFloat(child, "translationY", 600, 0);
                            fadeAnim.setDuration(300);
                            KickBackAnimator kickAnimator = new KickBackAnimator();
                            kickAnimator.setDuration(150);
                            fadeAnim.setEvaluator(kickAnimator);
                            fadeAnim.start();
                        }
                    });
        }

    }

    /**
     * 关闭动画效果
     *
     * @param layout
     */
    private void closeAnimation(ViewGroup layout) {
        for (int i = 0; i < layout.getChildCount(); i++) {
            final View child = layout.getChildAt(i);
//            if (child.getId() == R.id.ll_close) {
//                continue;
//            }
            Observable.timer((layout.getChildCount() - i - 1) * 30, TimeUnit.MILLISECONDS)
                    .subscribeOn(Schedulers.newThread())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new Action1<Long>() {
                        @Override
                        public void call(Long aLong) {
                            child.setVisibility(View.VISIBLE);
                            ValueAnimator fadeAnim = ObjectAnimator.ofFloat(child, "translationY", 0, 600);
                            fadeAnim.setDuration(200);
                            KickBackAnimator kickAnimator = new KickBackAnimator();
                            kickAnimator.setDuration(100);
                            fadeAnim.setEvaluator(kickAnimator);
                            fadeAnim.start();
                            fadeAnim.addListener(new Animator.AnimatorListener() {

                                @Override
                                public void onAnimationStart(Animator animation) {
                                }

                                @Override
                                public void onAnimationRepeat(Animator animation) {
                                }

                                @Override
                                public void onAnimationEnd(Animator animation) {
                                    child.setVisibility(View.INVISIBLE);

                                }

                                @Override
                                public void onAnimationCancel(Animator animation) {
                                }
                            });
                        }
                    });


            if (child.getId() == R.id.ll_close) {
                Observable.timer((layout.getChildCount() - i) * 30 + 80, TimeUnit.MILLISECONDS)
                        .subscribeOn(Schedulers.newThread())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(new Action1<Long>() {
                            @Override
                            public void call(Long aLong) {
                                popupwindow.dismiss();
                            }
                        });
            }
        }

    }

    /*
     * Api的参数类，仅仅用于生成调用START的json字符串，本身与SDK的调用无关
     */
    private CommonRecogParams apiParams;
    /**
     * 识别控制器，使用MyRecognizer控制识别的流程
     */
    protected MyRecognizer myRecognizer;

    private ChainRecogListener chainRecogListener;

    private DigitalDialogInput input;

    private void rec() {
        /**
         * 有2个listner，一个是用户自己的业务逻辑，如MessageStatusRecogListener。另一个是UI对话框的。
         * 使用这个ChainRecogListener把两个listener和并在一起
         */
        chainRecogListener = new ChainRecogListener();
        // DigitalDialogInput 输入 ，MessageStatusRecogListener可替换为用户自己业务逻辑的listener
        chainRecogListener.addListener(new MessageStatusRecogListener(null));
        myRecognizer.setEventListener(chainRecogListener); // 替换掉原来的listener

        final Map<String, Object> params = fetchParams();
        System.out.println("params" + params);
        // BaiduASRDigitalDialog的输入参数
        input = new DigitalDialogInput(myRecognizer, chainRecogListener, params);
        BaiduASRDigitalDialog.setInput(input); // 传递input信息，在BaiduASRDialog中读取,
        Intent intent = new Intent(this, BaiduASRDigitalDialog.class);

        // 修改对话框样式
        // intent.putExtra(BaiduASRDigitalDialog.PARAM_DIALOG_THEME, BaiduASRDigitalDialog.THEME_ORANGE_DEEPBG);
        startActivityForResult(intent, 2);
    }

    public Map<String, Object> fetchParams() {
        apiParams = new OnlineRecogParams();
        apiParams.initSamplePath(this);

        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(this);
        //  上面的获取是为了生成下面的Map， 自己集成时可以忽略
        Map<String, Object> params = apiParams.fetch(sp);
        //  集成时不需要上面的代码，只需要params参数。
        return params;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == 2) {
            String message = "对话框的识别结果：";
            if (resultCode == RESULT_OK) {
                ArrayList results = data.getStringArrayListExtra("results");
                if (results != null && results.size() > 0) {
                    message += results.get(0);
                }
                System.out.println("结果是：" + results);
            } else {
                message += "没有结果";
            }
            MyLogger.info(message);
        }

    }

}
