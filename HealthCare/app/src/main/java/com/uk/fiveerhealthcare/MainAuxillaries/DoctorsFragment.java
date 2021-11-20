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

import java.util.ArrayList;

import static com.uk.fiveerhealthcare.Utils.IAdapterCallback.EVENT_A;

public class DoctorsFragment extends Fragment
        implements View.OnClickListener {


    DoctorRCVAdapter doctorRCVAdapter;
    ArrayList<DModelTreatment> lst_treatment;
    RecyclerView rcvDoctor;
    private Dialog progressDialog;

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View frg = inflater.inflate(R.layout.fragment_doctor, container, false);

        init();
        bindviews(frg);
        populatePopulationList();

        return frg;

    }

    private void init() {
        lst_treatment = new ArrayList<>();
    }

    private void bindviews(View frg) {

        rcvDoctor = frg.findViewById(R.id.frg_rcv_doctor);
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

        if (doctorRCVAdapter == null) {


            lst_treatment.add(new DModelTreatment("Cardiologist", "Lorem ipsum dolor sit amet\n" +
                    "Lorem ipsum dolor sit amet"));
            lst_treatment.add(new DModelTreatment("Pediatrician", "Lorem ipsum dolor sit amet\n" +
                    "Lorem ipsum dolor sit amet"));
            lst_treatment.add(new DModelTreatment("General", "Lorem ipsum dolor sit amet\n" +
                    "Lorem ipsum dolor sit amet"));
            lst_treatment.add(new DModelTreatment("Homeopathy", "Lorem ipsum dolor sit amet\n" +
                    "Lorem ipsum dolor sit amet"));


            doctorRCVAdapter = new DoctorRCVAdapter(getActivity(), lst_treatment, (eventId, position) -> {

                switch (eventId) {
                    case EVENT_A:
                        navToTreatmentFragment();

                        break;
                }

            });


            rcvDoctor.setLayoutManager(linearLayoutManager);
            rcvDoctor.setAdapter(doctorRCVAdapter);

        } else {
            doctorRCVAdapter.notifyDataSetChanged();
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
    private void navToTreatmentFragment() {
        FragmentManager fm = getFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        Fragment frag = new TreatmentFragment();
        ft.setCustomAnimations(R.anim.enter_from_right, R.anim.exit_to_left,
                R.anim.enter_from_left, R.anim.exit_to_right);//not required
        ft.add(R.id.act_main_content_frg, frag, AppConstt.FragTag.FN_TreatmentFragment);

        ft.addToBackStack(AppConstt.FragTag.FN_TreatmentFragment);

        ft.hide(this);
        ft.commit();
    }

}
