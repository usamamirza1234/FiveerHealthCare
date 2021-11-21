package com.uk.fiveerhealthcare.MainAuxillaries;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.uk.fiveerhealthcare.R;
import com.uk.fiveerhealthcare.Utils.AppConstt;
import com.uk.fiveerhealthcare.Utils.CircleImageView;
import com.uk.fiveerhealthcare.Utils.IBadgeUpdateListener;


public class ConsultationChatRoomFragment extends Fragment
        implements View.OnClickListener {
    Bundle bundle;
    TextView txvName;
    String strName, strDesc, strType;
    ImageView civProfile;
    LinearLayout llNext;
    LinearLayout llAdmHospt;
    IBadgeUpdateListener mBadgeUpdateListener;

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View frg = inflater.inflate(R.layout.fragment_consultation_chatroom, container, false);

        init();
        bindviews(frg);
        setupData();

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


        llNext = frg.findViewById(R.id.frg_treatmentConfirm_llNext);
        llAdmHospt = frg.findViewById(R.id.frg_treatmentConfirm_llAdmHospt);
        civProfile = frg.findViewById(R.id.frg_treatmentConfirm_civProfile);
        txvName = frg.findViewById(R.id.frg_treatmentConfirm_txvName);
        llNext.setOnClickListener(this);
        llAdmHospt.setOnClickListener(this);


    }
    private void setupData() {
        txvName.setText(strName);

        if (strName.equalsIgnoreCase("Dr. Murat Tuzcu")) {
            civProfile.setImageDrawable(getContext().getResources().getDrawable(R.drawable.ic_dr1));
        } else if (strName.equalsIgnoreCase("Dr. Jassem Abdou")) {
            civProfile.setImageDrawable(getContext().getResources().getDrawable(R.drawable.ic_dr2));
        } else if (strName.equalsIgnoreCase("Dr. Julieta Zuluaga")) {
            civProfile.setImageDrawable(getContext().getResources().getDrawable(R.drawable.ic_profile));
        } else {
            civProfile.setImageDrawable(getContext().getResources().getDrawable(R.drawable.ic_dr4));
        }

    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {

            case R.id.frg_treatmentConfirm_llNext:
                navtoCustomerProfileFragment();
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

    private void navtoCustomerProfileFragment() {
        FragmentManager fm = getFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        Fragment frag = new CustomerProfileFragment();
        Bundle bundle = new Bundle();
        bundle.putString("key_name", strName);
        bundle.putString("key_type", strType);
        frag.setArguments(bundle);
        ft.setCustomAnimations(R.anim.enter_from_right, R.anim.exit_to_left,
                R.anim.enter_from_left, R.anim.exit_to_right);//not required
        ft.add(R.id.act_main_content_frg, frag, AppConstt.FragTag.FN_CustomerProfileFragment);

        ft.addToBackStack(AppConstt.FragTag.FN_CustomerProfileFragment);

        ft.hide(this);
        ft.commit();
    }
}

