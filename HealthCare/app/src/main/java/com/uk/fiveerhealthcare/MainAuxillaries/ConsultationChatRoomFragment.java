package com.uk.fiveerhealthcare.MainAuxillaries;

import android.app.Dialog;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.uk.fiveerhealthcare.R;
import com.uk.fiveerhealthcare.Utils.AppConstt;
import com.uk.fiveerhealthcare.Utils.IBadgeUpdateListener;

import org.jitsi.meet.sdk.JitsiMeet;
import org.jitsi.meet.sdk.JitsiMeetActivity;
import org.jitsi.meet.sdk.JitsiMeetConferenceOptions;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.Random;


public class ConsultationChatRoomFragment extends Fragment
        implements View.OnClickListener {
    Bundle bundle;
    TextView txvName;
    String strName, strDesc, strType;
    ImageView civProfile;
    LinearLayout llNext;
    LinearLayout llAdmHospt;
    IBadgeUpdateListener mBadgeUpdateListener;
    View frg;
    EditText secretCodeBox;
    Button joinBtn, shareBtn;
    private Dialog progressDialog;

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        frg = inflater.inflate(R.layout.fragment_consultation_chatroom, container, false);

        init();


        bindviews(frg);
        setupData();
        setupCall();
        return frg;

    }

    private void setupCall() {

        URL serverURL;
        try {
            serverURL = new URL("https://meet.jit.si");
            JitsiMeetConferenceOptions defaultOptions =
                    new JitsiMeetConferenceOptions.Builder()
                            .setServerURL(serverURL)
                            .setWelcomePageEnabled(true)

                            .build();
            JitsiMeet.setDefaultConferenceOptions(defaultOptions);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }


    }

    private void init() {
        setBottomBar();
        bundle = getArguments();
        if (bundle != null) {
            strName = bundle.getString("key_name");

            strType = bundle.getString("key_type");
        }

    }


    private void bindviews(View frg) {

        llNext = frg.findViewById(R.id.frg_treatmentConfirm_llNext);
        llAdmHospt = frg.findViewById(R.id.frg_treatmentConfirm_llAdmHospt);
        civProfile = frg.findViewById(R.id.frg_treatmentConfirm_civProfile);
        txvName = frg.findViewById(R.id.frg_treatmentConfirm_txvName);
        llNext.setOnClickListener(this);
        llAdmHospt.setOnClickListener(this);


    }

    private void setupData() {
        txvName.setText(strName);

        if (strName.equalsIgnoreCase("Dr. Murat Tuzcu")) {
            civProfile.setImageDrawable(getContext().getResources().getDrawable(R.drawable.ic_dr1));
        } else if (strName.equalsIgnoreCase("Dr. Jassem Abdou")) {
            civProfile.setImageDrawable(getContext().getResources().getDrawable(R.drawable.ic_dr2));
        } else if (strName.equalsIgnoreCase("Dr. Julieta Zuluaga")) {
            civProfile.setImageDrawable(getContext().getResources().getDrawable(R.drawable.ic_profile));
        } else {
            civProfile.setImageDrawable(getContext().getResources().getDrawable(R.drawable.ic_dr4));
        }

    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {

            case R.id.frg_treatmentConfirm_llNext:
                showProgDialog();

                break;
            case R.id.frg_treatmentConfirm_llAdmHospt:

                navtoCustomerProfileFragment();
                break;

        }
    }

    void setBottomBar() {
        try {
            mBadgeUpdateListener = (IBadgeUpdateListener) getActivity();
        } catch (ClassCastException castException) {
            castException.printStackTrace(); // The activity does not implement the listener
        }
        if (getActivity() != null && isAdded()) {
            mBadgeUpdateListener.setHeaderTitle("Payment");
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

    private void navtoCustomerProfileFragment() {
        FragmentManager fm = getFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        Fragment frag = new CustomerProfileFragment();
        Bundle bundle = new Bundle();
        bundle.putString("key_name", strName);
        bundle.putString("key_type", strType);
        frag.setArguments(bundle);
        ft.setCustomAnimations(R.anim.enter_from_right, R.anim.exit_to_left,
                R.anim.enter_from_left, R.anim.exit_to_right);//not required
        ft.add(R.id.act_main_content_frg, frag, AppConstt.FragTag.FN_CustomerProfileFragment);

        ft.addToBackStack(AppConstt.FragTag.FN_CustomerProfileFragment);

        ft.hide(this);
        ft.commit();
    }


    private void dismissDialog() {
        if (progressDialog != null) {
            progressDialog.dismiss();
        }
    }

    private void showProgDialog() {
        progressDialog = new Dialog(getActivity(), R.style.AppTheme);
        progressDialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));

        progressDialog.setContentView(R.layout.dialog_video_call);
        secretCodeBox = progressDialog.findViewById(R.id.codeBox);
        joinBtn = progressDialog.findViewById(R.id.joinBtn);
        shareBtn = progressDialog.findViewById(R.id.shareBtn);

        secretCodeBox.setText( getRandomString(6));
        joinBtn.setOnClickListener(v -> {
            dismissDialog();
            JitsiMeetConferenceOptions options = new
                    JitsiMeetConferenceOptions.Builder()
                    .setRoom(secretCodeBox.getText().toString())
                    .setWelcomePageEnabled(false)
                    .setSubject("Consultation")
                    .build();
            JitsiMeetActivity.launch(getActivity(), options);
        });
        progressDialog.setCancelable(true);
        progressDialog.show();
    }

    private static final String ALLOWED_CHARACTERS ="0123456789qwertyuiopasdfghjklzxcvbnm";

    private static String getRandomString(final int sizeOfRandomString)
    {
        final Random random=new Random();
        final StringBuilder sb=new StringBuilder(sizeOfRandomString);
        for(int i=0;i<sizeOfRandomString;++i)
            sb.append(ALLOWED_CHARACTERS.charAt(random.nextInt(ALLOWED_CHARACTERS.length())));
        return sb.toString();
    }
}

