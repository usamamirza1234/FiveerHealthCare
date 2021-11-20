package com.uk.fiveerhealthcare.Utils;

/**
 * Created by bilalahmed on 04/05/2018.
 * bilalahmed.cs@live.com
 */

public interface IWebCallback {

    void onWebResult(boolean isSuccess, String strMsg);

    void onWebException(Exception ex);
}
