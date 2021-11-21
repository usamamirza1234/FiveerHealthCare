package com.uk.fiveerhealthcare.IntroAuxilaries;

import android.app.Dialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.uk.fiveerhealthcare.R;
import com.uk.fiveerhealthcare.Utils.AppConstt;

public class SignUPSocialFragment extends Fragment
        implements View.OnClickListener
{

    private Dialog progressDialog;
    RelativeLayout rlSignin, rlSignUp, rlForgot;
    EditText edtName, edtPassword;
    String str = "";
    TextView txvLogin;

    Bundle bundle;

    ImageView imvFB,imvLkdn,imvGogle,imvTwiter;
    LinearLayout llFB,llLkdn,llGogle,llTwiter;

    String type="";
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View frg = inflater.inflate(R.layout.fragment_sign_in_facebook, container, false);

        init();
        bindviews(frg);
        setdata();
        return frg;
    }

    private void setdata()
    {
        if(type.equalsIgnoreCase("fb"))
        {
            imvFB.setVisibility(View.GONE);

            imvGogle.setVisibility(View.VISIBLE);
            imvLkdn.setVisibility(View.VISIBLE);
            imvTwiter.setVisibility(View.VISIBLE);

            llFB.setVisibility(View.VISIBLE);

            llGogle.setVisibility(View.GONE);
            llLkdn.setVisibility(View.GONE);
            llTwiter.setVisibility(View.GONE);


        }
        else if (type.equalsIgnoreCase("gogl"))
        {

        } else if (type.equalsIgnoreCase("lknd"))
        {

        }
        else if (type.equalsIgnoreCase("twitr"))
        {

        }
    }

    private void init()
    {
        bundle=getArguments();
        if (bundle!=null)
        {
         type = bundle.getString("key_type");
        }
    }

    private void navToSignUPFBFragment() {
//        FragmentManager fm = getFragmentManager();
//        FragmentTransaction ft = fm.beginTransaction();
//        Fragment frag = new SignUPFacebookFragment();
//        ft.setCustomAnimations(R.anim.enter_from_right, R.anim.exit_to_left,
//                R.anim.enter_from_left, R.anim.exit_to_right);//not required
//        ft.add(R.id.act_intro_content_frg, frag, AppConstt.FragTag.FN_SignUPFBFragment);
//
//        ft.addToBackStack(AppConstt.FragTag.FN_SignUPFBFragment);
//
//        ft.hide(this);
//        ft.commit();
    }


    private void bindviews(View frg) {
        txvLogin = frg.findViewById(R.id.frg_signupfb_txvLogin);

        imvGogle = frg.findViewById(R.id.frg_signup_imvgoogle);
        imvFB = frg.findViewById(R.id.frg_signup_imvfb);
        imvLkdn = frg.findViewById(R.id.frg_signup_imvlnd);
        imvTwiter = frg.findViewById(R.id.frg_signup_imvtwiter);

        llGogle = frg.findViewById(R.id.frg_signup_llGoogle);
        llFB = frg.findViewById(R.id.frg_signup_llFb);
        llLkdn = frg.findViewById(R.id.frg_signup_llLinkdin);
        llTwiter = frg.findViewById(R.id.frg_signup_llTwitter);




        llGogle.setOnClickListener(this);
        llFB.setOnClickListener(this);
        llLkdn.setOnClickListener(this);
        llTwiter.setOnClickListener(this);

        imvGogle.setOnClickListener(this);
        imvFB.setOnClickListener(this);
        imvLkdn.setOnClickListener(this);
        imvTwiter.setOnClickListener(this);

        txvLogin.setOnClickListener(this);

    }
    private void navToSignLoginFragment() {
        FragmentManager fm = getFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        Fragment frag = new SignInFragment();
        ft.setCustomAnimations(R.anim.enter_from_right, R.anim.exit_to_left,
                R.anim.enter_from_left, R.anim.exit_to_right);//not required
        ft.add(R.id.act_intro_content_frg, frag, AppConstt.FragTag.FN_SignInFragment);

        ft.addToBackStack(AppConstt.FragTag.FN_SignInFragment);

        ft.hide(this);
        ft.commit();
    }
    @Override
    public void onClick(View v) {

        switch (v.getId()) {

            case R.id.frg_signup_llFb:


                navToSignUPFBFragment();

                break;

            case R.id.frg_signupfb_txvLogin:

                navToSignLoginFragment();
                break;

        }
    }


}
