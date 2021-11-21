package com.uk.fiveerhealthcare;

import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.uk.fiveerhealthcare.MainAuxillaries.HomeFragment;
import com.uk.fiveerhealthcare.Utils.AppConstt;
import com.uk.fiveerhealthcare.Utils.IBadgeUpdateListener;

public class MainActivity extends AppCompatActivity implements View.OnClickListener, IBadgeUpdateListener {

    private final byte BOTTOM_TAB_HOME = 0;
    private final byte NUM_BOTTOM_TABS = 4;
    private final byte BOTTOM_TAB_EVENT = 1;
    private final byte BOTTOM_TAB_PROFILE = 2;
    private final byte BOTTOM_TAB_MORE = 3;
    TextView txvTitle;
    ImageView imvSearch;
    private FragmentManager fm;
    private RelativeLayout rlToolbar;
    private CheckBox[] arrchbBottomTab;
    private TextView[] arrtxvBottomTab;
    private LinearLayout[] arrllBottomTab;
    private RelativeLayout rlBottomTabContainer;
    private int colorBtabOn, colorBtabOff;
    private int bottomTabState;

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
        setBottomTabBar();
        navToHomeFragment();
    }


    private void init() {
        fm = getSupportFragmentManager();
    }

    private void bindviews() {
        rlToolbar = findViewById(R.id.act_main_rl_toolbar);
        imvSearch = findViewById(R.id.lay_toolbar_imSearch);
        txvTitle = findViewById(R.id.lay_toolbar_txvTitle);
    }

    public void navToHomeFragment() {
        switchBottomTab(BOTTOM_TAB_HOME);
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

    private void setBottomTabBar() {

        colorBtabOn = ContextCompat.getColor(this, R.color.white);
        colorBtabOff = ContextCompat.getColor(this, R.color.light_blue_app);

        arrllBottomTab = new LinearLayout[NUM_BOTTOM_TABS];
        arrllBottomTab[BOTTOM_TAB_HOME] = findViewById(R.id.laybttab_ll_prdhome);
        arrllBottomTab[BOTTOM_TAB_EVENT] = findViewById(R.id.laybttab_ll_events);
        arrllBottomTab[BOTTOM_TAB_PROFILE] = findViewById(R.id.laybttab_ll_profile);
        arrllBottomTab[BOTTOM_TAB_MORE] = findViewById(R.id.laybttab_ll_more);
        for (int i = 0; i < NUM_BOTTOM_TABS; i++)
            arrllBottomTab[i].setOnClickListener(this);


        arrchbBottomTab = new CheckBox[NUM_BOTTOM_TABS];
        arrchbBottomTab[BOTTOM_TAB_HOME] = findViewById(R.id.laybttab_chb_prdhome);
        arrchbBottomTab[BOTTOM_TAB_EVENT] = findViewById(R.id.laybttab_chb_events);
        arrchbBottomTab[BOTTOM_TAB_PROFILE] = findViewById(R.id.laybttab_chb_profile);
        arrchbBottomTab[BOTTOM_TAB_MORE] = findViewById(R.id.laybttab_chb_more);

        arrtxvBottomTab = new TextView[NUM_BOTTOM_TABS];
        arrtxvBottomTab[BOTTOM_TAB_HOME] = findViewById(R.id.laybttab_txv_prdhome);
        arrtxvBottomTab[BOTTOM_TAB_EVENT] = findViewById(R.id.laybttab_txv_events);
        arrtxvBottomTab[BOTTOM_TAB_PROFILE] = findViewById(R.id.laybttab_txv_profile);
        arrtxvBottomTab[BOTTOM_TAB_MORE] = findViewById(R.id.laybttab_txv_more);

        rlBottomTabContainer = findViewById(R.id.act_main_rl_bttabbar);

        setBottomTabVisiblity(View.VISIBLE);
    }
    //region IBadgeUpdateListener

    @Override
    public void setBottomTabVisiblity(int mVisibility) {

        if (mVisibility == View.GONE) {
            rlBottomTabContainer.setVisibility(View.GONE);

        } else {
            rlBottomTabContainer.setVisibility(View.VISIBLE);

        }
    }

    @Override
    public void setToolbarVisiblity(int mVisibility) {

        if (mVisibility == View.GONE) {
            rlToolbar.setVisibility(View.GONE);
        } else {
            rlToolbar.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void setToolbarState(byte mState) {
        switch (mState) {
            case AppConstt.ToolbarState.TOOLBAR_VISIBLE:
                rlToolbar.setVisibility(View.VISIBLE);
                break;
            case AppConstt.ToolbarState.TOOLBAR_HIDDEN:
                rlToolbar.setVisibility(View.GONE);
                break;
            default:
                rlToolbar.setVisibility(View.GONE);
                break;

        }
    }

    @Override
    public void switchStatusBarColor(boolean isDark) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = getWindow();
//                window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
//                window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            if (isDark)
                window.setStatusBarColor(Color.BLACK);
            else
                window.setStatusBarColor(ContextCompat.getColor(this, R.color.thm_light_green));
        }
    }

    @Override
    public boolean setHeaderTitle(String strAppTitle) {
        try {
            txvTitle.setText(strAppTitle);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {

            case R.id.laybttab_ll_prdhome:
                navToHomeFragment();
                break;

            case R.id.laybttab_ll_events:

                navToHomeFragment();
                break;

            case R.id.laybttab_ll_profile:

                navToHomeFragment();
                break;

            case R.id.laybttab_ll_more:

                navToHomeFragment();
                break;

        }
    }

    public void switchBottomTab(int tabNum) {
        for (int i = 0; i < NUM_BOTTOM_TABS; i++) {
            arrchbBottomTab[i].setChecked(i == tabNum);
            arrtxvBottomTab[i].setTextColor((i == tabNum) ? colorBtabOn : colorBtabOff);
            arrtxvBottomTab[i].setVisibility((i == tabNum) ? View.VISIBLE : View.GONE);
            bottomTabState = i;
//            if (i == tabNum)
//                arrtxvBottomTab[i].setTextColor(getResources().getColor(R.color.thm_red1));
//            else
//                arrtxvBottomTab[i].setVisibility(View.GONE);
        }

    }

    //endregion
}
