package com.eroadcar.networkmanagement.chart;

import android.graphics.Color;


import com.github.mikephil.charting.animation.Easing;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Description;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.formatter.PercentFormatter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by loptop on 2017/6/2.
 */
public class PieChartManager {
    PieChart pieChart;

    private PieChart mChart;
    private List<PieEntry> mEntries;
    private String[] labels;
    private int[] mPieColors;
    private int mValueColor;
    private float mTextSize;
    private PieDataSet.ValuePosition mValuePosition;


    public PieChartManager(PieChart mPieChart) {
        this.pieChart = mPieChart;
        initChart();
    }

    public PieChartManager(PieChart chart, List<PieEntry> entries, String[] labels,
                           int[] chartColor, float textSize, int valueColor, PieDataSet.ValuePosition valuePosition) {
        this.mChart = chart;
        this.mEntries = entries;
        this.labels = labels;
        this.mPieColors = chartColor;
        this.mTextSize = textSize;
        this.mValueColor = valueColor;
        this.mValuePosition = valuePosition;
        initPieChart();
    }

    public PieChartManager(PieChart chart, List<PieEntry> entries, String[] labels,
                           int[] chartColor, float textSize, int valueColor) {
        this(chart, entries, labels, chartColor, textSize, valueColor, PieDataSet.ValuePosition.INSIDE_SLICE);

    }

    private void initPieChart() {
        mChart.setExtraOffsets(5, 10, 5, 5);

        mChart.setDragDecelerationFrictionCoef(0.95f);
        mChart.setDrawCenterText(false);
        mChart.getDescription().setEnabled(false);
        mChart.setRotationAngle(0);
        // enable rotation of the chart by touch
        mChart.setRotationEnabled(true);
        mChart.setHighlightPerTapEnabled(true);
        mChart.setDrawEntryLabels(true);
        setChartData();
        mChart.animateY(1000, Easing.EasingOption.EaseInOutQuad);

        Legend l = mChart.getLegend();
        l.setVerticalAlignment(Legend.LegendVerticalAlignment.TOP);
        l.setHorizontalAlignment(Legend.LegendHorizontalAlignment.LEFT);
        l.setOrientation(Legend.LegendOrientation.VERTICAL);
        l.setDrawInside(false);
        l.setXEntrySpace(7f);
        l.setYEntrySpace(1f);
        l.setYOffset(0f);
        // entry label styling
        mChart.setEntryLabelColor(mValueColor);
        mChart.setEntryLabelTextSize(mTextSize);
        mChart.setExtraOffsets(10, 10, 10, 10);
    }

    public void setHoleDisenabled() {
        mChart.setDrawHoleEnabled(false);
    }

    /**
     * 中心圆是否可见
     *
     * @param holeColor   中心圆颜色
     * @param holeRadius  半径
     * @param transColor  透明圆颜色
     * @param transRadius 透明圆半径
     */
    public void setHoleEnabled(int holeColor, float holeRadius, int transColor, float transRadius) {
        mChart.setDrawHoleEnabled(true);
        mChart.setHoleColor(holeColor);
        mChart.setTransparentCircleColor(transColor);
        mChart.setTransparentCircleAlpha(110);
        mChart.setHoleRadius(holeRadius);
        mChart.setTransparentCircleRadius(transRadius);
    }

    private void setChartData() {
        PieDataSet dataSet = new PieDataSet(mEntries, "");
        dataSet.setSliceSpace(0f);
        dataSet.setSelectionShift(5f);
//        dataSet.setEntryLabelsColor(mValueColor);
        dataSet.setColors(mPieColors);
        //dataSet.setSelectionShift(0f);
        dataSet.setYValuePosition(mValuePosition);
        dataSet.setXValuePosition(mValuePosition);
        dataSet.setValueLineColor(mValueColor);
        dataSet.setSelectionShift(15f);
        dataSet.setValueLinePart1Length(0.6f);
        dataSet.setValueLineColor(mValueColor);
        PieData data = new PieData(dataSet);
        data.setValueFormatter(new PercentFormatter());
        data.setValueTextSize(mTextSize);
        data.setValueTextColor(mValueColor);
        data.setValueTextColor(mValueColor);
        mChart.setData(data);
        // undo all highlights
        mChart.highlightValues(null);
        mChart.invalidate();
    }

    /**
     * <p>说明文字是否可见</p>
     *
     * @param enabled true 可见,默认可见
     */
    public void setLegendEnabled(boolean enabled) {
        mChart.getLegend().setEnabled(enabled);
        mChart.invalidate();
    }

    public void setPercentValues(boolean showPercent) {
        mChart.setUsePercentValues(showPercent);
    }


    private void initChart() {
        pieChart.setHoleRadius(50f);//半径    
        pieChart.setTransparentCircleRadius(30f);// 半透明圈    
        pieChart.setDrawCenterText(true);//饼状图中间可以添加文字    
        pieChart.setDrawHoleEnabled(true);
        pieChart.setRotationAngle(90);// 初始旋转角度    
        pieChart.setRotationEnabled(true);// 可以手动旋转    
        pieChart.setUsePercentValues(true);//显示成百分比  

        Legend legend = pieChart.getLegend();
        legend.setVerticalAlignment(Legend.LegendVerticalAlignment.TOP);
        legend.setHorizontalAlignment(Legend.LegendHorizontalAlignment.RIGHT);
        legend.setOrientation(Legend.LegendOrientation.VERTICAL);
        legend.setDrawInside(false);
        legend.setXEntrySpace(7f);
        legend.setYEntrySpace(0f);
        legend.setYOffset(0f);

        pieChart.animateXY(1000, 1000);//设置动画  
    }

    /**
     * 设置饼状图
     *
     * @param name   饼状图分类的名字
     * @param date   数值
     * @param colors 颜色集合
     */
    public void setPieChart(List<String> name, List<Float> date, List<Integer> colors) {
        List<PieEntry> yValue = new ArrayList<>();
        for (int i = 0; i < date.size(); i++) {
            PieEntry entry = new PieEntry(date.get(i), name.get(i));
            yValue.add(entry);
        }
        PieDataSet set = new PieDataSet(yValue, "");
        set.setDrawValues(true);
        set.setValueTextSize(12);
        set.setColors(colors);
        set.setValueTextColor(Color.WHITE);

        PieData data = new PieData(set);
        pieChart.setData(data);
        pieChart.invalidate(); // refresh
    }

    /**
     * 设实心饼状图
     *
     * @param name   饼状图分类的名字
     * @param date   数值
     * @param colors 颜色集合
     */
    public void setSolidPieChart(List<String> name, List<Float> date, List<Integer> colors) {

        pieChart.setHoleRadius(0);//实心圆   
        pieChart.setTransparentCircleRadius(0);// 半透明圈  
        pieChart.setDrawCenterText(false);//饼状图中间不可以添加文字  

        List<PieEntry> yValue = new ArrayList<>();
        for (int i = 0; i < date.size(); i++) {
            PieEntry entry = new PieEntry(date.get(i), name.get(i));
            yValue.add(entry);
        }
        PieDataSet set = new PieDataSet(yValue, "");
        set.setDrawValues(true);
        set.setValueTextSize(12);
        set.setColors(colors);

        set.setValueTextColor(Color.WHITE);
        PieData data = new PieData(set);
        pieChart.setData(data);
        pieChart.invalidate(); // refresh
    }

    /**
     * 设置饼状图中间的描述内容
     *
     * @param str
     */
    public void setCenterDescription(String str, int color) {
        pieChart.setCenterText(str);
        pieChart.setCenterTextColor(color);
        pieChart.setCenterTextSize(12);
        pieChart.invalidate();
    }

    /**
     * 设置描述信息
     *
     * @param str
     */
    public void setDescription(String str) {
        Description description = new Description();
        description.setText(str);
        pieChart.setDescription(description);
        pieChart.invalidate();
    }
}
