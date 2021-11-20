package com.uk.fiveerhealthcare;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.uk.fiveerhealthcare.MainAuxillaries.HomeFragment;
import com.uk.fiveerhealthcare.Utils.AppConstt;

public class MainActivity extends AppCompatActivity  {

    private FragmentManager fm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
//        AppConfig.getInstance().performLangCheck(getWindow());
        if (savedInstanceState != null) {
            return;
        }

        init();
        bindviews();
        navToHomeFragment();
    }



    private void init() {
        fm = getSupportFragmentManager();
    }
    private void bindviews()
    {
    }
    public void navToHomeFragment() {
        clearMyBackStack();
        Fragment frg = new HomeFragment();
        FragmentTransaction ft = fm.beginTransaction();
        ft.replace(R.id.act_main_content_frg, frg, AppConstt.FragTag.FN_HomeFragment);
        ft.commit();

    }
    public void clearMyBackStack() {
        int count = fm.getBackStackEntryCount();
        for (int i = 0; i < count; ++i) {
            fm.popBackStackImmediate();

        }

    }
}
