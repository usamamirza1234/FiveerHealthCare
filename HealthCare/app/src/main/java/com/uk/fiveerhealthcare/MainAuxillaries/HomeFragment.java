package com.uk.fiveerhealthcare.MainAuxillaries;

import android.app.Dialog;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.uk.fiveerhealthcare.AppConfig;
import com.uk.fiveerhealthcare.IntroActivity;
import com.uk.fiveerhealthcare.IntroAuxilaries.SignUPFacebookFragment;
import com.uk.fiveerhealthcare.R;
import com.uk.fiveerhealthcare.Utils.AppConstt;

import java.util.ArrayList;

import static com.uk.fiveerhealthcare.Utils.IAdapterCallback.EVENT_A;

public class HomeFragment extends Fragment
        implements View.OnClickListener {


    private Dialog progressDialog;
    TreatmentTypeRCVAdapter treatmentTypeRCVAdapter;
    ArrayList <DModelTreatment> lst_treatment;
    RecyclerView rcvTreatment;

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View frg = inflater.inflate(R.layout.fragment_home, container, false);

        init();
        bindviews(frg);
        populatePopulationList();

        return frg;

    }

    private void init() {
        lst_treatment= new ArrayList<>();
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

        if (treatmentTypeRCVAdapter == null)
        {



            lst_treatment.add(new DModelTreatment("Cardiologist",""));
            lst_treatment.add(new DModelTreatment("Pediatrician",""));
            lst_treatment.add(new DModelTreatment("General",""));
            lst_treatment.add(new DModelTreatment("Homeopathy",""));


            treatmentTypeRCVAdapter = new TreatmentTypeRCVAdapter(getActivity(), lst_treatment, (eventId, position) -> {

                switch (eventId) {
                    case EVENT_A:


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


}
