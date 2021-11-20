package com.uk.fiveerhealthcare.Utils;

/**
 * Created by bilalahmed on 04/05/2018.
 * bilalahmed.cs@live.com
 */

public interface IBadgeUpdateListener {

    public void setBottomTabVisiblity(int mVisibility);

    public void setToolbarVisiblity(int mVisibility);

    public void setToolbarState(byte mState);

    public void switchStatusBarColor(boolean isDark);

    public boolean setHeaderTitle(String strAppTitle);



}
