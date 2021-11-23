package com.uk.fiveerhealthcare.MainAuxillaries;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.uk.fiveerhealthcare.R;
import com.uk.fiveerhealthcare.Utils.AppConstt;
import com.uk.fiveerhealthcare.Utils.CircleImageView;
import com.uk.fiveerhealthcare.Utils.IBadgeUpdateListener;

import java.util.Collections;

public class ConsultationConfirmPaymentFragment extends Fragment
        implements View.OnClickListener {

    Bundle bundle;
    TextView txvDesc, txvName;
    String strName, strDesc, strType;
    CircleImageView civProfile;
    LinearLayout llNext,llHospAdm,llConfrmTreatment;

    private final byte TOTAL_PERIOD = 2;
    private CheckBox[] arrchbFilterPeroid;
    private LinearLayout[] arrllFilterPeroid;
    private final byte CHB_TODAY = 0;
    private final byte CHB_YESTERDAY = 1;


    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View frg = inflater.inflate(R.layout.fragment_confirm_payment, container, false);

        init();
        bindviews(frg);
        setupData();

        return frg;

    }

    private void init() {
        bundle = getArguments();
        if (bundle != null) {
            strName = bundle.getString("key_name");
            strDesc = bundle.getString("key_desc");
            strType = bundle.getString("key_type");
        }
        setBottomBar();
    }

    private void bindviews(View frg) {

        txvName = frg.findViewById(R.id.frg_treatmentConfirm_txvName);
        civProfile = frg.findViewById(R.id.frg_treatmentConfirm_civProfile);

        llNext = frg.findViewById(R.id.frg_treatmentConfirm_llNext);
        llHospAdm = frg.findViewById(R.id.frg_treatmentConfirm_llAdmHospt);
        llConfrmTreatment = frg.findViewById(R.id.frg_treatmentConfirm_llConfrmTreatment);



        arrllFilterPeroid = new LinearLayout[TOTAL_PERIOD];
        arrllFilterPeroid[CHB_TODAY] = frg.findViewById(R.id.lay_prog_ll_today);
        arrllFilterPeroid[CHB_YESTERDAY] = frg.findViewById(R.id.lay_prog_ll_yesterday);

        for (int i = 0; i < TOTAL_PERIOD; i++)
            arrllFilterPeroid[i].setOnClickListener(this);


        arrchbFilterPeroid = new CheckBox[TOTAL_PERIOD];
        arrchbFilterPeroid[CHB_TODAY] = frg.findViewById(R.id.lay_prog_chb_today);
        arrchbFilterPeroid[CHB_YESTERDAY] = frg.findViewById(R.id.lay_prog_chb_yesterday);


        llNext.setOnClickListener(this);
        llHospAdm.setOnClickListener(this);
        llConfrmTreatment.setOnClickListener(this);
    }


    private void setupData() {
        txvName.setText(strName);
//        txvDesc.setText(strDesc);
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
                navtoPaymentFragment();
                break;
            case R.id.frg_treatmentConfirm_llAdmHospt:
                navtoPaymentFragment();
                break;
            case R.id.frg_treatmentConfirm_llConfrmTreatment:
                navtoPaymentFragment();
                break;
            case R.id.lay_prog_ll_today:

                switchBottomTab(CHB_TODAY);
                break;
            case R.id.lay_prog_ll_yesterday:

                switchBottomTab(CHB_YESTERDAY);
                break;


        }
    }
    public void switchBottomTab(int tabNum) {
        for (int i = 0; i < TOTAL_PERIOD; i++) {
            arrchbFilterPeroid[i].setChecked(i == tabNum);
        }

    }
    private void navtoPaymentFragment()
    {
        FragmentManager fm = getFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        Fragment frag = new ConsultationPaymentFragment();
        Bundle bundle = new Bundle();
        bundle.putString("key_name",strName);
        bundle.putString("key_desc",strDesc);
        bundle.putString("key_type",strType);
        frag.setArguments(bundle);
        ft.setCustomAnimations(R.anim.enter_from_right, R.anim.exit_to_left,
                R.anim.enter_from_left, R.anim.exit_to_right);//not required
        ft.add(R.id.act_main_content_frg, frag, AppConstt.FragTag.FN_TreatmentPaymentFragment);

        ft.addToBackStack(AppConstt.FragTag.FN_TreatmentPaymentFragment);

        ft.hide(this);
        ft.commit();
    }


    void setBottomBar() {
        try {
            mBadgeUpdateListener = (IBadgeUpdateListener) getActivity();
        } catch (ClassCastException castException) {
            castException.printStackTrace(); // The activity does not implement the listener
        }
        if (getActivity() != null && isAdded())
            mBadgeUpdateListener.setToolbarState(AppConstt.ToolbarState.TOOLBAR_VISIBLE);
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
