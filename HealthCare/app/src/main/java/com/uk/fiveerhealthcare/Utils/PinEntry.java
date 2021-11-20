package com.uk.fiveerhealthcare.Utils;

import android.content.Context;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.os.Build;
import android.os.Parcel;
import android.os.Parcelable;
import android.text.Editable;
import android.text.InputFilter;
import android.text.InputType;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.TextView;

import com.uk.fiveerhealthcare.R;


/**
 * A PIN entry view widget for Android based on the Android 5 Material Theme via the AppCompat v7
 * support library.
 */
public class PinEntry extends ViewGroup {

    // ------- EDITED BY ABRAR : CUSTOME
    private boolean applyDigitTextSize;
    private int previousLength;
    private boolean backSpace = true;

    /**
     * Accent types
     */
    public static final int ACCENT_NONE = 0;
    public static final int ACCENT_ALL = 1;
    public static final int ACCENT_CHARACTER = 2;

    /**
     * Number of digits
     */
    private int digits;

    /**
     * Input type
     */
    private int inputType;

    /**
     * Pin digit dimensions and styles
     */
    private int digitWidth;
    private int digitHeight;
    private int digitBackground;
    private int digitSpacing;
    private int digitTextSize;
    private int digitTextColor;
    private int digitElevation;

    /**
     * Accent dimensions and styles
     */
    private int accentType;
    private int accentWidth;
    private int accentColor;

    /**
     * Character to use for each digit
     */
    private String mask = "*";

    /**
     * Edit text to handle input
     */
    private EditText editText;

    /**
     * Focus change listener to send focus events to
     */
    private OnFocusChangeListener onFocusChangeListener;

    /**
     * Pin entered listener used as a callback for when all digits have been entered
     */
    private OnPinEnteredListener onPinEnteredListener;

    /**
     * If set to false, will always draw accent color if type is CHARACTER or ALL
     * If set to true, will draw accent color only when focussed.
     */
    private boolean accentRequiresFocus;

    public PinEntry(Context context) {
        this(context, null);
    }

    public PinEntry(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public PinEntry(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);

        // Get style information
        TypedArray array = getContext().obtainStyledAttributes(attrs, R.styleable.PinEntryView);
        digits = array.getInt(R.styleable.PinEntryView_numDigits, 4);
        inputType = array.getInt(R.styleable.PinEntryView_pinInputType, InputType.TYPE_CLASS_NUMBER);
        accentType = array.getInt(R.styleable.PinEntryView_accentType, ACCENT_NONE);

        // Dimensions
        DisplayMetrics metrics = getResources().getDisplayMetrics();
        digitWidth = array.getDimensionPixelSize(R.styleable.PinEntryView_digitWidth,
                (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 50, metrics));
        digitHeight = array.getDimensionPixelSize(R.styleable.PinEntryView_digitHeight,
                (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 50, metrics));
        digitSpacing = array.getDimensionPixelSize(R.styleable.PinEntryView_digitSpacing,
                (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 20, metrics));
        //---------
        /*digitTextSize = array.getDimensionPixelSize(R.styleable.PinEntryView_digitTextSize,
                (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, 15, metrics));*/
        digitTextSize = array.getDimensionPixelSize(R.styleable.PinEntryView_digitTextSize,
                (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 50, metrics));
        //---------
        accentWidth = array.getDimensionPixelSize(R.styleable.PinEntryView_accentWidth,
                (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 3, metrics));
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            digitElevation = array.getDimensionPixelSize(R.styleable.PinEntryView_digitElevation, 0);
        }

        // Get theme to resolve defaults
        Resources.Theme theme = getContext().getTheme();

        // Background colour, default to android:windowBackground from theme
        TypedValue background = new TypedValue();
        theme.resolveAttribute(android.R.attr.windowBackground, background, true);
        digitBackground = array.getResourceId(R.styleable.PinEntryView_digitBackground,
                background.resourceId);

        // Text colour, default to android:textColorPrimary from theme
        TypedValue textColor = new TypedValue();
        theme.resolveAttribute(android.R.attr.textColorPrimary, textColor, true);
        digitTextColor = array.getColor(R.styleable.PinEntryView_digitTextColor,
                textColor.resourceId > 0 ? getResources().getColor(textColor.resourceId) :
                        textColor.data);

        // Accent colour, default to android:colorAccent from theme
        TypedValue accentColor = new TypedValue();
        theme.resolveAttribute(R.attr.colorAccent, accentColor, true);
        this.accentColor = array.getColor(R.styleable.PinEntryView_pinAccentColor,
                accentColor.resourceId > 0 ? getResources().getColor(accentColor.resourceId) :
                        accentColor.data);

        // Mask character
        String maskCharacter = array.getString(R.styleable.PinEntryView_mask);
        if (maskCharacter != null) {
            mask = maskCharacter;
        }

        // Accent shown, default to only when focused
        accentRequiresFocus = array.getBoolean(R.styleable.PinEntryView_accentRequiresFocus, true);

        //--- ABRAR : Custom
        applyDigitTextSize = array.getBoolean(R.styleable.PinEntryView_applyDigitTextSize, false);


        // Recycle the typed array
        array.recycle();

        // Add child views
        addViews();
    }

    @Override
    public boolean shouldDelayChildPressedState() {
        return false;
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        // Calculate the size of the view
        int width = (digitWidth * digits) + (digitSpacing * (digits - 1));
        setMeasuredDimension(
                width + getPaddingLeft() + getPaddingRight() + (digitElevation * 2),
                digitHeight + getPaddingTop() + getPaddingBottom() + (digitElevation * 2));

        int height = MeasureSpec.makeMeasureSpec(getMeasuredHeight(), MeasureSpec.EXACTLY);

        // Measure children
        for (int i = 0; i < getChildCount(); i++) {
            getChildAt(i).measure(width, height);
        }
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        // Position the text views
        for (int i = 0; i < digits; i++) {
            View child = getChildAt(i);
            int left = i * digitWidth + (i > 0 ? i * digitSpacing : 0);
            child.layout(
                    left + getPaddingLeft() + digitElevation,
                    getPaddingTop() + (digitElevation / 2),
                    left + getPaddingLeft() + digitElevation + digitWidth,
                    getPaddingTop() + (digitElevation / 2) + digitHeight);
        }

        // Add the edit text as a 1px wide view to allow it to focus
        getChildAt(digits).layout(0, 0, 1, getMeasuredHeight());
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            // Make sure this view is focused
            editText.requestFocus();

            // Show keyboard
            InputMethodManager inputMethodManager = (InputMethodManager) getContext()
                    .getSystemService(Context.INPUT_METHOD_SERVICE);

            inputMethodManager.showSoftInput(editText, 0);
            return true;
        }
        return super.onTouchEvent(event);
    }

    @Override
    protected Parcelable onSaveInstanceState() {
        Parcelable parcelable = super.onSaveInstanceState();
        SavedState savedState = new SavedState(parcelable);
        savedState.editTextValue = editText.getText().toString();
        return savedState;
    }

    @Override
    protected void onRestoreInstanceState(Parcelable state) {
        SavedState savedState = (SavedState) state;
        super.onRestoreInstanceState(savedState.getSuperState());
        editText.setText(savedState.editTextValue);
        try {
            editText.setSelection(savedState.editTextValue.length());
        } catch (IndexOutOfBoundsException e) {
            e.printStackTrace();
        }
    }

    @Override
    public OnFocusChangeListener getOnFocusChangeListener() {
        return onFocusChangeListener;
    }

    @Override
    public void setOnFocusChangeListener(OnFocusChangeListener l) {
        onFocusChangeListener = l;
    }

    /**
     * Add a TextWatcher to the EditText
     *
     * @param watcher
     */
    public void addTextChangedListener(TextWatcher watcher) {
        editText.addTextChangedListener(watcher);
    }

    /**
     * Remove a TextWatcher from the EditText
     *
     * @param watcher
     */
    public void removeTextChangedListener(TextWatcher watcher) {
        editText.removeTextChangedListener(watcher);
    }

    /**
     * Get current pin entered listener
     *
     * @return
     */
    public OnPinEnteredListener getOnPinEnteredListener() {
        return onPinEnteredListener;
    }

    /**
     * Set pin entered listener
     *
     * @param onPinEnteredListener
     */
    public void setOnPinEnteredListener(OnPinEnteredListener onPinEnteredListener) {
        this.onPinEnteredListener = onPinEnteredListener;
    }

    /**
     * Get the {@link Editable} from the EditText
     *
     * @return
     */
    public Editable getText() {
        return editText.getText();
    }

    /**
     * Set text to the EditText
     *
     * @param text
     */
    public void setText(CharSequence text) {
        if (text.length() > digits) {
            text = text.subSequence(0, digits);
        }
        editText.setText(text);
    }

    /**
     * Clear pin input
     */
    public void clearText() {
        editText.setText("");
    }

    /**
     * Create views and add them to the view group
     */
    private void addViews() {
        // Add a digit view for each digit
        for (int i = 0; i < digits; i++) {
            DigitView digitView = new DigitView(getContext());
            digitView.setWidth(digitWidth);
            digitView.setHeight(digitHeight);
            digitView.setGravity(Gravity.CENTER);
            digitView.setBackgroundResource(digitBackground);
            digitView.setTextColor(digitTextColor);
            //--------------
            //-- EDITED BY ABRAR : CUSTOME : START
            if (applyDigitTextSize == true) {
                digitView.setTextSize(digitTextSize);
                int paddingTop = (int) getContext().getResources().getDimension(R.dimen.pinenteryview_font_padding_top);
                digitView.setPadding(0,
                        paddingTop,
                        0, 0);
            } else {
                digitView.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 35);
            }

            //-----------
            digitView.setGravity(Gravity.CENTER);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                digitView.setElevation(digitElevation);
            }
            addView(digitView);
        }

        // Add an "invisible" edit text to handle input
        editText = new EditText(getContext());
        editText.setBackgroundColor(getResources().getColor(android.R.color.transparent));
        editText.setTextColor(getResources().getColor(android.R.color.transparent));
        editText.setGravity(Gravity.CENTER);

        editText.setCursorVisible(false);
        editText.setFilters(new InputFilter[]{new InputFilter.LengthFilter(digits)});
        editText.setInputType(inputType);
        editText.setImeOptions(EditorInfo.IME_FLAG_NO_EXTRACT_UI);
        editText.setOnFocusChangeListener(new OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                // Update the selected state of the views
                int length = editText.getText().length();
                for (int i = 0; i < digits; i++) {
                    getChildAt(i).setSelected(hasFocus && (accentType == ACCENT_ALL ||
                            (accentType == ACCENT_CHARACTER && (i == length ||
                                    (i == digits - 1 && length == digits)))));
                }

                // Make sure the cursor is at the end
                try {
                    editText.setSelection(length);
                } catch (IndexOutOfBoundsException e) {
                    e.printStackTrace();
                }

                // Provide focus change events to any listener
                if (onFocusChangeListener != null) {
                    onFocusChangeListener.onFocusChange(PinEntry.this, hasFocus);
                }
            }
        });
        editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                previousLength = s.length();
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                int length = s.length();

                for (int i = 0; i < digits; i++) {
                    if (s.length() > i) {
                        String mask = PinEntry.this.mask == null || PinEntry.this.mask.length() == 0 ?
                                String.valueOf(s.charAt(i)) : PinEntry.this.mask;
                        ((TextView) getChildAt(i)).setText(mask);
                    } else {
                        ((TextView) getChildAt(i)).setText("");
                    }
                    if (editText.hasFocus()) {
                        getChildAt(i).setSelected(accentType == ACCENT_ALL ||
                                (accentType == ACCENT_CHARACTER && (i == length ||
                                        (i == digits - 1 && length == digits))));
                    }
                }

                if (length == digits && onPinEnteredListener != null) {
                    onPinEnteredListener.onPinEntered(s.toString());
                } else if (length < 4) {
                    onPinEnteredListener.onPinEntered(s.toString());
                }
            }
        });
        addView(editText);
    }

    /**
     * Save state of the view
     */
    static class SavedState extends BaseSavedState {

        public static final Creator<SavedState> CREATOR =
                new Creator<SavedState>() {
                    @Override
                    public SavedState createFromParcel(Parcel in) {
                        return new SavedState(in);
                    }

                    @Override
                    public SavedState[] newArray(int size) {
                        return new SavedState[size];
                    }
                };
        String editTextValue;

        public SavedState(Parcelable superState) {
            super(superState);
        }

        private SavedState(Parcel source) {
            super(source);
            editTextValue = source.readString();
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            super.writeToParcel(dest, flags);
            dest.writeString(editTextValue);
        }

    }

    /**
     * Custom text view that adds a coloured accent when selected
     */
    private class DigitView extends TextView {

        /**
         * Paint used to draw accent
         */
        private Paint paint;

        public DigitView(Context context) {
            this(context, null);
        }

        public DigitView(Context context, AttributeSet attrs) {
            this(context, attrs, 0);
        }

        public DigitView(Context context, AttributeSet attrs, int defStyleAttr) {
            super(context, attrs, defStyleAttr);

            // Setup paint to keep onDraw as lean as possible
            paint = new Paint();
            paint.setStyle(Paint.Style.STROKE);
            paint.setColor(accentColor);
            paint.setStrokeWidth(10);
        }

        @Override
        protected void onDraw(Canvas canvas) {
            super.onDraw(canvas);

            // If selected draw the accent
            if (isSelected() || !accentRequiresFocus) {
                //to draw focus text border
                canvas.drawLine(0,getHeight(), getWidth(),getHeight() ,  paint);
            }
        }

    }

    public interface OnPinEnteredListener {
        void onPinEntered(String pin);
    }

}

