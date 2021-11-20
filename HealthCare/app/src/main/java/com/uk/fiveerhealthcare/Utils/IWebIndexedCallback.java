package com.uk.fiveerhealthcare.Utils;

/**
 * Created by bilalahmed on 04/05/2018.
 * bilalahmed.cs@live.com
 */

public interface IWebIndexedCallback {

    void onWebResult(boolean isSuccess, String strMsg, int index);

    void onWebException(Exception ex, int index);
}
