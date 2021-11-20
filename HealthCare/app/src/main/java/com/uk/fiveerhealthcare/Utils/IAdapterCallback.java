package com.uk.fiveerhealthcare.Utils;

/**
 * Created by bilalahmed on 04/05/2018.
 * bilalahmed.cs@live.com
 */

public interface IAdapterCallback {

    int EVENT_A = 1;
    int EVENT_B = 2;
    int EVENT_C = 3;
    int EVENT_D =4 ;
    int EVENT_F =5 ;

    void onAdapterEventFired(int eventId, int position);
}
