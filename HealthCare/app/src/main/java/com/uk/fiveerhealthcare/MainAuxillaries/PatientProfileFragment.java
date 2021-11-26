package com.uk.fiveerhealthcare.MainAuxillaries;

import android.Manifest;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.media.AudioManager;
import android.media.ToneGenerator;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.Description;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter;
import com.github.mikephil.charting.formatter.LargeValueFormatter;
import com.github.mikephil.charting.interfaces.datasets.IBarDataSet;
import com.github.mikephil.charting.interfaces.datasets.IDataSet;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;
import com.robinhood.spark.SparkAdapter;
import com.robinhood.spark.SparkView;
import com.uk.fiveerhealthcare.R;
import com.uk.fiveerhealthcare.Utils.AppConstt;
import com.uk.fiveerhealthcare.Utils.CustomToast;
import com.uk.fiveerhealthcare.Utils.IBadgeUpdateListener;

import java.util.ArrayList;
import java.util.List;

import static android.content.Context.SENSOR_SERVICE;
import static android.graphics.Color.RED;

public class PatientProfileFragment extends Fragment
        implements View.OnClickListener {

    public LineChart lineChartDownFill;
    RelativeLayout llCheckHB;
    TextView txvHeartbeat;
    TextView txvMeasuring;
    IBadgeUpdateListener mBadgeUpdateListener;

    BarChart severityBarChart;

    SensorManager mSensorManager;
    Sensor mHeartRateSensor;
    boolean checkHR = true;
    ArrayList<BarEntry> yValue = new ArrayList<>();
    List<Float> data = new ArrayList<>();
    List<Float> data1 = new ArrayList<>();
    List<Float> data2 = new ArrayList<>();
    List<Float> data3 = new ArrayList<>();
    ArrayList<String> severityStringList = new ArrayList<>();
    private String TAG="timer" ;
    private int bpm =0;

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View frg = inflater.inflate(R.layout.fragment_patient_profile, container, false);

        init();
        bindviews(frg);
        initializeBarChart();
        onPostSeveritylist();


        return frg;

    }

    public void checkHB() {
        final boolean[] timer = {false};
        new CountDownTimer(30000, 1000) {

            public void onTick(long millisUntilFinished) {
                txvMeasuring.setVisibility(View.VISIBLE);
                timer[0] = true;
                txvMeasuring.setText("Mesuring ... " + millisUntilFinished / 1000+" seconds are remaining");
                Log.d(TAG, "onTick: "+"seconds remaining: " + millisUntilFinished / 1000);
            }

            public void onFinish() {
                timer[0] = false;
                txvMeasuring.setVisibility(View.GONE);
                if ((int) bpm > 0 && (int) bpm < 83) {
                    ToneGenerator toneGen1 = new ToneGenerator(AudioManager.STREAM_MUSIC, 100);
                    toneGen1.startTone(ToneGenerator.TONE_CDMA_PIP, 500);
                }
                else if ((int) bpm > 165 ){
                    ToneGenerator toneGen1 = new ToneGenerator(AudioManager.STREAM_MUSIC, 100);
                    toneGen1.startTone(ToneGenerator.TONE_CDMA_PIP, 500);
                }
                else {
                    CustomToast.showToastMessage(getActivity(), "Average BPM", Toast.LENGTH_LONG);

                }
                Log.d(TAG, "onTick: "+"seconds done: ");
            }
        }.start();


        SensorEventListener sensorEventListener = new SensorEventListener() {
            @Override
            public void onSensorChanged(SensorEvent event) {
              if ( timer[0] )
              {
                  if (checkHR) {
                      if (event.sensor.getType() == Sensor.TYPE_HEART_RATE) {
                          if ((int) event.values[0] > 0)
                          {

                              bpm = (int) event.values[0];
                              String msg = "" + (int) event.values[0];
                              txvHeartbeat.setText(msg + " bmp");
                              Log.d("Sensor", "onSensorChanged " + msg);
                              checkHR = false;

                          }
                      }
                  }
              }


            }

            @Override
            public void onAccuracyChanged(Sensor sensor, int accuracy) {
                Log.d("Sensor", "onAccuracyChanged - accuracy: " + accuracy);
            }
        };
        mSensorManager.registerListener(sensorEventListener, mHeartRateSensor, SensorManager.SENSOR_DELAY_NORMAL);
        CustomToast.showToastMessage(getActivity(), "Place finger on sensor and hold for 1 minute", Toast.LENGTH_LONG);

        Log.i("Sensor", "LISTENER REGISTERED.");

        final Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                checkHR = true;
                handler.postDelayed(this, 50);
            }
        }, 1);
//        if (mSensorManager.getDefaultSensor(Sensor.TYPE_HEART_BEAT) == null) {
//            CustomToast.showToastMessage(getActivity(), "Your phone do not have Heart Rate support", Toast.LENGTH_LONG);
//        } else {
//            CustomToast.showToastMessage(getActivity(), "Place finger on sensor and hold for 1 minute", Toast.LENGTH_LONG);
//
//            Log.i("Sensor", "LISTENER REGISTERED.");
//
//            final Handler handler = new Handler();
//            handler.postDelayed(new Runnable() {
//                @Override
//                public void run() {
//                    checkHR = true;
//                    handler.postDelayed(this, 100);
//                }
//            }, 1);
//
//        }
    }


    private void init() {
        setBottomBar();
        ActivityCompat.requestPermissions(getActivity(),
                new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
                1);
    }

    private void bindviews(View frg) {

        mSensorManager = ((SensorManager) getActivity().getSystemService(SENSOR_SERVICE));
        mHeartRateSensor = mSensorManager.getDefaultSensor(Sensor.TYPE_HEART_RATE);


        initLineChartDownFill(frg);
        SparkView sparkView = frg.findViewById(R.id.sparkview);
        llCheckHB = frg.findViewById(R.id.frg_customer_profile_llStatus);

        SparkView sparkView2 = frg.findViewById(R.id.sparkview2);
        SparkView sparkView3 = frg.findViewById(R.id.sparkview3);
        severityBarChart = frg.findViewById(R.id.barChart);
        txvHeartbeat = frg.findViewById(R.id.txvHeartbeat);
        txvMeasuring = frg.findViewById(R.id.txvMeasuring);

        data.add(2.4f);
        data.add(3.4f);
        data.add(2.4f);
        data.add(1.8f);
        data.add(3.6f);
        data.add(3.2f);

        data1.add(1.8f);
        data1.add(3.6f);
        data1.add(3.4f);
        data1.add(2.4f);
        data1.add(3.2f);
        data1.add(2.4f);


        sparkView.setAdapter(new MyAdapter(data));

        sparkView2.setAdapter(new MyAdapter(data1));
        sparkView3.setAdapter(new MyAdapter(data));
        llCheckHB.setOnClickListener(this);

    }

    void setBottomBar() {
        try {
            mBadgeUpdateListener = (IBadgeUpdateListener) getActivity();
        } catch (ClassCastException castException) {
            castException.printStackTrace(); // The activity does not implement the listener
        }
        if (getActivity() != null && isAdded()) {
            mBadgeUpdateListener.setHeaderTitle("");
            mBadgeUpdateListener.setToolbarState(AppConstt.ToolbarState.TOOLBAR_HIDDEN);
        }
    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        if (!hidden) {
            setBottomBar();
        }
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.frg_customer_profile_llStatus:
                checkHB();
                break;
        }
    }

    private void initLineChartDownFill(View view) {

        lineChartDownFill = view.findViewById(R.id.lineChartDownFill);
        lineChartDownFill.setTouchEnabled(false);
        lineChartDownFill.setDragEnabled(true);
        lineChartDownFill.setScaleEnabled(true);
        lineChartDownFill.setPinchZoom(false);
        lineChartDownFill.setDrawGridBackground(false);
        lineChartDownFill.setMaxHighlightDistance(200);
        lineChartDownFill.setViewPortOffsets(0, 0, 0, 0);
        lineChartDownFillWithData();

    }

    private void lineChartDownFillWithData() {


        Description description = new Description();
        description.setText("Days Data");
        description.setEnabled(false);

//        lineChartDownFill.setDescription(description);


        ArrayList<Entry> entryArrayList = new ArrayList<>();
        entryArrayList.add(new Entry(0, 60f, "1"));
        entryArrayList.add(new Entry(1, 55f, "2"));
        entryArrayList.add(new Entry(2, 60f, "3"));
        entryArrayList.add(new Entry(3, 40f, "4"));
        entryArrayList.add(new Entry(4, 45f, "5"));
        entryArrayList.add(new Entry(5, 36f, "6"));
        entryArrayList.add(new Entry(6, 30f, "7"));
        entryArrayList.add(new Entry(7, 40f, "8"));
        entryArrayList.add(new Entry(8, 45f, "9"));
        entryArrayList.add(new Entry(9, 60f, "10"));
        entryArrayList.add(new Entry(10, 45f, "10"));
        entryArrayList.add(new Entry(11, 20f, "10"));


        //LineDataSet is the line on the graph
        LineDataSet lineDataSet = new LineDataSet(entryArrayList, "");

        lineDataSet.setLineWidth(0f);
        lineDataSet.setColor(getContext().getResources().getColor(R.color.blue_app));
        lineDataSet.setCircleColorHole(Color.GREEN);
        lineDataSet.setCircleColor(R.color.white);
        lineDataSet.setHighLightColor(RED);
        lineDataSet.setDrawValues(false);
        lineDataSet.setCircleRadius(10f);
        lineDataSet.setCircleColor(Color.YELLOW);

        //to make the smooth line as the graph is adrapt change so smooth curve
        lineDataSet.setMode(LineDataSet.Mode.CUBIC_BEZIER);
        //to enable the cubic density : if 1 then it will be sharp curve
        lineDataSet.setCubicIntensity(0.2f);

        //to fill the below of smooth line in graph
        lineDataSet.setDrawFilled(true);
        lineDataSet.setFillColor(Color.BLACK);
        //set the transparency
        lineDataSet.setFillAlpha(80);

        //set the gradiant then the above draw fill color will be replace
        Drawable drawable = ContextCompat.getDrawable(getContext(), R.color.blue_app);
        lineDataSet.setFillDrawable(drawable);

        //set legend disable or enable to hide {the left down corner name of graph}
//        Legend legend = lineChartDownFill.getLegend();
//        legend.setEnabled(false);

        //to remove the cricle from the graph
        lineDataSet.setDrawCircles(false);

        //lineDataSet.setColor(ColorTemplate.COLORFUL_COLORS);


        ArrayList<ILineDataSet> iLineDataSetArrayList = new ArrayList<>();
        iLineDataSetArrayList.add(lineDataSet);

        //LineData is the data accord
        LineData lineData = new LineData(iLineDataSetArrayList);
        lineData.setValueTextSize(13f);
        lineData.setValueTextColor(Color.BLACK);

        lineChartDownFill.getAxisLeft().setDrawLabels(false);
        lineChartDownFill.getAxisRight().setDrawLabels(false);
        lineChartDownFill.getXAxis().setDrawLabels(false);
        lineChartDownFill.setData(lineData);
        lineChartDownFill.invalidate();


    }


    private void initializeBarChart() {
        severityBarChart.getDescription().setEnabled(false);

        // if more than 60 entries are displayed in the chart, no values will be
        // drawn
        severityBarChart.setMaxVisibleValueCount(4);
        severityBarChart.getXAxis().setDrawGridLines(false);
        // scaling can now only be done on x- and y-axis separately
        severityBarChart.setPinchZoom(false);

        severityBarChart.setDrawBarShadow(false);
        severityBarChart.setDrawGridBackground(false);

        XAxis xAxis = severityBarChart.getXAxis();
        xAxis.setDrawGridLines(false);

        severityBarChart.getAxisLeft().setDrawGridLines(false);
        severityBarChart.getAxisRight().setDrawGridLines(false);
        severityBarChart.getAxisRight().setEnabled(false);
        severityBarChart.getAxisLeft().setEnabled(true);
        severityBarChart.getXAxis().setDrawGridLines(false);
        // add a nice and smooth animation
        severityBarChart.animateY(1500);


        severityBarChart.getLegend().setEnabled(false);

        severityBarChart.getAxisRight().setDrawLabels(false);
        severityBarChart.getAxisLeft().setDrawLabels(true);
        severityBarChart.setTouchEnabled(false);
        severityBarChart.setDoubleTapToZoomEnabled(false);
        severityBarChart.getXAxis().setEnabled(true);
        severityBarChart.getXAxis().setTextSize(6f);
        severityBarChart.getXAxis().setPosition(XAxis.XAxisPosition.BOTTOM);
        severityBarChart.invalidate();

    }

    private void createBarChart(ArrayList<DModelDoctor> severityListServer) {
        ArrayList<BarEntry> values = new ArrayList<>();

        for (int i = 0; i < severityListServer.size(); i++) {
            DModelDoctor dataObject = severityListServer.get(i);
            values.add(new BarEntry(i, Float.parseFloat(dataObject.getName())));
        }

        BarDataSet set1;

        if (severityBarChart.getData() != null &&
                severityBarChart.getData().getDataSetCount() > 0) {
            set1 = (BarDataSet) severityBarChart.getData().getDataSetByIndex(0);
            set1.setValues(values);
            severityBarChart.getData().notifyDataChanged();
            severityBarChart.notifyDataSetChanged();
        } else {
            set1 = new BarDataSet(values, "Data Set");
            set1.setColors(getResources().getColor(R.color.light_pink_app2));
            set1.setDrawValues(true);

            ArrayList<IBarDataSet> dataSets = new ArrayList<>();
            dataSets.add(set1);

            BarData data = new BarData(dataSets);
            severityBarChart.setData(data);
            severityBarChart.setVisibleXRange(1, 6);
            severityBarChart.setFitBars(true);
            XAxis xAxis = severityBarChart.getXAxis();
            xAxis.setGranularity(1f);
            xAxis.setGranularityEnabled(false);
//            severityBarChart.getBarData().setBarWidth(0.30f);


            xAxis.setValueFormatter(new IndexAxisValueFormatter(severityStringList));//setting String values in Xaxis


            YAxis yAxis = severityBarChart.getAxisLeft();
            yAxis.setValueFormatter(new LargeValueFormatter());
            yAxis.setDrawGridLines(false);
            yAxis.setSpaceTop(1f);
            yAxis.setAxisMinimum(0f);
            yAxis.setEnabled(false);


            for (IDataSet set : severityBarChart.getData().getDataSets())
                set.setDrawValues(!set.isDrawValuesEnabled());

            severityBarChart.invalidate();
        }
    }

    protected void onPostSeveritylist() {


        ArrayList<DModelDoctor> data = new ArrayList<>();
        data.add(new DModelDoctor("7", "severityType ", "ticketCount "));
        data.add(new DModelDoctor("10", "severityType ", "ticketCount "));
        data.add(new DModelDoctor("12", "severityType ", "ticketCount "));
        data.add(new DModelDoctor("2", "severityType ", "ticketCount "));
        data.add(new DModelDoctor("4", "severityType ", "ticketCount "));
        data.add(new DModelDoctor("7", "severityType ", "ticketCount "));


        List<String> xAxisValues = new ArrayList<>();
        xAxisValues.add("7:00");
        xAxisValues.add("10:00");
        xAxisValues.add("12:00");
        xAxisValues.add("2:00");
        xAxisValues.add("4:00");
        xAxisValues.add("7:00");


        severityStringList.addAll(xAxisValues);

        createBarChart(data);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String[] permissions, int[] grantResults) {
        switch (requestCode) {
            case 1: {

                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                    // permission was granted, yay! Do the
                    // contacts-related task you need to do.
                } else {

                    // permission denied, boo! Disable the
                    // functionality that depends on this permission.
                    Toast.makeText(getContext(), "Permission denied to read your External storage", Toast.LENGTH_SHORT).show();
                }
                return;
            }

            // other 'case' lines to check for other
            // permissions this app might request
        }
    }

    public class MyAdapter extends SparkAdapter {
        private final List<Float> yData;

        public MyAdapter(List<Float> yData) {
            this.yData = yData;
        }

        @Override
        public int getCount() {
            return yData.size();
        }

        @Override
        public Object getItem(int index) {
            return yData.get(index);
        }

        @Override
        public float getY(int index) {
            return yData.get(index);
        }
    }
}
