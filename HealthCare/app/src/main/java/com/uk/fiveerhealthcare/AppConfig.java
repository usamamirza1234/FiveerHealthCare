package com.uk.fiveerhealthcare;

import android.app.Activity;
import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.graphics.Typeface;
import android.os.Build;
import android.util.DisplayMetrics;
import android.util.Log;
import android.util.TypedValue;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;

import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONObject;

import java.nio.charset.StandardCharsets;
import java.text.NumberFormat;
import java.util.Iterator;
import java.util.Random;

import com.uk.fiveerhealthcare.Utils.AppConstt;
import com.uk.fiveerhealthcare.Utils.CustomAlertConfirmationInterface;
import com.uk.fiveerhealthcare.Utils.CustomAlertDialog;
import com.uk.fiveerhealthcare.Utils.IWebCallback;
import com.uk.fiveerhealthcare.Utils.IWebIndexedCallback;
import com.uk.fiveerhealthcare.Utils.IWebPaginationCallback;
import com.uk.fiveerhealthcare.Utils.RModel_Error;
import com.uk.fiveerhealthcare.Utils.RModel_Message;
import com.uk.fiveerhealthcare.Utils.RModel_onFailureError;


public class AppConfig {
    private static com.uk.fiveerhealthcare.AppConfig ourInstance;// = new AppConfig(null);
    public CustomAlertDialog customAlertDialog;
    //endregion
    public Gson gson;
    public String mRole;
    public byte mStateApp;
    public int scrnWidth, scrnHeight;
    //Custom Font Type Face
    public Typeface tfAppDefault;
    public DModelUser mUser;

    public boolean isAppRunnig, isCommingFromSplash, isSwitchingLang, isEnglishMode, shouldSkipSplash, isComingFromForgotPassword;
    public int marginToast;
    public int nFCMCounter;
    public String urlWebview;
    public String currentAppVersion;
    public String mLanguage;

    //General golbal data for the App
    private Context mContext;
    private SharedPreferences sharedPref;
    private SharedPreferences.Editor editor;

    private AppConfig(Context _mContext) {
        if (_mContext != null) {

            this.mContext = _mContext;
            this.scrnWidth = 0;
            this.scrnHeight = 0;
            this.tfAppDefault = Typeface.DEFAULT;


            this.marginToast = (int) (TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 100
                    , mContext.getResources().getDisplayMetrics()));


            Random random = new Random();
            this.nFCMCounter = random.nextInt(9999 - 1000) + 1000;

            initUserSessionData();
        }
    }

    public static void initInstance(Context _mContext) {
        if (ourInstance == null) {
            // Create the instance
            ourInstance = new com.uk.fiveerhealthcare.AppConfig(_mContext);
        }
    }

    public static com.uk.fiveerhealthcare.AppConfig getInstance() {
        return ourInstance;
    }


    //region Parsing Web Error Messages
    public void parsErrorMessage(final IWebCallback iWebCallback, byte[] responseBody) {
        try {
            Gson gson = new Gson();
            String strResponse = new String(responseBody, StandardCharsets.UTF_8);
            RModel_Error responseObjectLocal = gson.fromJson(strResponse, RModel_Error.class);

            StringBuilder str = new StringBuilder();

            // Traversing the ArrayList
            for (String eachstring : responseObjectLocal.getErrors()) {

                // Each element in ArrayList is appended
                // followed by comma
                str.append(eachstring).append(",");
            }

            // StringBuffer to String conversion
            String commaseparatedlist = str.toString();

            // By following condition you can remove the last
            // comma
            if (commaseparatedlist.length() > 0)
                commaseparatedlist
                        = commaseparatedlist.substring(
                        0, commaseparatedlist.length() - 1);

            System.out.println(commaseparatedlist);

//            for (int i=0;i<responseObjectLocal.getErrors().size();i++)
//            {
//
//            }

            iWebCallback.onWebResult(false, commaseparatedlist);
        } catch (Exception ex) {
            ex.printStackTrace();
            iWebCallback.onWebException(ex);
        }
    }

    private void parseJson(JSONObject data) {
        Log.d("paserdJSON", "JSONObject: e " + data);
        if (data != null) {
            Iterator<String> it = data.keys();
            while (it.hasNext()) {
                String key = it.next();
                try {
                    if (data.get(key) instanceof JSONArray) {
                        JSONArray arry = data.getJSONArray(key);
                        int size = arry.length();
                        for (int i = 0; i < size; i++) {
                            parseJson(arry.getJSONObject(i));
                        }
                    } else if (data.get(key) instanceof JSONObject) {
                        parseJson(data.getJSONObject(key));
                    } else {
                        Log.d("paserdJSON", key + ":" + data.getString(key));
                        System.out.println(key + ":" + data.getString(key));
                    }
                } catch (Throwable e) {
                    Log.d("paserdJSON", "Throwable: e " + e.getMessage());
                    try {
                        Log.d("paserdJSON", "Throwable: e " + key + ":" + data.getString(key));
                        System.out.println(key + ":" + data.getString(key));
                    } catch (Exception ee) {
                        Log.d("paserdJSON", "Throwable: ee " + ee.getMessage());
                    }
                    e.printStackTrace();

                }
            }
        }
    }

    public void parsErrorMessageOnFailure(final IWebCallback iWebCallback, byte[] responseBody) {
        try {
            Gson gson = new Gson();
            String strResponse = new String(responseBody, StandardCharsets.UTF_8);
            RModel_onFailureError responseObjectLocal = gson.fromJson(strResponse, RModel_onFailureError.class);


            Log.d("paserdJSON", "JSONObject: responseObjectLocal.getErrors() " + responseObjectLocal.getErrors());
//            parseJson(responseObjectLocal.getErrors());


            iWebCallback.onWebResult(false, responseObjectLocal.getErrors().toString());
        } catch (Exception ex) {
            ex.printStackTrace();
            iWebCallback.onWebException(ex);
        }
    }

//    public void performLangCheck(Window _window) {
//        if (_window != null && Build.VERSION.SDK_INT >= Build.VERSION_CODES.N)
//            _window.getDecorView().setLayoutDirection(TextUtilsCompat
//                    .getLayoutDirectionFromLocale(Locale.getDefault()));
//    }
    //endregion

    public void parsErrorMessage(final IWebIndexedCallback iWebIndexedCallback, byte[] responseBody, int _index) {
        try {
            Gson gson = new Gson();
            String strResponse = new String(responseBody, StandardCharsets.UTF_8);
            RModel_Message responseObjectLocal = gson.fromJson(strResponse, RModel_Message.class);
            iWebIndexedCallback.onWebResult(false, responseObjectLocal.getMessage(), _index);
        } catch (Exception ex) {
            ex.printStackTrace();
            iWebIndexedCallback.onWebException(ex, _index);
        }
    }

    public void parsErrorMessage(final IWebPaginationCallback iWebPaginationCallback, byte[] responseBody,
                                 final int _index, final int _currIndex) {
        try {
            Gson gson = new Gson();
            String strResponse = new String(responseBody, StandardCharsets.UTF_8);
            RModel_Message responseObjectLocal = gson.fromJson(strResponse, RModel_Message.class);
            if (_index == _currIndex)
                iWebPaginationCallback.onWebInitialResult(false, responseObjectLocal.getMessage());
            else
                iWebPaginationCallback.onWebSuccessiveResult(false, false, responseObjectLocal.getMessage());
        } catch (Exception ex) {
            ex.printStackTrace();
            if (_index == _currIndex)
                iWebPaginationCallback.onWebInitialException(ex);
            else
                iWebPaginationCallback.onWebSuccessiveException(ex);
        }
    }

    public void closeKeyboard(Activity activity) {
        InputMethodManager imm = (InputMethodManager) activity.getSystemService(Activity.INPUT_METHOD_SERVICE);
        //Find the currently focused view, so we can grab the correct window User_AccessToken from it.
        View view = activity.getCurrentFocus();
        //If no view currently has focus, create a new one, just so we can grab a window User_AccessToken from it
        if (view == null) {
            view = new View(activity);
        }
        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }

    public void showErrorMessage(Context context, String _errorMsg) {
        customAlertDialog = new CustomAlertDialog(context, "", _errorMsg, "Ok", "", false, new CustomAlertConfirmationInterface() {
            @Override
            public void callConfirmationDialogPositive() {
                customAlertDialog.dismiss();
            }

            @Override
            public void callConfirmationDialogNegative() {
                customAlertDialog.dismiss();
            }
        });
        customAlertDialog.show();
    }
    //endregion


    private void initUserSessionData() {
        gson = new Gson();
        this.isAppRunnig = false;
        this.isCommingFromSplash = false;
        this.isSwitchingLang = false;
        this.isComingFromForgotPassword = false;

        this.shouldSkipSplash = false;


        this.sharedPref = mContext.getSharedPreferences(AppConstt.PREF_FILE_NAME, Context.MODE_PRIVATE);
        this.editor = sharedPref.edit();

        this.mLanguage = "";
        this.isEnglishMode = true;
        this.mRole = "";
        this.mUser = new DModelUser();

        loadUserProfile();


    }

    //region Regulate user custom screen settings
    public Context regulateDisplayScale(final Context baseContext) {
        Context newContext;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {

            DisplayMetrics displayMetrics = baseContext.getResources().getDisplayMetrics();
            Configuration configuration = baseContext.getResources().getConfiguration();
            String deviceInfo = Build.MANUFACTURER;
            float scaledDensity = ((float) displayMetrics.densityDpi / (float) DisplayMetrics.DENSITY_DEFAULT);

            if (deviceInfo.equalsIgnoreCase(AppConstt.DeviceManufacturer.Samsung) ||
                    deviceInfo.equalsIgnoreCase(AppConstt.DeviceManufacturer.Huawei)) {

                //Logic based on Assets/phone_data.txt file
                if (scaledDensity < 2.5) {
                    configuration.densityDpi = 320;//DisplayMetrics.DENSITY_XHIGH
                } else if (scaledDensity < 3.5) {
                    configuration.densityDpi = 480;
                    if (scaledDensity > 2.9 && scaledDensity < 3.0) {
                        //HuaweiNexus_6P One & Only Special Case
                        configuration.densityDpi = 640;
                    }
                } else if (scaledDensity <= 4.5) {
                    configuration.densityDpi = 640;
                } else {
                    //Future devices (D.N.E as per Dec 2019)
                    configuration.densityDpi = 800;
                }
                newContext = baseContext;//baseContext.createConfigurationContext(configuration);

            } else {
                //Xiaomi and all other unknown devices
                if (displayMetrics.densityDpi != DisplayMetrics.DENSITY_DEVICE_STABLE) {
                    // Current density is different from Default Density. Override it
                    displayMetrics.densityDpi = DisplayMetrics.DENSITY_DEVICE_STABLE;
                    configuration.densityDpi = DisplayMetrics.DENSITY_DEVICE_STABLE;
                    newContext = baseContext;//baseContext.createConfigurationContext(configuration);
                } else {
                    // Same density. Just use same context
                    newContext = baseContext;
                }
            }
        } else {
            // Old API. Screen zoom not supported
            newContext = baseContext;
        }

        return newContext;
    }
    //endregion

    public Context regulateDisplayScaleObselete(final Context baseContext) {
        Context newContext;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            DisplayMetrics displayMetrics = baseContext.getResources().getDisplayMetrics();
            Configuration configuration = baseContext.getResources().getConfiguration();
            if (displayMetrics.densityDpi != DisplayMetrics.DENSITY_DEVICE_STABLE) {
                // Current density is different from Default Density. Override it
                displayMetrics.densityDpi = DisplayMetrics.DENSITY_DEVICE_STABLE;
                configuration.densityDpi = DisplayMetrics.DENSITY_DEVICE_STABLE;
                newContext = baseContext;//baseContext.createConfigurationContext(configuration);
            } else {
                // Same density. Just use same context
                newContext = baseContext;
            }
        } else {
            // Old API. Screen zoom not supported
            newContext = baseContext;
        }
        return newContext;
    }

    public void regulateFontScale(final Configuration configuration, final Context baseContext) {
        //The maximum scale/size of font allowed in this app, if user has changed that from his/her phone settings
        final float MAX_FONTSCALE = 1.0f;
        if (configuration.fontScale != MAX_FONTSCALE) {
            configuration.fontScale = MAX_FONTSCALE;
            DisplayMetrics metrics = baseContext.getResources().getDisplayMetrics();
            WindowManager wm = (WindowManager) baseContext.getSystemService(Context.WINDOW_SERVICE);
            wm.getDefaultDisplay().getMetrics(metrics);
            metrics.scaledDensity = configuration.fontScale * metrics.density;
//            Log.d("regulateDisplayScale", "fontScale: " + configuration.fontScale);
//            Log.d("regulateDisplayScale", "density: " + metrics.density);
//            Log.d("regulateDisplayScale", "scaledDensity: " + metrics.scaledDensity);
            baseContext.getResources().updateConfiguration(configuration, metrics);
        }
    }

    public void regulateFontScaleObselete(final Configuration configuration, final Context baseContext) {
        //The maximum scale/size of font allowed in this app, if user has changed that from his/her phone settings
        final float MAX_FONTSCALE = 1.0f;
        if (configuration.fontScale != MAX_FONTSCALE) {
            configuration.fontScale = MAX_FONTSCALE;
            DisplayMetrics metrics = baseContext.getResources().getDisplayMetrics();
            WindowManager wm = (WindowManager) baseContext.getSystemService(Context.WINDOW_SERVICE);
            wm.getDefaultDisplay().getMetrics(metrics);
            metrics.scaledDensity = configuration.fontScale * metrics.density;
            baseContext.getResources().updateConfiguration(configuration, metrics);
        }
    }

    //region Units conversions
    public String numberToCurrLang(String strNumber) {
        StringBuilder builder = new StringBuilder();

        if (isEnglishMode) {
            builder.append(strNumber);
        } else {
            char[] arabicChars = {'٠', '١', '٢', '٣', '٤', '٥', '٦', '٧', '٨', '٩'};
//        char[] arabicChars = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9'};
            for (int i = 0; i < strNumber.length(); i++) {
                if (Character.isDigit(strNumber.charAt(i))) {
                    builder.append(arabicChars[(int) (strNumber.charAt(i)) - 48]);
                } else {
                    builder.append(strNumber.charAt(i));
                }
            }
        }

        return builder.toString();
    }

    public String priceToCurrLang(double _price, boolean isUnitRequired) {
        StringBuilder builder = new StringBuilder();
        if (isEnglishMode) {
            builder.append((AppConstt.UNIT_CURRENCY_EN + " "));
        } else {
            builder.append((AppConstt.UNIT_CURRENCY_AR + " "));
        }

//        builder.append(numberToCurrLang(String.format("%.2f", _price)));//Not working for arabic etc where , is being used
        NumberFormat formatter = NumberFormat.getNumberInstance();
        formatter.setMinimumFractionDigits(0);
        formatter.setMaximumFractionDigits(2);
        builder.append(numberToCurrLang("" + formatter.format(_price)));
//        builder.append(numberToCurrLang("" +
//                new BigDecimal(_price).setScale(2, RoundingMode.HALF_UP).doubleValue()));

        return builder.toString();
    }
    //endregion

    //region Language Settings

    public String distanceToCurrLang(double _distance) {
        StringBuilder builder = new StringBuilder();


//        builder.append(numberToCurrLang(String.format("%.2f", _price)));//Not working for arabic etc where , is being used
        NumberFormat formatter = NumberFormat.getNumberInstance();
        formatter.setMinimumFractionDigits(0);
        formatter.setMaximumFractionDigits(1);
        builder.append(numberToCurrLang("" + formatter.format(_distance)));
//        builder.append(numberToCurrLang("" +
//                new BigDecimal(_distance).setScale(2, RoundingMode.HALF_UP).doubleValue()));

        if (isEnglishMode) {
            builder.append((" " + AppConstt.UNIT_DISTANCE_EN));
        } else {
            builder.append((" " + AppConstt.UNIT_DISTANCE_AR));
        }
        return builder.toString();
    }

    public String durationToCurrLang(double _distance) {
        StringBuilder builder = new StringBuilder();


//        builder.append(numberToCurrLang(String.format("%.2f", _price)));//Not working for arabic etc where , is being used
        NumberFormat formatter = NumberFormat.getNumberInstance();
        formatter.setMinimumFractionDigits(0);
        formatter.setMaximumFractionDigits(1);
        builder.append(numberToCurrLang("" + formatter.format(_distance)));
//        builder.append(numberToCurrLang("" +
//                new BigDecimal(_distance).setScale(2, RoundingMode.HALF_UP).doubleValue()));

        if (isEnglishMode) {
            builder.append((" " + AppConstt.UNIT_DURATION_EN));
        } else {
            builder.append((" " + AppConstt.UNIT_DURATION_AR));
        }
        return builder.toString();
    }

    //region Profile
    public void loadUserProfile() {
        mUser.setUser_Id(sharedPref.getInt("key_user_id", 0));
        mUser.setName(sharedPref.getString("key_user_name", ""));
        mUser.setPhone(sharedPref.getString("key_user_phone", ""));
        mUser.setEmail(sharedPref.getString("key_user_email", ""));
        mUser.setGender(sharedPref.getString("key_user_gender", ""));
        mUser.setDOB(sharedPref.getString("key_user_dob", ""));
        mUser.setIqama_Id(sharedPref.getString("key_user_iqama_id", ""));
        mUser.setIqama_Expiry(sharedPref.getString("key_user_iqama_expiry", ""));
        mUser.setIqama_Image(sharedPref.getString("key_user_iqama_image", ""));
        mUser.setAddress(sharedPref.getString("key_user_addr", ""));
        mUser.setImage(sharedPref.getString("key_user_image", ""));
        mUser.setRole(sharedPref.getString("key_user_role", ""));
        mUser.setStatus(sharedPref.getInt("key_user_status", 0));
        mUser.setIs_Under_Review(sharedPref.getInt("key_user_uner_review", 0));
        mUser.setAdditional_Notes(sharedPref.getString("key_user_additional_notes", ""));
        mUser.setActive(sharedPref.getInt("key_user_active", 0));
        mUser.setPushOn(sharedPref.getBoolean("key_user_ispush", true));
        mUser.setLoggedIn(sharedPref.getBoolean("key_user_isloggedin", false));
        mUser.setAuthorization(sharedPref.getString("key_user_auth", ""));
        mUser.setPassword_Token(sharedPref.getString("key_user_password_token", ""));
        mUser.setCreatedAt(sharedPref.getString("key_user_created_at", ""));
    }

    public void saveUserProfile() {
        try {
            byte[] ptext2 = mUser.Authorization.getBytes();
            mUser.Authorization = new String(ptext2, StandardCharsets.UTF_8);

        } catch (Exception e) {
            e.printStackTrace();
        }

        editor.putInt("key_user_id", mUser.getUser_Id());
        editor.putString("key_user_name", mUser.getName());
        editor.putString("key_user_phone", mUser.getPhone());
        editor.putString("key_user_email", mUser.getEmail());
        editor.putString("key_user_gender", mUser.getGender());
        editor.putString("key_user_dob", mUser.getDOB());
        editor.putString("key_user_iqama_id", mUser.getIqama_Id());
        editor.putString("key_user_iqama_expiry", mUser.getIqama_Expiry());
        editor.putString("key_user_iqama_image", mUser.getIqama_Image());
        editor.putString("key_user_addr", mUser.getAddress());
        editor.putString("key_user_image", mUser.getImage());
        editor.putString("key_user_role", mUser.getRole());
        editor.putInt("key_user_status", mUser.getStatus());
        editor.putInt("key_user_uner_review", mUser.getIs_Under_Review());
        editor.putString("key_user_additional_notes", mUser.getAdditional_Notes());
        editor.putInt("key_user_active", mUser.getActive());
        editor.putBoolean("key_user_ispush", mUser.isPushOn());
        editor.putBoolean("key_user_isloggedin", mUser.isLoggedIn());
        editor.putString("key_user_auth", mUser.getAuthorization());
        editor.putString("key_user_password_token", mUser.getPassword_Token());
        editor.putString("key_user_created_at", mUser.getCreatedAt());

        editor.commit();
    }

    public void deleteUserData() {
        //retain FCM token only
        String tmpToken = loadFCMDeviceToken();
        //retain Language as well
        String appLangTemp = loadDefLanguage();

        editor.clear();
        editor.commit();

        initUserSessionData();

        if (mContext != null) {
            // Clear all notifications
            NotificationManager notificationManager = (NotificationManager) mContext.getSystemService(Context.NOTIFICATION_SERVICE);
            notificationManager.cancelAll();
        }

        saveFCMDeviceToken(tmpToken);
        saveDefLanguage(appLangTemp);
    }
    //endregion





    public void navtoLogin() {

        if (mUser.isLoggedIn()) {
            deleteUserData();

            try {
                Intent intent = new Intent(com.uk.fiveerhealthcare.MyApplication.mContext,
                        IntroActivity.class);

                // set the new task and clear flags
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                com.uk.fiveerhealthcare.MyApplication.mContext.startActivity(intent);

            } catch (Exception e) {
                e.printStackTrace();
            }
        }

    }

    //region Base URLs
    public String getBaseUrlApi() {
        if (!BuildConfig.DEBUG)
            return AppConstt.ServerUrl.REL_URL_API;
        else
            return AppConstt.ServerUrl.DEB_URL_API;
    }
    //endregion

    //region Api's Encryption

    public String getBaseUrlImage() {
        if (!BuildConfig.DEBUG)
            return AppConstt.ServerUrl.REL_URL_IMG;
        else
            return AppConstt.ServerUrl.DEB_URL_IMG;
    }

    public String getBaseUrlThumbs() {
        if (!BuildConfig.DEBUG)
            return AppConstt.ServerUrl.REL_URL_THUMBS;
        else
            return AppConstt.ServerUrl.DEB_URL_THUMBS;
    }

    public String getBaseUrlSocket() {
        if (!BuildConfig.DEBUG)
            return AppConstt.ServerUrl.REL_URL_SOCKET;
        else
            return AppConstt.ServerUrl.DEB_URL_SOCKET;
    }

    public void saveIsEnglish(boolean _isEnglishMode) {
        isEnglishMode = _isEnglishMode;
        editor.putBoolean("key_is_eng", _isEnglishMode);
        editor.commit();
    }


    public void saveDefLanguage(String lang) {
        editor.putString("app_deflt_lang", lang);
        editor.commit();
    }

    public String loadDefLanguage() {
        mLanguage = sharedPref.getString("app_deflt_lang", "en");
        isEnglishMode = !mLanguage.equalsIgnoreCase("ur");
        return mLanguage;
    }

    public String getNetworkErrorMessage() {
        if (!isEnglishMode)
            return AppConstt.NetworkMsg.ERROR_NETWORK_AR;
        else
            return AppConstt.NetworkMsg.ERROR_NETWORK_EN;
    }

    public String getNetworkExceptionMessage(String _msg) {
        if (!BuildConfig.DEBUG) {
            if (!isEnglishMode)
                return AppConstt.NetworkMsg.EXCEPTION_NETWORK_AR;
            else
                return AppConstt.NetworkMsg.EXCEPTION_NETWORK_EN;
        } else {
            return _msg;
        }
    }

    //region Notifications Archieving
    public void saveNotifications(String strJsonOrders, String _lang) {
        editor.putString("key_Notifications" + "_" + _lang, strJsonOrders);
        editor.commit();
    }

    public String loadNotifications(String _lang) {
        return sharedPref.getString("key_Notifications" + "_" + _lang, null);
    }




    //endregion

    //region FCM Token Archieving
    public void saveFCMDeviceToken(String _token) {
        editor.putString("key_fcm_token", _token);
        editor.commit();
    }

    public String loadFCMDeviceToken() {
        return sharedPref.getString("key_fcm_token", "");
    }
    //endregion


    public boolean loadIsRevised() {
        return sharedPref.getBoolean("key_is_revised_v1", false);
    }

    public void saveIsRevised(boolean _isRevised) {
        editor.putBoolean("key_is_revised_v1", _isRevised);
        editor.commit();
    }


    public Boolean getNotificationsSettings() {
        return sharedPref.getBoolean("key_notification_settings", true);
    }

    public void setNotificationsSettings(boolean _notification) {
        editor.putBoolean("key_notification_settings", _notification);
        editor.commit();

    }






}
