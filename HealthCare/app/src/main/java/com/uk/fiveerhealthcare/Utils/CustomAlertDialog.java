package com.uk.fiveerhealthcare.Utils;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;

import java.util.Objects;

import com.uk.fiveerhealthcare.R;

public class CustomAlertDialog extends Dialog implements View.OnClickListener  {
    private String strTitle, strMsgToShow, strPositiveBtnText, strNegativeBtnText;
    private boolean isNegativeBtnRequired;
    private CustomAlertConfirmationInterface customAlertConfirmationInterface;

    public CustomAlertDialog(@NonNull Context context, final String _title, String _msgToShow, String _positiveBtnText,
                             String _negativeBtnText, boolean _isNegativeButtonRequired,
                             final CustomAlertConfirmationInterface _customDialogConfirmationListener) {
        super(context);
        this.strTitle = _title;
        this.strMsgToShow = _msgToShow;
        this.strPositiveBtnText = _positiveBtnText;
        this.strNegativeBtnText = _negativeBtnText;
        this.isNegativeBtnRequired = _isNegativeButtonRequired;
        this.customAlertConfirmationInterface = _customDialogConfirmationListener;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.dlg_custom_alert);
        Objects.requireNonNull(getWindow()).setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        bindViews();
    }

    private void bindViews() {
        TextView txvTitle = findViewById(R.id.dlg_cstm_alrt_title);
        TextView txvMessage = findViewById(R.id.dlg_cstm_alrt_text_msg);
        Button btnPositive = findViewById(R.id.dlg_cstm_alrt_btn_ok);
        Button btnNegative = findViewById(R.id.dlg_cstm_alrt_btn_cancel);
        View dividerView = findViewById(R.id.dlg_cstm_alrt_separator);

        if (strTitle != null && strTitle.length() > 0) {
            txvTitle.setText(strTitle);
        }
        if (strMsgToShow != null && strMsgToShow.length() > 0) {
            txvMessage.setText(strMsgToShow);
        }
        if (strPositiveBtnText != null && strPositiveBtnText.length() > 0) {
            btnPositive.setText(strPositiveBtnText);
        }

        if (strPositiveBtnText != null && strPositiveBtnText.length() > 0) {
            btnPositive.setText(strPositiveBtnText);
        }

        if (isNegativeBtnRequired) {
            if (strNegativeBtnText != null && strNegativeBtnText.length() > 0) {
                btnNegative.setText(strNegativeBtnText);
            }
        } else {
            btnNegative.setVisibility(View.GONE);
            dividerView.setVisibility(View.GONE);
        }

        btnPositive.setOnClickListener(this);
        btnNegative.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.dlg_cstm_alrt_btn_ok:
                if (customAlertConfirmationInterface != null)
                    customAlertConfirmationInterface.callConfirmationDialogPositive();
                this.dismiss();
                break;

            case R.id.dlg_cstm_alrt_btn_cancel:
                if (customAlertConfirmationInterface != null)
                    customAlertConfirmationInterface.callConfirmationDialogNegative();
                this.dismiss();
                break;

        }
    }
}
