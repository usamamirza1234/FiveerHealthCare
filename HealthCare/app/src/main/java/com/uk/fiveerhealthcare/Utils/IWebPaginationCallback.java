package com.uk.fiveerhealthcare.Utils;

/**
 * Created by bilalahmed on 04/05/2018.
 * bilalahmed.cs@live.com
 */

public interface IWebPaginationCallback {

    void onWebInitialResult(boolean isSuccess, String strMsg);

    void onWebSuccessiveResult(boolean isSuccess, boolean isCompleted, String strMsg);

    void onWebInitialException(Exception ex);

    void onWebSuccessiveException(Exception ex);
}
