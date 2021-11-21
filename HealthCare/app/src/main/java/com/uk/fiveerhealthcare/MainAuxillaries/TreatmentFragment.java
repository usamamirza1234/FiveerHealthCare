package com.uk.fiveerhealthcare.MainAuxillaries;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.uk.fiveerhealthcare.R;
import com.uk.fiveerhealthcare.Utils.AppConstt;
import com.uk.fiveerhealthcare.Utils.CircleImageView;
import com.uk.fiveerhealthcare.Utils.IBadgeUpdateListener;

public class TreatmentFragment extends Fragment
        implements View.OnClickListener {


    IBadgeUpdateListener mBadgeUpdateListener;
    Bundle bundle;
    TextView txvDesc, txvName;
    String strName, strDesc, strType;
    CircleImageView civProfile;
    LinearLayout llBookApt, llEmrgncySevice, llApplyAdm;

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View frg = inflater.inflate(R.layout.fragment_treatment, container, false);

        init();
        bindviews(frg);
        setupData();
        return frg;
    }

    private void setupData() {
        txvName.setText(strName);
        txvDesc.setText(strDesc);
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

    private void init() {
        setBottomBar();
        bundle = getArguments();
        if (bundle != null) {
            strName = bundle.getString("key_name");
            strDesc = bundle.getString("key_desc");
            strType = bundle.getString("key_type");
        }
    }

    private void bindviews(View frg) {

        txvDesc = frg.findViewById(R.id.frg_treatment_txv_desc);
        txvName = frg.findViewById(R.id.frg_treatment_txv_name);
        civProfile = frg.findViewById(R.id.frg_treatment_imv_profile);

        llBookApt = frg.findViewById(R.id.frg_treatment_llBookAppt);
        llEmrgncySevice = frg.findViewById(R.id.frg_treatment_llEmergncySev);
        llApplyAdm = frg.findViewById(R.id.frg_treatment_llApplyHosp);

        llBookApt.setOnClickListener(this);
        llEmrgncySevice.setOnClickListener(this);
        llApplyAdm.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.frg_treatment_llBookAppt:
                navToBookAptFragment();
                break;

            case R.id.frg_treatment_llEmergncySev:
//                checkErrorConditions();
                break;
            case R.id.frg_treatment_llApplyHosp:
//                checkErrorConditions();
                break;
        }
    }


    private void navToBookAptFragment() {
        FragmentManager fm = getFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        Fragment frag = new TreatmentConfirmFragment();
        Bundle bundle = new Bundle();
        bundle.putString("key_name",strName);
        bundle.putString("key_desc",strDesc);
        bundle.putString("key_type",strType);
        frag.setArguments(bundle);
        ft.setCustomAnimations(R.anim.enter_from_right, R.anim.exit_to_left,
                R.anim.enter_from_left, R.anim.exit_to_right);//not required
        ft.add(R.id.act_main_content_frg, frag, AppConstt.FragTag.FN_TreatmentConfirmFragment);

        ft.addToBackStack(AppConstt.FragTag.FN_TreatmentConfirmFragment);

        ft.hide(this);
        ft.commit();
    }

    void setBottomBar() {
        try {
            mBadgeUpdateListener = (IBadgeUpdateListener) getActivity();
        } catch (ClassCastException castException) {
            castException.printStackTrace(); // The activity does not implement the listener
        }
        if (getActivity() != null && isAdded()) {
            mBadgeUpdateListener.setHeaderTitle("Treatment");
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
