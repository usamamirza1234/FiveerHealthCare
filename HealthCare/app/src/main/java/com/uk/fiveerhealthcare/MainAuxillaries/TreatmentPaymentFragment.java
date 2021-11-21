package com.uk.fiveerhealthcare.MainAuxillaries;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;

import com.uk.fiveerhealthcare.R;
import com.uk.fiveerhealthcare.Utils.AppConstt;
import com.uk.fiveerhealthcare.Utils.IBadgeUpdateListener;

public class TreatmentPaymentFragment extends Fragment
        implements View.OnClickListener {




    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View frg = inflater.inflate(R.layout.fragment_treatment_payemnt, container, false);

        init();
        bindviews(frg);


        return frg;

    }

    private void init() {
        setBottomBar();
    }

    private void bindviews(View frg) {

//        rcvDoctor = frg.findViewById(R.id.frg_rcv_doctor);
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {
//            case R.id.frg_signup_imvfb:
//                navToSignUPFBFragment();
//                break;
//
//            case R.id.frg_signin_rlLogin:
//                checkErrorConditions();
//                break;
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
            mBadgeUpdateListener.setHeaderTitle("Treatment");
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
