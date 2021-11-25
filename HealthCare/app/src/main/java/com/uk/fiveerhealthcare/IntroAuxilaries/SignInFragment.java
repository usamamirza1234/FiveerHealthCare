package com.uk.fiveerhealthcare.IntroAuxilaries;

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
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.uk.fiveerhealthcare.AppConfig;
import com.uk.fiveerhealthcare.IntroActivity;
import com.uk.fiveerhealthcare.R;
import com.uk.fiveerhealthcare.Utils.AppConstt;
import com.uk.fiveerhealthcare.Utils.CustomToast;

public class SignInFragment extends Fragment
        implements View.OnClickListener {

    RelativeLayout rlSignin, rlSignUp, rlForgot;
    EditText edtName, edtPassword;
    String str = "";
    ImageView imvfb;
    TextView txvLogin;
    ImageView imvFB, imvLkdn, imvGogle, imvTwiter;
    private Dialog progressDialog;

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View frg = inflater.inflate(R.layout.fragment_sign_in, container, false);

        init();
        bindviews(frg);


        return frg;

    }

    private void init() {
    }

    private void bindviews(View frg) {

        txvLogin = frg.findViewById(R.id.frg_signup_txvLogin);
        rlSignin = frg.findViewById(R.id.frg_signin_rlLogin);
        edtName = frg.findViewById(R.id.frg_signin_edtName);
        edtPassword = frg.findViewById(R.id.frg_signin_edtPass);


        rlSignin.setOnClickListener(this);
        txvLogin.setOnClickListener(this);


        imvGogle = frg.findViewById(R.id.frg_signup_imvgoogle);
        imvFB = frg.findViewById(R.id.frg_signup_imvfb);
        imvLkdn = frg.findViewById(R.id.frg_signup_imvlnd);
        imvTwiter = frg.findViewById(R.id.frg_signup_imvtwiter);


        imvGogle.setOnClickListener(this);
        imvFB.setOnClickListener(this);
        imvLkdn.setOnClickListener(this);
        imvTwiter.setOnClickListener(this);

        editTextWatchers();
    }

    private void editTextWatchers() {

        edtName.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (s.toString().startsWith(" ")) {
                    edtName.setText("");
                }
            }
        });
        edtPassword.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (s.toString().startsWith(" ")) {
                    edtPassword.setText("");
                }
            }
        });

    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.frg_signup_txvLogin:
                navToSignInFragment();
                break;


            case R.id.frg_signup_imvfb:
                navToSignUPFBFragment("fb");
                break;

            case R.id.frg_signup_imvgoogle:
                navToSignUPFBFragment("gogl");
                break;

            case R.id.frg_signup_imvlnd:
                navToSignUPFBFragment("lknd");
                break;

            case R.id.frg_signup_imvtwiter:
                navToSignUPFBFragment("twitr");
                break;

            case R.id.frg_signin_rlLogin:
                checkErrorConditions();
                break;
        }
    }


    private void navToSignUPFBFragment(String type) {
        FragmentManager fm = getFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        Fragment frag = new SignUPSocialFragment();
        Bundle bundle = new Bundle();
        bundle.putString("key_type", type);
        frag.setArguments(bundle);
        ft.setCustomAnimations(R.anim.enter_from_right, R.anim.exit_to_left,
                R.anim.enter_from_left, R.anim.exit_to_right);//not required
        ft.add(R.id.act_intro_content_frg, frag, AppConstt.FragTag.FN_SignUPFBFragment);

        ft.addToBackStack(AppConstt.FragTag.FN_SignUPFBFragment);

        ft.hide(this);
        ft.commit();
    }

    private void navToMainActivity() {
        ((IntroActivity) getActivity()).navtoMainActivity();

    }

    private void checkErrorConditions() {
        if (checkPasswordError()) {


            verifyFromSQLite();
        }
    }


    private void verifyFromSQLite() {

        if (AppConfig.getInstance().database.checkUser(edtName.getText().toString().trim()
                , edtPassword.getText().toString().trim())) {
            CustomToast.showToastMessage(getActivity(), "Login successful ", Toast.LENGTH_LONG);
            AppConfig.getInstance().mUser.setName(edtName.getText().toString());
            AppConfig.getInstance().mUser.setEmail(edtName.getText().toString());
            AppConfig.getInstance().mUser.isLoggedIn = true;
            AppConfig.getInstance().saveUserProfile();
            ((IntroActivity) getActivity()).navtoMainActivity();
        } else {

            CustomToast.showToastMessage(getActivity(), "Failed to login", Toast.LENGTH_LONG);
        }
    }

    private boolean checkPasswordError() {
        if (!edtName.getText().toString().equalsIgnoreCase("") && !edtPassword.getText().toString().isEmpty()) {
            return true;
        } else {
            Toast.makeText(getContext(), "Enter all fields", Toast.LENGTH_LONG).show();
            return false;
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

    //    //region  functions for Dialog
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
//    private void showProgInstallingDataDialog() {
//        progressDialog = new Dialog(getActivity(), R.style.AppTheme);
//        progressDialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
//        progressDialog.setContentView(R.layout.dialog_installing_progress);
//
//        progressDialog.setCancelable(false);
//        progressDialog.show();
//    }
//    private void closeKeyboard() {
//        AppConfig.getInstance().closeKeyboard(getActivity());
//    }
//    //region  functions for API
//    private void requestUserSigin(String _signUpEntity) {
//
//        showProgDialog();
//        Intro_WebHit_Post_LogIn intro_webHit_post_logIn = new Intro_WebHit_Post_LogIn();
//
//        intro_webHit_post_logIn.postSignIn(getContext(), new IWebCallback() {
//            @Override
//            public void onWebResult(boolean isSuccess, String strMsg) {
//                if (isSuccess) {
//
//                    dismissProgDialog();
//
//                    //Save user login data todo un
//                    AppConfig.getInstance().mUser.User_Id = Integer.parseInt(Intro_WebHit_Post_LogIn.responseObject.getUser().getId());
//
//                    AppConfig.getInstance().mUser.Email = Intro_WebHit_Post_LogIn.responseObject.getUser().getLoginID();
//
//                    AppConfig.getInstance().mUser.Authorization = Intro_WebHit_Post_LogIn.responseObject.getToken();
//                    if (Intro_WebHit_Post_LogIn.responseObject.getUser().getName() != null)
//                        Toast.makeText(getActivity(),"error", Toast.LENGTH_SHORT).show();
//                    AppConfig.getInstance().mUser.Name = Intro_WebHit_Post_LogIn.responseObject.getUser().getName();
//                    AppConfig.getInstance().mUser.isLoggedIn = true;
//                    AppConfig.getInstance().saveUserProfile();
//
//
//                    requestDataBase();
//
//                } else {
//                    dismissProgDialog();
//                    Toast.makeText(getActivity(), strMsg, Toast.LENGTH_SHORT).show();
//                    //uncommet todo
//                    AppConfig.getInstance().showErrorMessage(getContext(), strMsg);
//                }
//            }
//
//            @Override
//            public void onWebException(Exception ex) {
//                dismissProgDialog();
//                Toast.makeText(getActivity(), ex.getMessage(), Toast.LENGTH_SHORT).show();
//                AppConfig.getInstance().showErrorMessage(getContext(), ex.toString());
//            }
//        }, _signUpEntity);
//    }
//    private void requestDataBase() {
//        dismissProgDialog();
//        showProgDialog();
//        Home_WebHit_Get_Database home_webHit_get_database = new Home_WebHit_Get_Database();
//        home_webHit_get_database.getDatabase(new IWebCallback() {
//            @Override
//            public void onWebResult(boolean isSuccess, String strMsg) {
//                if (isSuccess) {
//
//                    showProgInstallingDataDialog();
//                    if (strMsg != "") {
//                        //   populateDBOPS(strMsg);
//                    }
//
//                    navToMainActivity();
//                    dismissProgDialog();
//                } else {
//                    dismissProgDialog();
//                    Toast.makeText(getActivity(), strMsg, Toast.LENGTH_SHORT).show();
////                    AppConfig.getInstance().showErrorMessage(getContext(), strMsg);
//                }
//            }
//
//            @Override
//            public void onWebException(Exception ex) {
//                dismissProgDialog();
//                Toast.makeText(getActivity(), ex.getMessage(), Toast.LENGTH_SHORT).show();
////                AppConfig.getInstance().showErrorMessage(getContext(), ex.toString());
//            }
//        });
//    }
//    private void navToSignUpFragment() {
//
//    }
//    private void navToSignInFragment() {
////        FragmentManager fm = getFragmentManager();
////        FragmentTransaction ft = fm.beginTransaction();
////        Fragment frag = new SignInFragment();
////        ft.add(R.id.act_intro_content_frg, frag, AppConstt.FragTag.FN_SignInFragment);
////        ft.addToBackStack(AppConstt.FragTag.FN_SignInFragment);
////        ft.hide(this);
////        ft.commit();
//    }
//    private FragmentManager getSupportFragmentManager() {
//        return null;
//    }
//    private void navToForgotPasswordFragment() {
////        FragmentManager fm = getFragmentManager();
////        FragmentTransaction ft = fm.beginTransaction();
////        Fragment frag = new ForgotPasswordFragment();
////        ft.add(R.id.act_intro_content_frg, frag, AppConstt.FragTag.FN_SignInFragment);
////        ft.addToBackStack(AppConstt.FragTag.FN_SignInFragment);
////        ft.hide(this);
////        ft.commit();
//    }

}
