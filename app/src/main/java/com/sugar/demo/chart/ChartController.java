package com.sugar.demo.chart;

import android.graphics.Color;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.formatter.ValueFormatter;
import com.github.mikephil.charting.utils.ViewPortHandler;
import java.text.DecimalFormat;

public class ChartController<T extends ChartModle> {

    private static final int COLOR_BACKGROUND = Color.parseColor("#FFFFFF");//背景色
    private static final int COLOR_GRID_BACKGROUND = Color.parseColor("#FFFFFF");//网格背景
    private static final int COLOR_BORDER = Color.parseColor("#7F8C8D");//边框背景
    private static final int COLOR_TEXT = Color.parseColor("#2C3E50");//字体颜色


    private static final int COLOR_FILL = Color.parseColor("#1FE4BD");//填充色#C6E2FF
    private static final int COLOR_SHAPE = Color.parseColor("#0074FF");//蓝色

    private static final int COLOR_VALUE_TEXT = Color.parseColor("#FF4081");//


    private static final float SIZE_CIRCLE = 4f;
    private static final float SIZE_LINE_WIDTH = 1.5f;

    private static final int INDEX = 0;//曲线索引
    private static final int MAX_NUM = 6;//X最多显示点数

    private LineChart mLineChart;

    public ChartController(LineChart chart) {
        this.mLineChart = chart;
    }

    public void init() {
        mLineChart.setDescription("");// 数据描述
        mLineChart.setNoDataTextDescription("");//没有数据时显示

        mLineChart.setDrawBorders(true);  //是否在折线图上添加边框
        mLineChart.setBorderColor(COLOR_BORDER);//边框颜色
        mLineChart.setBackgroundColor(COLOR_BACKGROUND);// 设置背景
        mLineChart.setDrawGridBackground(true); // 是否显示表格
        mLineChart.setGridBackgroundColor(COLOR_GRID_BACKGROUND);//表格颜色

        mLineChart.setTouchEnabled(true); // 设置是否可以触摸
        mLineChart.setDragEnabled(true);// 是否可以拖拽
        mLineChart.setScaleEnabled(true);// 是否可以缩放
        mLineChart.setPinchZoom(false);// if disabled, scaling can be done on x- and y-axis separately

        XAxis x = mLineChart.getXAxis();
        x.setEnabled(true);
        x.setPosition(XAxis.XAxisPosition.BOTTOM);
        x.setTextColor(COLOR_TEXT);
        x.setPosition(XAxis.XAxisPosition.BOTTOM);
        x.setAvoidFirstLastClipping(true);

        YAxis y = mLineChart.getAxisLeft();
        y.setLabelCount(6, false);
        y.setStartAtZero(true);
        y.setTextColor(COLOR_TEXT);
        y.setPosition(YAxis.YAxisLabelPosition.OUTSIDE_CHART);
        y.setDrawGridLines(false);
        y.setAxisMaxValue(40f); // 最大值
        y.setAxisMinValue(0f);// 最小值
        y.setStartAtZero(false);// 不一定要从0开始


        mLineChart.getAxisRight().setEnabled(false);

        initDataSet();
    }

    /**
     * 增加一条空数据
     */
    private void initDataSet() {
        LineData data = new LineData();
        data.addDataSet(addLineDataSet());
        mLineChart.setData(data);
    }

    /**
     * 初始化数据
     *
     * @return
     */
    private LineDataSet addLineDataSet() {
        LineDataSet set = new LineDataSet(null, "温度");

        // get the legend (only possible after setting data)
        Legend mLegend = mLineChart.getLegend(); // 设置比例图标示，就是那个一组y的value的
        // modify the legend ...
        // mLegend.setPosition(LegendPosition.LEFT_OF_CHART);
        // mLegend.setForm(LegendForm.CIRCLE);// 样式
        // mLegend.setFormSize(6f);// 字体
        // mLegend.setTextColor(Color.WHITE);// 颜色
        mLegend.setEnabled(false);

        //用y轴的集合来设置参数
        set.setLineWidth(SIZE_LINE_WIDTH); // 线宽
        set.setCircleSize(SIZE_CIRCLE);// 显示的圆形大小
        set.setColor(COLOR_SHAPE);// 显示颜色
        set.setCircleColor(COLOR_SHAPE);// 圆形的颜色
        set.setHighLightColor(Color.WHITE); // 高亮的线的颜色
        set.setDrawHorizontalHighlightIndicator(false);

        set.setDrawCircles(true);
        set.setDrawCubic(false);
        set.setCubicIntensity(0.6f);
        set.setDrawFilled(true);
        set.setFillColor(COLOR_FILL);
        set.setValueTextSize(10f);
        set.setValueTextColor(COLOR_VALUE_TEXT);
        set.setDrawValues(true);
        set.setValueFormatter(new ValueFormatter() {
            @Override
            public String getFormattedValue(float value, Entry entry, int dataSetIndex,
                                            ViewPortHandler viewPortHandler) {
                DecimalFormat decimalFormat = new DecimalFormat(".0℃");
                String s = decimalFormat.format(value);
                return s;
            }
        });

        return set;
    }


    /**
     * 添加一个点,并且左移动
     *
     * @param t
     */
    public void addEntry(T t) {
        LineData data = mLineChart.getData();
        data.addXValue(t.getXValue());


        LineDataSet set = data.getDataSetByIndex(INDEX);
        Entry entry = new Entry(t.getYValue(), set.getEntryCount());
        data.addEntry(entry, INDEX);

        mLineChart.notifyDataSetChanged();//刷新UI

        mLineChart.setVisibleXRangeMaximum(MAX_NUM);//最多在x轴坐标线上显示的总量
        mLineChart.moveViewToX(data.getXValCount() - MAX_NUM);
    }

}
