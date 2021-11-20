package com.uk.fiveerhealthcare.Utils;

/**
 * Created by bilalahmed on 04/05/2018.
 * bilalahmed.cs@live.com
 */

public interface IDialogCallback {

    int RESULT_BTN_OK = 1;
    int RESULT_BTN_CANCEL = 2;
    int RESULT_BTN_NEUTRAL = 3;
    int RESULT_BTN_DISMISS = 4;

    void onDialogResult(int resultType);

}

