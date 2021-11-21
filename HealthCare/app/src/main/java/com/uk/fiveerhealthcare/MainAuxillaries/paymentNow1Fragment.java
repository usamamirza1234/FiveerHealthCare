package com.uk.fiveerhealthcare.MainAuxillaries;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.uk.fiveerhealthcare.R;
import com.uk.fiveerhealthcare.Utils.AppConstt;
import com.uk.fiveerhealthcare.Utils.CircleImageView;
import com.uk.fiveerhealthcare.Utils.IBadgeUpdateListener;




public class paymentNow1Fragment extends Fragment
        implements View.OnClickListener {
    Bundle bundle;
    TextView txvName;
    String strName, strDesc, strType;
    CircleImageView civProfile;
    LinearLayout llNext;




    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View frg = inflater.inflate(R.layout.fragment_payment_now1, container, false);

        init();
        bindviews(frg);



        return frg;

    }



    private void init() {
        setBottomBar();
        bundle = getArguments();
        if (bundle != null) {
            strName = bundle.getString("key_name");

            strType = bundle.getString("key_type");
        }

    }

    private void bindviews(View frg) {









    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {

            case R.id.frg_treatmentConfirm_llNext:

                break;

        }
    }




    void setBottomBar() {
        try {
            mBadgeUpdateListener = (IBadgeUpdateListener) getActivity();
        } catch (ClassCastException castException) {
            castException.printStackTrace(); // The activity does not implement the listener
        }
        if (getActivity() != null && isAdded())
        {
            mBadgeUpdateListener.setHeaderTitle("Payment");
            mBadgeUpdateListener.setToolbarState(AppConstt.ToolbarState.TOOLBAR_VISIBLE);
        }
    }
    IBadgeUpdateListener mBadgeUpdateListener;
    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        if (!hidden) {
            setBottomBar();
        }
    }




}

