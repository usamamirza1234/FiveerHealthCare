package com.uk.fiveerhealthcare.IntroAuxilaries;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.uk.fiveerhealthcare.R;
import com.uk.fiveerhealthcare.Utils.AppConstt;

public class PreSignInFragment extends Fragment implements View.OnClickListener {


    RelativeLayout rlGetStarted, rlSignUp, rlForgot;

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View frg = inflater.inflate(R.layout.fragment_pre_sign_in, container, false);


        init();
        bindViews(frg);


        return frg;
    }

    private void init() {
    }

    private void bindViews(View frg)
    {
        rlGetStarted = frg.findViewById(R.id.frg_presigin_rlGetStarted);
        rlGetStarted.setOnClickListener(this);

    }


    @Override
    public void onClick(View v) {

        switch (v.getId()) {

            case R.id.frg_presigin_rlGetStarted:
                navToSignInFragment();
                break;

        }
    }


    private void navToSignInFragment() {
        FragmentManager fm = getFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        Fragment frag = new SignUPFragment();
        ft.setCustomAnimations(R.anim.enter_from_right, R.anim.exit_to_left,
                R.anim.enter_from_left, R.anim.exit_to_right);//not required
        ft.add(R.id.act_intro_content_frg, frag, AppConstt.FragTag.FN_SignUpFragment);

        ft.addToBackStack(AppConstt.FragTag.FN_SignUpFragment);

        ft.hide(this);
        ft.commit();
    }


}
