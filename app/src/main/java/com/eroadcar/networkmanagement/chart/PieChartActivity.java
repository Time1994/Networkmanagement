package com.eroadcar.networkmanagement.chart;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.eroadcar.networkmanagement.R;
import com.eroadcar.networkmanagement.activity.BaseActivity;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;


/**
 * Created by Administrator on 2017/5/24 0024.
 */
public class PieChartActivity extends BaseActivity implements View.OnClickListener {
    private Button back_btn;
    private TextView title_tv;
    private PieChart mPieChart1;
    private PieChart mPieChart2;
    private PieChartManager pieChartManager;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pie_chart);
        back_btn = (Button) findViewById(R.id.back_btn);
        title_tv = (TextView) findViewById(R.id.title_tv);
        mPieChart1 = (PieChart) findViewById(R.id.pie_chart1);

        mPieChart1.setCenterText("翼禄新能源");//饼状图中间的文字  
        mPieChart1.setCenterTextColor(getResources().getColor(R.color.green));
        mPieChart2 = (PieChart) findViewById(R.id.pie_chart2);
        mPieChart2.setCenterText("翼禄新能源");//饼状图中间的文字  
        back_btn.setOnClickListener(this);
        title_tv.setText("数据统计");

        int[] colors = {Color.parseColor("#faa74c"), Color.parseColor("#58D4C5"), Color.parseColor("#36a3eb"), Color.parseColor("#cc435f"), Color.parseColor("#f1ea56"),
                Color.parseColor("#f49468"), Color.parseColor("#d5932c"), Color.parseColor("#34b5cc"), Color.parseColor("#8169c6"), Color.parseColor("#ca4561"), Color.parseColor("#fee335")};
        ArrayList<PieEntry> entries = new ArrayList<PieEntry>();
//        for(int i = 0 ;i <= 5; i++){
//            PieEntry pieEntry = new PieEntry(60,"项目" + i + "占比");
//            entries.add(pieEntry);
//        }
//
        PieEntry pieEntry = new PieEntry(80, "项目8占比");
        entries.add(pieEntry);
        PieEntry pieEntrys = new PieEntry(0, "项目8占比");
        entries.add(pieEntrys);
        PieEntry pieEntryss = new PieEntry(20, "项目8占比");
        entries.add(pieEntryss);
        if (entries.size() != 0) {
            pieChartManager = new PieChartManager(mPieChart1, entries, new String[]{"", "", ""}, colors, 12f, Color.GRAY, PieDataSet.ValuePosition.OUTSIDE_SLICE);
            pieChartManager.setHoleEnabled(Color.TRANSPARENT, 40f, Color.TRANSPARENT, 40f);
            pieChartManager.setLegendEnabled(false);
            pieChartManager.setPercentValues(true);
        }


//        List<String> names = new ArrayList<>(); //每个模块的内容描述
//        names.add("销售");
//        names.add("租赁");
//        names.add("维修");
////        names.add("模块四");
//        List<Float> date = new ArrayList<>(); //每个模块的值（占比率）
//        date.add(40f);
//        date.add(35f);
//        date.add(25f);
////        date.add(39.1f);
//        List<Integer> colors = new ArrayList<>(); //每个模块的颜色
//        colors.add(getResources().getColor(R.color.orange));
//        colors.add(getResources().getColor(R.color.green));
//        colors.add(getResources().getColor(R.color.oranger));
////        colors.add(Color.DKGRAY);
//        //饼状图管理类
//        PieChartManager pieChartManager1 = new PieChartManager(mPieChart1);
//        pieChartManager1.setPieChart(names, date, colors);
//        pieChartManager1.setDescription("翼禄新能源");
//
//        PieChartManager pieChartManager2 = new PieChartManager(mPieChart2);
//        pieChartManager2.setSolidPieChart(names, date, colors);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.back_btn:
                onBackPressed();
                break;
        }
    }
}
