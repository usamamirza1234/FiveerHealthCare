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
import android.widget.TextView;

import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;

import com.uk.fiveerhealthcare.AppConfig;
import com.uk.fiveerhealthcare.R;
import com.uk.fiveerhealthcare.Utils.AppConstt;
import com.uk.fiveerhealthcare.Utils.IBadgeUpdateListener;

import static android.content.Context.SENSOR_SERVICE;


public class ProfileFragment extends Fragment
        implements View.OnClickListener {

    IBadgeUpdateListener mBadgeUpdateListener;


    public TextView textViewName;
    public TextView textViewEmail;
    public TextView textViewPassword;


    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View frg = inflater.inflate(R.layout.fragment_profile, container, false);

        init();
        bindviews(frg);
        setupData();
        return frg;
    }

    private void setupData() {

    }

    private void init() {

        setBottomBar();

    }

    private void bindviews(View frg) {


        textViewName = (TextView) frg.findViewById(R.id.textViewName);
        textViewEmail = (TextView) frg.findViewById(R.id.textViewEmail);
        textViewPassword = (TextView) frg.findViewById(R.id.textViewPassword);

        textViewName.setText(AppConfig.getInstance().mUser.getName());
        textViewEmail.setText(AppConfig.getInstance().mUser.getEmail());



    } public void onResume(){
        super.onResume();
    }


    @Override
    public void onClick(View v) {

        switch (v.getId()) {

        }
    }


    void setBottomBar() {
        try {
            mBadgeUpdateListener = (IBadgeUpdateListener) getActivity();
        } catch (ClassCastException castException) {
            castException.printStackTrace(); // The activity does not implement the listener
        }
        if (getActivity() != null && isAdded()) {
            mBadgeUpdateListener.setHeaderTitle("Profile");
            mBadgeUpdateListener.setToolbarState(AppConstt.ToolbarState.TOOLBAR_VISIBLE);
        }
    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        if (!hidden) {
            setBottomBar();
        }
    }





}
