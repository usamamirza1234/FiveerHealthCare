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

public class DoctorsFragment extends Fragment
        implements View.OnClickListener {


    DoctorRCVAdapter doctorRCVAdapter;
    ArrayList<DModelDoctor> lst_treatment;
    RecyclerView rcvDoctor;
    IBadgeUpdateListener mBadgeUpdateListener;
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
        setBottomBar();
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


            lst_treatment.add(new DModelDoctor("Dr. Murat Tuzcu", "Cardiologist", "is the Chief Academic Officer and the Chief of Cardiovascular Medicine in the Heart and Vascular Institute at Cleveland Clinic Abu Dhabi.", getContext().getResources().getDrawable(R.drawable.ic_dr1)));
            lst_treatment.add(new DModelDoctor("Dr. Jassem Abdou", "Asthma", "Is substantial experience with over 20 years’ experience at Zayed Military Hospital as a Consultant pulmonologist, where he also participated in the teaching residency program of Arab board in Internal Medicine.", getContext().getResources().getDrawable(R.drawable.ic_dr2)));
            lst_treatment.add(new DModelDoctor("Dr. Julieta Zuluaga", "Blood pressure", "Dr. Julieta Zuluaga is a specialist in clinical hematology with experience working in several countries including Colombia, Spain, Italy and the UAE. She obtained her specialty training in hematolo.",getContext().getResources().getDrawable(R.drawable.ic_profile)));
            lst_treatment.add(new DModelDoctor("Dr. Noor Hasan", "Diabetes", "Now, delivering her multi-faceted healthcare skills at ICLDC, Dr. Noor is widely recognised for taking leading roles in medical education and administration, as well her practice in internal medicine, specialising in diabetes.", getContext().getResources().getDrawable(R.drawable.ic_dr4)));


            doctorRCVAdapter = new DoctorRCVAdapter(getActivity(), lst_treatment, (eventId, position) -> {

                switch (eventId) {
                    case EVENT_A:

                        navToConsultantFragment(lst_treatment,position);

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

    private void navToConsultantFragment(ArrayList<DModelDoctor> lst_treatment, int position) {
        FragmentManager fm = getFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        Fragment frag = new ConsultantFragment();
        Bundle bundle = new Bundle();
        bundle.putString("key_name",lst_treatment.get(position).getName());
        bundle.putString("key_desc",lst_treatment.get(position).getDesc());
        bundle.putString("key_type",lst_treatment.get(position).getType());
        frag.setArguments(bundle);
        ft.setCustomAnimations(R.anim.enter_from_right, R.anim.exit_to_left,
                R.anim.enter_from_left, R.anim.exit_to_right);//not required
        ft.add(R.id.act_main_content_frg, frag, AppConstt.FragTag.FN_TreatmentFragment);

        ft.addToBackStack(AppConstt.FragTag.FN_TreatmentFragment);

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
            mBadgeUpdateListener.setHeaderTitle("Doctor List");
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
