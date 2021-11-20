package com.uk.fiveerhealthcare.Utils;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import androidx.appcompat.widget.AppCompatButton;

import com.uk.fiveerhealthcare.R;



public class HintSpinner extends RelativeLayout {

    private AppCompatButton _hintButton;
    private HintSpinnerDefSelection _spinner;
    private ImageView _downArrow;
    private ArrayAdapter<String> _spinnerAdapterString;
    private ArrayAdapter<CharSequence> _spinnerAdapterCharSequence;
    private boolean _allowToSelect;
    private onSpinnerItemClickListener<String> _callback;
    private static final String TAG = HintSpinner.class.getSimpleName();
    private boolean _isItemResourceDeclared = false;
    private int _spinnerType = 0;
    private boolean _isSelected;

    public HintSpinner(Context context) {
        super(context);
        init(null);
    }

    public HintSpinner(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(attrs);
    }

    public HintSpinner(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init(attrs);
    }

    private void init(AttributeSet attrs) {
        inflate(getContext(), R.layout.spinner_view, this);
        this._hintButton = (AppCompatButton) findViewById(R.id.awesomeSpinner_hintButton);
        this._spinner = (HintSpinnerDefSelection) findViewById(R.id.awesomeSpinner_spinner);
        this._downArrow = (ImageView) findViewById(R.id.awesomeSpinner_downArrow);

        if(attrs != null){
            setSpinnerStyle(getContext().obtainStyledAttributes(attrs, R.styleable.AwesomeSpinnerStyle, 0, 0));
        }

    }

    private void setSpinnerStyle(TypedArray typedArray){

        _hintButton.setText(typedArray.getString(R.styleable.AwesomeSpinnerStyle_spinnerHint));

//        LayoutParams params = new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
//        params.setMargins(10,40,10,-30);
        switch (typedArray.getInt(R.styleable.AwesomeSpinnerStyle_spinnerDirection, 0)){
            case 0:
//                _hintButton.setGravity(Gravity.LEFT | Gravity.BOTTOM);
//                params.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
//                _downArrow.setLayoutParams(params);
                break;
            case 1:
//                _hintButton.setGravity(Gravity.RIGHT | Gravity.BOTTOM);
//                params.addRule(RelativeLayout.ALIGN_PARENT_LEFT);
//                _downArrow.setLayoutParams(params);

                break;
        }

    }

    public void setAdapter(ArrayAdapter<String> adapter){
        this._spinnerAdapterString = adapter;
        _spinner.setAdapter(_spinnerAdapterString);
        initiateSpinnerString();
    }

    public void setAdapter(ArrayAdapter<CharSequence> adapter, int idle){
        _spinnerType = 1;
        this._spinnerAdapterCharSequence = adapter;
        _spinner.setAdapter(_spinnerAdapterCharSequence);
        initiateSpinnerCharSequence();
    }

    public boolean isSelected(){
        return _isSelected;
    }

    private void initiateSpinnerString(){

        if(!_isItemResourceDeclared){
            _spinnerAdapterString.setDropDownViewResource(R.layout.spinner_list_item);
        }

        _spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
//                Log.d(TAG, "position selected: " + position);
                if (HintSpinner.this._callback == null) {
                    throw new IllegalStateException("callback cannot be null");
                }
                if(_allowToSelect){
                    Object item = HintSpinner.this._spinner.getItemAtPosition(position);
                    HintSpinner.this._callback.onItemSelected(position, (String) item);
                    _hintButton.setText(_spinner.getSelectedItem().toString());
                    _hintButton.setTextColor(Color.BLACK);
                    _isSelected = true;
                }
                _allowToSelect = true;

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                Log.d(TAG, "Nothing selected");
            }
        });


    }

    private void initiateSpinnerCharSequence(){

        if(!_isItemResourceDeclared){
            _spinnerAdapterCharSequence.setDropDownViewResource(R.layout.spinner_list_item);
        }

        _spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Log.d(TAG, "position selected: " + position);
                if (HintSpinner.this._callback == null) {
                    throw new IllegalStateException("callback cannot be null");
                }
                if(_allowToSelect){
                    Object item = HintSpinner.this._spinner.getItemAtPosition(position);
                    HintSpinner.this._callback.onItemSelected(position, (String) item);
                    _hintButton.setText(_spinner.getSelectedItem().toString());
                    _hintButton.setTextColor(Color.BLACK);
                    _isSelected = true;
                }
                _allowToSelect = true;

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                Log.d(TAG, "Nothing selected");
            }
        });


    }

    public void setDropDownViewResource(int resource){

        if(_spinnerType == 1){
            _spinnerAdapterCharSequence.setDropDownViewResource(resource);
        }else{
            _spinnerAdapterString.setDropDownViewResource(resource);
        }

        _isItemResourceDeclared = true;

    }

    public void setOnSpinnerItemClickListener(onSpinnerItemClickListener<String> callback){

        this._callback = callback;

        _hintButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                _spinner.performClick();
            }
        });
    }

    public void setSelection(int position){
        _allowToSelect = true;
        _spinner.setSelection(position);
    }

    public void setSelection(String value){
        if(value.trim().isEmpty()){
            _allowToSelect = true;
            if(_spinnerType == 0){
                int spinnerPosition = _spinnerAdapterString.getPosition(value);
                _spinner.setSelection(spinnerPosition);
            }else{
                int spinnerPosition = _spinnerAdapterCharSequence.getPosition(value);
                _spinner.setSelection(spinnerPosition);
            }
        }
    }

    public String getSelectedItem(){
        if(isSelected()){
            return _spinner.getSelectedItem().toString();
        }else{
            return null;
        }
    }

    public HintSpinnerDefSelection getSpinner(){
        return _spinner;
    }

    public int getSelectedItemPosition(){
        return _spinner.getSelectedItemPosition();
    }

    public interface onSpinnerItemClickListener<T> {
        /**
         * When a spinner item has been selected.
         *
         * @param position Position selected
         * @param itemAtPosition Item selected
         */
        void onItemSelected(int position, T itemAtPosition);
    }


}
