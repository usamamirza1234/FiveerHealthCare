package com.uk.fiveerhealthcare.IntroAuxilaries;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.Task;
import com.uk.fiveerhealthcare.R;
import com.uk.fiveerhealthcare.Utils.AppConstt;
import com.uk.fiveerhealthcare.Utils.CustomToast;

public class SignUPSocialFragment extends Fragment
        implements View.OnClickListener {

    private static final int RC_SIGN_IN = 9001;
    TextView txvLogin;
    Bundle bundle;
    ImageView imvFB, imvLkdn, imvGogle, imvTwiter;
    LinearLayout llFB, llLkdn, llGogle, llTwiter;
    RelativeLayout rlEmail;
    String type = "";
    GoogleSignInAccount acct, account;
    GoogleSignInClient mGoogleSignInClient;
    CallbackManager callbackManager;

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View frg = inflater.inflate(R.layout.fragment_sign_in_facebook, container, false);

        init();
        bindviews(frg);
        setdata();
        return frg;
    }

    private void setdata() {
        if (type.equalsIgnoreCase("fb")) {
            imvFB.setVisibility(View.GONE);

            imvGogle.setVisibility(View.VISIBLE);
            imvLkdn.setVisibility(View.VISIBLE);
            imvTwiter.setVisibility(View.VISIBLE);

            llFB.setVisibility(View.VISIBLE);

            llGogle.setVisibility(View.GONE);
            llLkdn.setVisibility(View.GONE);
            llTwiter.setVisibility(View.GONE);


        } else if (type.equalsIgnoreCase("gogl")) {

            imvFB.setVisibility(View.VISIBLE);
            imvGogle.setVisibility(View.GONE);
            imvLkdn.setVisibility(View.VISIBLE);
            imvTwiter.setVisibility(View.VISIBLE);

            llFB.setVisibility(View.GONE);
            llGogle.setVisibility(View.VISIBLE);
            llLkdn.setVisibility(View.GONE);
            llTwiter.setVisibility(View.GONE);

        } else if (type.equalsIgnoreCase("lknd")) {
            imvFB.setVisibility(View.VISIBLE);
            imvGogle.setVisibility(View.VISIBLE);
            imvLkdn.setVisibility(View.GONE);
            imvTwiter.setVisibility(View.VISIBLE);

            llFB.setVisibility(View.GONE);
            llGogle.setVisibility(View.GONE);
            llLkdn.setVisibility(View.VISIBLE);
            llTwiter.setVisibility(View.GONE);
        } else if (type.equalsIgnoreCase("twitr")) {
            imvFB.setVisibility(View.VISIBLE);
            imvGogle.setVisibility(View.VISIBLE);
            imvLkdn.setVisibility(View.VISIBLE);
            imvTwiter.setVisibility(View.GONE);

            llFB.setVisibility(View.GONE);
            llGogle.setVisibility(View.GONE);
            llLkdn.setVisibility(View.GONE);
            llTwiter.setVisibility(View.VISIBLE);
        }
    }

    private void init() {
        bundle = getArguments();
        if (bundle != null) {
            type = bundle.getString("key_type");
        }
        googleInit();
    }


    private void bindviews(View frg) {
        txvLogin = frg.findViewById(R.id.frg_signupfb_txvLogin);

        rlEmail = frg.findViewById(R.id.frg_signup_rlEmail);

        imvGogle = frg.findViewById(R.id.frg_signup_imvgoogle);
        imvFB = frg.findViewById(R.id.frg_signup_imvfb);
        imvLkdn = frg.findViewById(R.id.frg_signup_imvlnd);
        imvTwiter = frg.findViewById(R.id.frg_signup_imvtwiter);

        llGogle = frg.findViewById(R.id.frg_signup_llGoogle);
        llFB = frg.findViewById(R.id.frg_signup_llFb);
        llLkdn = frg.findViewById(R.id.frg_signup_llLinkdin);
        llTwiter = frg.findViewById(R.id.frg_signup_llTwitter);


        rlEmail.setOnClickListener(this);

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

            case R.id.frg_signup_rlEmail:
                getActivity().onBackPressed();
                break;
            case R.id.frg_signup_llGoogle:
                googleSignIn();
                break;
            case R.id.frg_signup_llFb:
                facebookSignin();
                break;

            case R.id.frg_signup_imvfb:
                type = ("fb");
                setdata();
                break;

            case R.id.frg_signup_imvgoogle:
                type = ("gogl");
                setdata();
                break;

            case R.id.frg_signup_imvlnd:
                type = ("lknd");
                setdata();
                break;

            case R.id.frg_signup_imvtwiter:
                type = ("twitr");
                setdata();
                break;


            case R.id.frg_signupfb_txvLogin:

                navToSignLoginFragment();
                break;

        }
    }


    //region Google Facebook Integration
    private void facebookSignin() {
        callbackManager = CallbackManager.Factory.create();
//                llFB.setReadPermissions(Arrays.asList(EMAIL));
        LoginManager.getInstance().registerCallback(callbackManager,
                new FacebookCallback<LoginResult>() {
                    @Override
                    public void onSuccess(LoginResult loginResult) {
                        // App code
                        Log.i("LoginActivity", "FB Login Success");

//
//
//                        AccessToken accessToken = loginResult.getAccessToken();
//                        Profile profile = Profile.getCurrentProfile();
//
//                        // Facebook Email address
//                        GraphRequest request = GraphRequest.newMeRequest(
//                                loginResult.getAccessToken(),
//                                new GraphRequest.GraphJSONObjectCallback() {
//                                    @Override
//                                    public void onCompleted(
//                                            JSONObject object,
//                                            GraphResponse response) {
//                                        Log.v("LoginActivity Response ", response.toString());
//                                        String Name, FEmail;
//
//                                        try {
//                                            Name = object.getString("name");
//
//                                            FEmail = object.getString("email");
//                                            Log.v("Email = ", " " + FEmail);
////                                                    CustomToast.showToastMessage(getActivity(), "Name " + Name, Toast.LENGTH_LONG);
//
//
////                                            requestSocial( FEmail, Name);
//
//                                        } catch (JSONException e) {
//                                            e.printStackTrace();
//                                        }
//                                    }
//                                });
//                        Bundle parameters = new Bundle();
//                        parameters.putString("fields", "id,name,email,gender, birthday");
//                        request.setParameters(parameters);
//                        request.executeAsync();


                    }

                    @Override
                    public void onCancel() {
                        // App code
                        Log.i("LoginActivity", "FB Login Cancel");
                        LoginManager.getInstance().logOut();
                        Toast.makeText(getContext(), "Cancel", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onError(FacebookException exception) {
                        // App code
                        Log.i("LoginActivity", "FB Login Error");
                        Toast.makeText(getContext(), "error_login", Toast.LENGTH_SHORT).show();
                    }
                });

    }


    private void googleInit() {
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();
        mGoogleSignInClient = GoogleSignIn.getClient(getActivity(), gso);

//        GoogleSignInAccount acct = GoogleSignIn.getLastSignedInAccount(getActivity());
//        if (acct != null) {
//
////            String personName = acct.getDisplayName();
////            String personGivenName = acct.getGivenName();
////            String personFamilyName = acct.getFamilyName();
////            String personEmail = acct.getEmail();
////            String personId = acct.getId();
////            Uri personPhoto = acct.getPhotoUrl();
////            CustomToast.showToastMessage(getActivity(), "Signed in successfully with Google " + acct.getDisplayName(), Toast.LENGTH_LONG);
//        }
    }

    public void googleSignIn() {
        Intent signInIntent = mGoogleSignInClient.getSignInIntent();
        startActivityForResult(signInIntent, RC_SIGN_IN);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        try {
            callbackManager.onActivityResult(requestCode, resultCode, data);
        } catch (Exception e) {

        }
        // Result returned from launching the Intent from GoogleSignInClient.getSignInIntent(...);
        if (requestCode == RC_SIGN_IN) {
            Log.d("LOG_AS", "onActivityResult: google sign in " + data.toString());
            // The Task returned from this call is always completed, no need to attach
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            handleSignInResult(task);
        }
    }

    private void handleSignInResult(Task<GoogleSignInAccount> completedTask) {
        try {
            account = completedTask.getResult(ApiException.class);
            acct = GoogleSignIn.getLastSignedInAccount(getActivity());
            Log.d("LOG_AS", "Google Obj : " + acct.getId());

            if (acct != null) {
//                googleUserEmail = acct.getEmail();
//                googleSocailID = acct.getId()
//                requestSocial(acct.getEmail(),"");
//
//                JsonObject jsonObject = new JsonObject();
//                jsonObject.addProperty("name", acct.getDisplayName());
//                jsonObject.addProperty("email", googleUserEmail);
//                jsonObject.addProperty("social_id", googleSocailID);
//                jsonObject.addProperty("social_platform", "google");
//                jsonObject.addProperty("user_type", "user");
//                jsonObject.addProperty("city_id", "1");
//                jsonObject.addProperty("city", "jaddah");
//                jsonObject.addProperty("device_token", AppConfig.getInstance().loadFCMDeviceToken());
//                jsonObject.addProperty("login_type", "social");
//                jsonObject.addProperty("device_type", "android");
//                Log.d("LOG_AS", "Google Sign IN JSON : " + jsonObject.toString());
//
//                requestSignInGoogle(jsonObject.toString());
            }


            //    updateUI(account);
        } catch (ApiException e) {
            // The ApiException status code indicates the detailed failure reason.
            // Please refer to the GoogleSignInStatusCodes class reference for more information.
            Log.w("LOG_AS", "signInResult:failed code=" + e.toString());
            CustomToast.showToastMessage(getActivity(), "Sign in to google is FAILED!" + e.toString(), Toast.LENGTH_LONG);
            // updateUI(null);
        }
    }


    //endregion


}
