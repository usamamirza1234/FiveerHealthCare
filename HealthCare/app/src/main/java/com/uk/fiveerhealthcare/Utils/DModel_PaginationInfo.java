package com.uk.fiveerhealthcare.Utils;


public class DModel_PaginationInfo {
    public int currIndex;
    public boolean isCompleted;

    public DModel_PaginationInfo() {
        currIndex = AppConstt.PAGINATION_START_INDEX;
        isCompleted = false;
    }
}
