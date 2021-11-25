package com.uk.fiveerhealthcare.IntroAuxilaries;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
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

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.Profile;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.uk.fiveerhealthcare.AppConfig;
import com.uk.fiveerhealthcare.IntroActivity;
import com.uk.fiveerhealthcare.R;
import com.uk.fiveerhealthcare.User;
import com.uk.fiveerhealthcare.Utils.AppConstt;
import com.uk.fiveerhealthcare.Utils.CustomToast;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Arrays;

public class SignUPSocialFragment extends Fragment
        implements View.OnClickListener {
    FirebaseFirestore database;
    FirebaseAuth auth;


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
        auth = FirebaseAuth.getInstance();
        database = FirebaseFirestore.getInstance();
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
        showProgDialog();
        Log.i("LoginActivity", "FB Login ");
        callbackManager = CallbackManager.Factory.create();
//                llFB.setReadPermissions(Arrays.asList(EMAIL));
        LoginManager.getInstance().registerCallback(callbackManager,
                new FacebookCallback<LoginResult>() {
                    @Override
                    public void onSuccess(LoginResult loginResult) {
                        dismissDialog();
                        // App code
                        Log.i("LoginActivity", "FB Login Success");
//                                final AccessToken accessToken = loginResult.getAccessToken();

//                                GraphRequestAsyncTask request = GraphRequest.newMeRequest(accessToken, new GraphRequest.GraphJSONObjectCallback() {
//                                    @Override
//                                    public void onCompleted(JSONObject user, GraphResponse graphResponse) {
//                                        Log.d(TAG, user.optString("email"));
//                                        Log.d(TAG, user.optString("name"));
//                                        Log.d(TAG, user.optString("id"));
//                                        Log.d(TAG, user.toString());
//
//                                        requestSocial( user.optString("id")+"@facebook.com", user.optString("name"));
//                                    }
//                                });


                        AccessToken accessToken = loginResult.getAccessToken();
                        Profile profile = Profile.getCurrentProfile();

                        // Facebook Email address
                        GraphRequest request = GraphRequest.newMeRequest(
                                loginResult.getAccessToken(),
                                new GraphRequest.GraphJSONObjectCallback() {
                                    @Override
                                    public void onCompleted(
                                            JSONObject object,
                                            GraphResponse response) {
                                        Log.v("LoginActivity Response ", response.toString());
                                        String Name, FEmail;

                                        try {
                                            Name = object.getString("name");

                                            FEmail = object.getString("email");
                                            Log.v("Email = ", " " + FEmail);


                                            if (!AppConfig.getInstance().database.checkUser(FEmail.trim())) {

                                                AppConfig.getInstance().mUser.setName(Name.trim());
                                                AppConfig.getInstance().mUser.setEmail(FEmail);
                                                AppConfig.getInstance().mUser.isLoggedIn = true;
                                                AppConfig.getInstance().saveUserProfile();
                                                User user = new User();
                                                user.setName(Name.trim());
                                                user.setEmail(FEmail.trim());
                                                user.setPassword("social");
                                                AppConfig.getInstance().database.addUser(user);
                                                CustomToast.showToastMessage(getActivity(), "Login successful ", Toast.LENGTH_LONG);
                                                ((IntroActivity) getActivity()).navtoMainActivity();
                                            } else if (AppConfig.getInstance().database.checkUser(FEmail.trim()
                                                    , "social".trim())) {
                                                CustomToast.showToastMessage(getActivity(), "Login successful ", Toast.LENGTH_LONG);
                                                AppConfig.getInstance().mUser.setName(Name.trim());
                                                AppConfig.getInstance().mUser.setEmail(FEmail);
                                                AppConfig.getInstance().mUser.isLoggedIn = true;

                                                AppConfig.getInstance().saveUserProfile();
                                                ((IntroActivity) getActivity()).navtoMainActivity();
                                            } else {
                                                CustomToast.showToastMessage(getActivity(), "Failed to login", Toast.LENGTH_LONG);
                                            }

//                                            requestSocial( FEmail, Name);

                                        } catch (JSONException e) {
                                            e.printStackTrace();
                                        }
                                    }
                                });
                        Bundle parameters = new Bundle();
                        parameters.putString("fields", "id,name,email,gender, birthday");
                        request.setParameters(parameters);
                        request.executeAsync();


                    }

                    @Override
                    public void onCancel() {   dismissDialog();
                        // App code
                        Log.i("LoginActivity", "FB Login Cancel");
                        LoginManager.getInstance().logOut();
                        Toast.makeText(getContext(), "Cancel", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onError(FacebookException exception) {
                        dismissDialog();
                        // App code
                        Log.i("LoginActivity", "FB Login Error");
                        Toast.makeText(getContext(), "error_login", Toast.LENGTH_SHORT).show();
                    }
                });


        AccessToken accessToken = AccessToken.getCurrentAccessToken();
        boolean isLoggedIn = accessToken != null && !accessToken.isExpired();


        LoginManager.getInstance().logInWithReadPermissions(this, Arrays.asList("email"));

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
        showProgDialog();
        Intent signInIntent = mGoogleSignInClient.getSignInIntent();
        startActivityForResult(signInIntent, RC_SIGN_IN);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        try {
            callbackManager.onActivityResult(requestCode, resultCode, data);
        } catch (Exception e) {
            Log.d("LoginActivity", "LoginActivity:FB Exception   " + e.toString());
        }
        // Result returned from launching the Intent from GoogleSignInClient.getSignInIntent(...);
        if (requestCode == RC_SIGN_IN) {
            Log.d("LoginActivity", "Google SignIn " + data.toString());
            // The Task returned from this call is always completed, no need to attach
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            handleSignInResult(task);
        }
    }

    private void handleSignInResult(Task<GoogleSignInAccount> completedTask) {
        try {
            dismissDialog();
            account = completedTask.getResult(ApiException.class);
            acct = GoogleSignIn.getLastSignedInAccount(getActivity());
            Log.d("LoginActivity", "Google Obj : " + acct.getId());

            if (acct != null) {

                final User user = new User();
                user.setEmail(acct.getEmail());
                user.setPassword("social");
                user.setName(acct.getDisplayName());

                auth.createUserWithEmailAndPassword(acct.getEmail(), "social").addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()) {
                            database.collection("Users")
                                    .document().set(user).addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void aVoid) {

                                    if (!AppConfig.getInstance().database.checkUser(acct.getEmail().trim())) {

                                        AppConfig.getInstance().mUser.setName(acct.getDisplayName().trim());
                                        AppConfig.getInstance().mUser.setEmail(acct.getEmail().toString());
                                        AppConfig.getInstance().mUser.isLoggedIn = true;
                                        AppConfig.getInstance().saveUserProfile();

                                        AppConfig.getInstance().database.addUser(user);
                                        CustomToast.showToastMessage(getActivity(), "Login successful ", Toast.LENGTH_LONG);
                                        ((IntroActivity) getActivity()).navtoMainActivity();
                                    } else if (AppConfig.getInstance().database.checkUser(acct.getEmail().toString().trim()
                                            , "social".trim())) {
                                        CustomToast.showToastMessage(getActivity(), "Login successful ", Toast.LENGTH_LONG);
                                        AppConfig.getInstance().mUser.setName(acct.getDisplayName().trim());
                                        AppConfig.getInstance().mUser.setEmail(acct.getEmail().toString());
                                        AppConfig.getInstance().mUser.isLoggedIn = true;

                                        AppConfig.getInstance().saveUserProfile();
                                        ((IntroActivity) getActivity()).navtoMainActivity();
                                    } else {
                                        CustomToast.showToastMessage(getActivity(), "Failed to login", Toast.LENGTH_LONG);
                                    }


                                }
                            });
//                            Toast.makeText(SignupActivity.this, "Account is created.", Toast.LENGTH_SHORT).show();
                        } else {
                            CustomToast.showToastMessage(getActivity(), "Failed to login", Toast.LENGTH_LONG);
                        }
                    }
                });
            }


            //    updateUI(account);
        } catch (ApiException e) {
            // The ApiException status code indicates the detailed failure reason.
            // Please refer to the GoogleSignInStatusCodes class reference for more information.
            Log.w("LoginActivity", "signInResult:failed code=" + e.toString());
            CustomToast.showToastMessage(getActivity(), "Sign in to google is FAILED!" + e.toString(), Toast.LENGTH_LONG);
            // updateUI(null);
        }
    }


    //endregion
    private Dialog progressDialog;
    private void dismissDialog() {
        if (progressDialog != null) {
            progressDialog.dismiss();
        }
    }

    private void showProgDialog() {
        progressDialog = new Dialog(getActivity(), R.style.AppTheme);
        progressDialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        progressDialog.setContentView(R.layout.dialog_progress);

        progressDialog.setCancelable(false);
        progressDialog.show();
    }
}
