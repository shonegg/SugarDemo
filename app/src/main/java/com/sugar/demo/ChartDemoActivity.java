package com.sugar.demo;

import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.github.mikephil.charting.charts.LineChart;
import com.sugar.demo.chart.ChartController;
import com.sugar.demo.chart.TempModel;

import java.text.SimpleDateFormat;
import java.util.Date;

public class ChartDemoActivity extends AppCompatActivity {

    private ChartController<TempModel> mController;
    private TextView tempTv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chart);

        tempTv = (TextView) findViewById(R.id.tempTv);

        LineChart mChart = (LineChart) findViewById(R.id.chart);
        mController = new ChartController<>(mChart);
        mController.init();
        start();
    }

    private void start() {

        Handler mHandler = new Handler();
        mHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                TempModel model = new TempModel();
                SimpleDateFormat df = new SimpleDateFormat("HH:mm:ss");
                String time = df.format(new Date());
                model.setXValue(time);
                float randomNum = (float) ((Math.random()) * 28 + 10);
                float yVal = (float) (Math.round(randomNum * 10.0) / 10.0);
                model.setYValue(yVal);
                mController.addEntry(model);
                tempTv.setText("" + yVal + "℃");
                start();
            }
        }, 1000);
    }

}
