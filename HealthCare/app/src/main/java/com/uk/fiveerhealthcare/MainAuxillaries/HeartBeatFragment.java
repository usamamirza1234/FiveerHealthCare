package com.uk.fiveerhealthcare.MainAuxillaries;

import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;

import com.uk.fiveerhealthcare.R;
import com.uk.fiveerhealthcare.Utils.AppConstt;
import com.uk.fiveerhealthcare.Utils.IBadgeUpdateListener;

import static android.content.Context.SENSOR_SERVICE;

public class HeartBeatFragment extends Fragment
        implements View.OnClickListener, SensorEventListener {

    SensorManager mSensorManager;
    Sensor mHeartRateSensor;
    SensorEventListener sensorEventListener;
    IBadgeUpdateListener mBadgeUpdateListener;
    private SensorManager sensorManager;

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View frg = inflater.inflate(R.layout.fragment_heart_beat, container, false);
        init();
        bindviews(frg);
        return frg;
    }


    private void init() {
        setBottomBar();

    }

    private void bindviews(View frg) {

        SensorEventListener sensorEventListener = new SensorEventListener() {
            @Override
            public void onSensorChanged(SensorEvent event) {

                if (event.sensor.getType() == Sensor.TYPE_HEART_RATE) {
                    String msg = "" + (int)event.values[0];
//                    textViewName.setText(msg);
                    Log.d("Sensor", msg);
                }
                else
                    Log.d("Sensor", "Unknown sensor type");
            }

            @Override
            public void onAccuracyChanged(Sensor sensor, int accuracy) {
                Log.d("Sensor","onAccuracyChanged - accuracy: "+accuracy);
            }
        };

        mSensorManager = ((SensorManager) getActivity().getSystemService(SENSOR_SERVICE));
        mHeartRateSensor = mSensorManager.getDefaultSensor(Sensor.TYPE_HEART_RATE);
        mSensorManager.registerListener(this, mHeartRateSensor, SensorManager.SENSOR_DELAY_NORMAL);
        Log.d("Sensor", "LISTENER REGISTERED.");
        mSensorManager.registerListener(sensorEventListener, mHeartRateSensor, mSensorManager.SENSOR_DELAY_FASTEST);
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

        }
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        if (event.sensor.getType() == Sensor.TYPE_HEART_RATE) {
            String msg = " Value sensor: " + (int) event.values[0];
            Log.d("Sensor", "onSensorChanged " + msg);
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {
        Log.d("Sensor", "onAccuracyChanged - accuracy: " + accuracy);
    }

}
