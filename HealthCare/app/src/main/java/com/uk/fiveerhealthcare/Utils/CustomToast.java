package com.uk.fiveerhealthcare.Utils;

import android.app.Activity;
import android.content.Context;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.uk.fiveerhealthcare.R;



public class CustomToast {
    private static Toast toast;

    public static void showToastMessage(Activity mContext, String strMessage, int duration) {

        if (mContext != null) {
            LayoutInflater inflater = (LayoutInflater) mContext
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);

            View layout = inflater.inflate(R.layout.toast_custom,
                    (ViewGroup) ((Activity) mContext).findViewById(R.id.toast_container));
            // set a message
            TextView text = (TextView) layout.findViewById(R.id.toast_txv_message);
            text.setText(strMessage);

            // Toast...
            if (toast != null)
                toast.cancel();

            toast = new Toast(mContext);
            toast.setGravity(Gravity.TOP | Gravity.CENTER_HORIZONTAL, 0, com.uk.fiveerhealthcare.AppConfig.getInstance().marginToast);
            toast.setDuration(duration == Toast.LENGTH_LONG ? Toast.LENGTH_LONG : Toast.LENGTH_SHORT);
            toast.setView(layout);
            toast.show();
        }
    }
}
