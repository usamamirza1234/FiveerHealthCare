package com.uk.fiveerhealthcare.IntroAuxilaries;


import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.uk.fiveerhealthcare.AppConfig;
import com.uk.fiveerhealthcare.IntroActivity;
import com.uk.fiveerhealthcare.MainActivity;
import com.uk.fiveerhealthcare.R;
import com.uk.fiveerhealthcare.Utils.AppConstt;
import com.uk.fiveerhealthcare.Utils.IBadgeUpdateListener;

/**
 * Created by UsamaMirza on 30/04/2021.
 * usamamirza@veroke.com
 */

public class SplashFragment extends Fragment {
    public static int notificationId = AppConstt.Notifications.TYPE_NIL;
    public static int orderId = AppConstt.Notifications.TYPE_NIL;
    private final int DURATION_SPLASH = 1500;
    private IBadgeUpdateListener mBadgeUpdateListener;
    private LinearLayout llEng, llArbc;

    public SplashFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View frg = inflater.inflate(R.layout.fragment_splash, container, false);


        try {
            mBadgeUpdateListener = (IBadgeUpdateListener) getActivity();
        } catch (ClassCastException castException) {
            castException.printStackTrace(); // The activity does not implement the listener
        }
        if (getActivity() != null && isAdded())
            mBadgeUpdateListener.setToolbarState(AppConstt.INTRO_ToolbarStates.choice);


        //Set essential global variables
        AppConfig.getInstance().isCommingFromSplash = true;




        if (!AppConfig.getInstance().loadIsRevised()) {
            AppConfig.getInstance().deleteUserData();
            AppConfig.getInstance().saveIsRevised(true);
        }

        launchSignIn();
        return frg;
    }

    private void launchSignIn() {
        clearPrevCachedData();
        // Timer to run splash screen for 0.5 SECONDS
        final Thread timer = new Thread() {
            @Override
            public void run() {
                try {
                    sleep(DURATION_SPLASH);

                    AppConfig.getInstance().loadUserProfile();
                    if (getActivity() != null)
                        getActivity().runOnUiThread(new Runnable() {
                            public void run() {
//                                if (AppConfig.getInstance().mUserData.isLoggedIn) {
//                                    showProfile();
//                                } else {
//                                    //New user
//                           requestSwitchLang();
//                                    navtoChoiceFragment();
//                                }

                                requestSwitchLang();
                                if (!AppConfig.getInstance().mUser.isLoggedIn())
                                    navToPreSignInVAFragment();
                                else
                                    ((IntroActivity) getActivity()).navtoMainActivity();


                            }
                        });
                } catch (InterruptedException e) {
                    e.printStackTrace();
//                    getActivity().finish();
                }
            }
        };
        timer.start();
    }

    private void requestSwitchLang() {




    }

    public void navToPreSignInVAFragment() {
        PreSignInFragment frg = new PreSignInFragment();
        FragmentManager fm = getFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        ft.replace(R.id.act_intro_content_frg, frg, AppConstt.FragTag.FN_PreSignInFragment);

        ft.commit();

    }
    private void clearPrevCachedData() {



    }


    private int dpToPix(float _value) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, _value, getResources().getDisplayMetrics());
    }

    private void showProfile() {

        Log.d("SERVER_TOKEN", AppConfig.getInstance().mUser.getAuthorization());
        Intent intent = new Intent();
//        if (AppConfig.getInstance().mUser.Type == AppConstt.UserType.CUSTOMER)
        intent.setClass(getActivity(), MainActivity.class);

        if (notificationId != AppConstt.Notifications.TYPE_NIL) {
            intent.putExtra(AppConstt.Notifications.PUSH_TYPE, notificationId);
            intent.putExtra(AppConstt.Notifications.PUSH_ORDER_ID, orderId);
        }
        intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
        startActivity(intent);

        getActivity().overridePendingTransition(0, 0);
        getActivity().finish();//Not required in the backstack
    }
}



