package com.uk.fiveerhealthcare.MainAuxillaries;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.uk.fiveerhealthcare.R;
import com.uk.fiveerhealthcare.Utils.AppConstt;
import com.uk.fiveerhealthcare.Utils.CircleImageView;
import com.uk.fiveerhealthcare.Utils.IBadgeUpdateListener;


public class ConsultationPayNowFragment extends Fragment
        implements View.OnClickListener {
    Bundle bundle;
    TextView txvName;
    String strName, strDesc, strType;
    CircleImageView civProfile;
    LinearLayout llNext;
    RelativeLayout rlPaynow;
    IBadgeUpdateListener mBadgeUpdateListener;

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View frg = inflater.inflate(R.layout.fragment_consultation_paynow, container, false);

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

        rlPaynow = frg.findViewById(R.id.frg_payment_llpaynow);
        rlPaynow.setOnClickListener(this);

    }


    @Override
    public void onClick(View v) {

        switch (v.getId()) {

            case R.id.frg_payment_llpaynow:
                navtoConsultationChatRoomFragment();
                break;

        }
    }

    void setBottomBar() {
        try {
            mBadgeUpdateListener = (IBadgeUpdateListener) getActivity();
        } catch (ClassCastException castException) {
            castException.printStackTrace(); // The activity does not implement the listener
        }
        if (getActivity() != null && isAdded()) {
            mBadgeUpdateListener.setHeaderTitle("Payment");
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


    private void navtoConsultationChatRoomFragment() {
        FragmentManager fm = getFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        Fragment frag = new ConsultationChatRoomFragment();
        Bundle bundle = new Bundle();
        bundle.putString("key_name", strName);
        bundle.putString("key_desc", strDesc);
        bundle.putString("key_type", strType);
        frag.setArguments(bundle);
        ft.setCustomAnimations(R.anim.enter_from_right, R.anim.exit_to_left,
                R.anim.enter_from_left, R.anim.exit_to_right);//not required
        ft.add(R.id.act_main_content_frg, frag, AppConstt.FragTag.FN_ConsultationChatRoomFragment);

        ft.addToBackStack(AppConstt.FragTag.FN_ConsultationChatRoomFragment);

        ft.hide(this);
        ft.commit();
    }

}

