package com.uk.fiveerhealthcare.MainAuxillaries;

import android.app.Dialog;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

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

public class CustomerProfileFragment extends Fragment
        implements View.OnClickListener {

    RelativeLayout rlPrev, rlNext;
    CustomerProfileTreatmentRCVAdapter customerProfileTreatmentRCVAdapter;
    DoctorRCVAdapter doctorRCVAdapter;
    ArrayList<DModelTreatment> lst_treatment;
    ArrayList<DModelDoctor> lst_doctor;
    RecyclerView rcvTreatment,rcvDoctor;
    IBadgeUpdateListener mBadgeUpdateListener;
    RelativeLayout llStatus;

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View frg = inflater.inflate(R.layout.fragment_customer_profile, container, false);

        init();
        bindviews(frg);
        populatePopulationList();
        populateDoctorList();
        return frg;

    }

    private void init() {
        setBottomBar();
        lst_treatment = new ArrayList<>();
        lst_doctor = new ArrayList<>();
    }

    private void bindviews(View frg) {

        rcvTreatment = frg.findViewById(R.id.frg_rcv_types);
        rcvDoctor = frg.findViewById(R.id.frg_rcv_doctor);
        llStatus = frg.findViewById(R.id.frg_customer_profile_llStatus);

        rlPrev = frg.findViewById(R.id.frg_home_rl_previous);
        rlNext = frg.findViewById(R.id.frg_home_rl_next);
        rlPrev.setOnClickListener(this);
        rlNext.setOnClickListener(this);
        llStatus.setOnClickListener(this);

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
            case R.id.frg_customer_profile_llStatus:
                navtoPatientProfileFragment();
                break;
//
//            case R.id.frg_signin_rlLogin:
//                checkErrorConditions();
//                break;
        }
    }


    private void populatePopulationList() {


        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false);

        if (customerProfileTreatmentRCVAdapter == null) {


            lst_treatment.add(new DModelTreatment("Cardiologist\nSpeacialist", "Lorem ipsum dolor sit amet\n" ));
            lst_treatment.add(new DModelTreatment("Diabetes\nSpeacialist" , "Lorem ipsum dolor sit amet\n"));
            lst_treatment.add(new DModelTreatment("Blood Pressure\nSpeacialist", "Lorem ipsum dolor sit amet\n" ));
            lst_treatment.add(new DModelTreatment("Asthma\nSpeacialist", "Lorem ipsum dolor sit amet\n" ));


            customerProfileTreatmentRCVAdapter = new CustomerProfileTreatmentRCVAdapter(getActivity(), lst_treatment, (eventId, position) -> {

                switch (eventId) {
                    case EVENT_A:

                        break;
                }

            });


            rcvTreatment.setLayoutManager(linearLayoutManager);
            rcvTreatment.setAdapter(customerProfileTreatmentRCVAdapter);

        } else {
            customerProfileTreatmentRCVAdapter.notifyDataSetChanged();
        }
    }
    private void populateDoctorList() {


        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);

        if (doctorRCVAdapter == null) {


            lst_doctor.add(new DModelDoctor("Dr. Murat Tuzcu", "Cardiologist", "is the Chief Academic Officer and the Chief of Cardiovascular Medicine in the Heart and Vascular Institute at Cleveland Clinic Abu Dhabi.", getContext().getResources().getDrawable(R.drawable.ic_dr1)));
            lst_doctor.add(new DModelDoctor("Dr. Jassem Abdou", "Asthma", "Is substantial experience with over 20 years’ experience at Zayed Military Hospital as a Consultant pulmonologist, where he also participated in the teaching residency program of Arab board in Internal Medicine.", getContext().getResources().getDrawable(R.drawable.ic_dr2)));
            lst_doctor.add(new DModelDoctor("Dr. Julieta Zuluaga", "Blood pressure", "Dr. Julieta Zuluaga is a specialist in clinical hematology with experience working in several countries including Colombia, Spain, Italy and the UAE. She obtained her specialty training in hematolo.",getContext().getResources().getDrawable(R.drawable.ic_profile)));
            lst_doctor.add(new DModelDoctor("Dr. Noor Hasan", "Diabetes", "Delivering her multi-faceted healthcare skills     at ICLDC, Dr. Noor is widely recognised for taking leading roles in medical education and administration, as well her practice in internal medicine, specialising in diabetes.", getContext().getResources().getDrawable(R.drawable.ic_dr4)));


            doctorRCVAdapter = new DoctorRCVAdapter(getActivity(), lst_doctor, (eventId, position) -> {

                switch (eventId) {
                    case EVENT_A:



                        break;
                }

            });


            rcvDoctor.setLayoutManager(linearLayoutManager);
            rcvDoctor.setAdapter(doctorRCVAdapter);

        } else {
            doctorRCVAdapter.notifyDataSetChanged();
        }
    }

    private void navtoPatientProfileFragment() {
        FragmentManager fm = getFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        Fragment frag = new PatientProfileFragment();


        ft.setCustomAnimations(R.anim.enter_from_right, R.anim.exit_to_left,
                R.anim.enter_from_left, R.anim.exit_to_right);//not required
        ft.add(R.id.act_main_content_frg, frag, AppConstt.FragTag.FN_PatientProfileFragment);

        ft.addToBackStack(AppConstt.FragTag.FN_PatientProfileFragment);

        ft.hide(this);
        ft.commit();
    }
}
