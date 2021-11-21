package com.uk.fiveerhealthcare.MainAuxillaries;

import android.app.Dialog;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.uk.fiveerhealthcare.R;
import com.uk.fiveerhealthcare.Utils.AppConstt;
import com.uk.fiveerhealthcare.Utils.IBadgeUpdateListener;

import java.util.ArrayList;

import static com.uk.fiveerhealthcare.Utils.IAdapterCallback.EVENT_A;

public class HomeFragment extends Fragment
        implements View.OnClickListener {


    TreatmentTypeRCVAdapter treatmentTypeRCVAdapter;
    ArrayList<DModelTreatment> lst_treatment;
    RecyclerView rcvTreatment;
    IBadgeUpdateListener mBadgeUpdateListener;
    private Dialog progressDialog;

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View frg = inflater.inflate(R.layout.fragment_home, container, false);

        init();
        bindviews(frg);
        populatePopulationList();

        return frg;

    }

    private void init() {
        setBottomBar();
        lst_treatment = new ArrayList<>();
    }

    private void bindviews(View frg) {

        rcvTreatment = frg.findViewById(R.id.frg_rcv_types);
//        imvfb = frg.findViewById(R.id.frg_signup_imvfb);
//        rlSignin = frg.findViewById(R.id.frg_signin_rlLogin);
//        edtName = frg.findViewById(R.id.frg_signin_edtName);
//        edtPassword = frg.findViewById(R.id.frg_signin_edtPass);
//
//
//        imvfb.setOnClickListener(this);
//        rlSignin.setOnClickListener(this);
//
//
//        editTextWatchers();
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
//            case R.id.frg_signup_imvfb:
//                navToSignUPFBFragment();
//                break;
//
//            case R.id.frg_signin_rlLogin:
//                checkErrorConditions();
//                break;
        }
    }


    private void populatePopulationList() {


        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);

        if (treatmentTypeRCVAdapter == null) {


            lst_treatment.add(new DModelTreatment("Cardiologist", "Lorem ipsum dolor sit amet\n" ));
            lst_treatment.add(new DModelTreatment("Diabetes", "Lorem ipsum dolor sit amet\n"));
            lst_treatment.add(new DModelTreatment("Blood pressure", "Lorem ipsum dolor sit amet\n" ));
            lst_treatment.add(new DModelTreatment("Asthma", "Lorem ipsum dolor sit amet\n" ));


            treatmentTypeRCVAdapter = new TreatmentTypeRCVAdapter(getActivity(), lst_treatment, (eventId, position) -> {

                switch (eventId) {
                    case EVENT_A:
                        navToDoctorFragment();
                        break;
                }

            });


            rcvTreatment.setLayoutManager(linearLayoutManager);
            rcvTreatment.setAdapter(treatmentTypeRCVAdapter);

        } else {
            treatmentTypeRCVAdapter.notifyDataSetChanged();
        }
    }


    //region  functions for Dialog
    private void dismissProgDialog() {
        if (progressDialog != null) {
            progressDialog.dismiss();
        }
    }

    private void showProgDialog() {
        progressDialog = new Dialog(getActivity(), R.style.AppTheme);
        progressDialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
//        progressDialog.setContentView(R.layout.dialog_progress);

        progressDialog.setCancelable(false);
        progressDialog.show();
    }

    private void navToDoctorFragment() {
        FragmentManager fm = getFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        Fragment frag = new DoctorsFragment();
        ft.setCustomAnimations(R.anim.enter_from_right, R.anim.exit_to_left,
                R.anim.enter_from_left, R.anim.exit_to_right);//not required
        ft.add(R.id.act_main_content_frg, frag, AppConstt.FragTag.FN_DoctorsFragment);

        ft.addToBackStack(AppConstt.FragTag.FN_DoctorsFragment);

        ft.hide(this);
        ft.commit();
    }
}
