package com.uk.fiveerhealthcare.Utils;

import android.content.Context;
import android.util.AttributeSet;

import androidx.appcompat.widget.AppCompatSpinner;

public class HintSpinnerDefSelection extends AppCompatSpinner {

    OnItemSelectedListener listener;
    int prevPos = 0;
    public HintSpinnerDefSelection(Context context) {
        super(context);
    }

    public HintSpinnerDefSelection(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public HintSpinnerDefSelection(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    @Override
    public void setSelection(int position) {
        super.setSelection(position);
        if (position == getSelectedItemPosition() && prevPos == position) {
            getOnItemSelectedListener().onItemSelected(null, null, position, 0);
        }
        prevPos = position;
    }
}