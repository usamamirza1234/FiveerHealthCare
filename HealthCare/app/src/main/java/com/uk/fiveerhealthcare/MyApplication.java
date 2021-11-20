package com.uk.fiveerhealthcare;

import android.app.Application;
import android.content.Context;
import android.content.res.Configuration;
import android.graphics.Typeface;
import android.os.Build;
import android.util.Log;
import android.util.LongSparseArray;

import androidx.multidex.MultiDex;

import com.google.firebase.analytics.FirebaseAnalytics;
import com.uk.fiveerhealthcare.Utils.AppConstt;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;


public class MyApplication extends Application {


    private static final String THEME_DEFAULT_FONT = "serif";//Must be same as used in styles.xml
    // Constants found in the Android documentation
    // http://developer.android.com/reference/android/widget/TextView.html#attr_android:typeface
    private static final int normal_idx = 0;
    private static final int sans_idx = 1;
    private static final int serif_idx = 2;
    private static final int monospace_idx = 3;
    public static Context mContext;
    private static MyApplication applicationContext;
    // Defining sans as the normal (default) typeface.
    private String DEFAULT_NORMAL_BOLD_FONT_FILENAME = "";
    private String DEFAULT_NORMAL_BOLD_ITALIC_FONT_FILENAME = "";
    private String DEFAULT_NORMAL_ITALIC_FONT_FILENAME = "";
    private String DEFAULT_NORMAL_NORMAL_FONT_FILENAME = "";
    private String DEFAULT_SANS_BOLD_FONT_FILENAME = "";
    private String DEFAULT_SANS_BOLD_ITALIC_FONT_FILENAME = "";
    private String DEFAULT_SANS_ITALIC_FONT_FILENAME = "";
    private String DEFAULT_SANS_NORMAL_FONT_FILENAME = "";
    private String DEFAULT_SERIF_BOLD_FONT_FILENAME = "";
    private String DEFAULT_SERIF_BOLD_ITALIC_FONT_FILENAME = "";
    private String DEFAULT_SERIF_ITALIC_FONT_FILENAME = "";
    private String DEFAULT_SERIF_NORMAL_FONT_FILENAME = "";
    private String DEFAULT_MONOSPACE_BOLD_FONT_FILENAME = "";
    private String DEFAULT_MONOSPACE_BOLD_ITALIC_FONT_FILENAME = "";
    private String DEFAULT_MONOSPACE_ITALIC_FONT_FILENAME = "";
    private String DEFAULT_MONOSPACE_NORMAL_FONT_FILENAME = "";

    //    private Tracker mTracker;
//
//    synchronized public Tracker getDefaultTracker() {
//
//        if (mTracker == null) {
//
//            GoogleAnalytics analytics = GoogleAnalytics.getInstance(this);
//
//            // To enable debug logging use: adb shell setprop log.tag.GAv4 DEBUG
//
//            mTracker = analytics.newTracker(R.xml.app_tracker);
//
//        }
//
//        return mTracker;
//
//    }
    private FirebaseAnalytics mFirebaseAnalytics;

    public static MyApplication getInstance() {
        return applicationContext;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        mContext = this;
        applicationContext = this;

        AppConfig.initInstance(mContext);
//        if (AppConfig.getInstance().loadFCMDeviceToken().equals(""))
//        {
//            FirebaseMessaging.getInstance().getToken().addOnCompleteListener(new OnCompleteListener<String>() {
//                @Override
//                public void onComplete(@NonNull Task<String> task) {
//                    if (task.isSuccessful())
//                    {
//                        String token=task.getResult();
//
//                        AppConfig.getInstance().saveFCMDeviceToken(token);
//
//                    }
//
//                }
//            });
//
//        }
//        mFirebaseAnalytics = FirebaseAnalytics.getInstance(this);
        setAppLanguage(AppConfig.getInstance().loadDefLanguage());
    }

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        MultiDex.install(this);
    }

    public void setAppLanguage(String _lang) {
        Locale locale = new Locale(_lang);
        Locale.setDefault(locale);
        Configuration config = new Configuration();
        config.locale = locale;
        getBaseContext().getResources().updateConfiguration(config,
                getBaseContext().getResources().getDisplayMetrics());




        if (_lang.equalsIgnoreCase(AppConstt.AppLang.LANG_UR)) {
            AppConfig.getInstance().saveDefLanguage(AppConstt.AppLang.LANG_UR);
            AppConfig.getInstance().isEnglishMode = false;
        } else {
            AppConfig.getInstance().saveDefLanguage(AppConstt.AppLang.LANG_EN);
            AppConfig.getInstance().isEnglishMode = true;
        }
        setLanguageSpecificFonts(AppConfig.getInstance().isEnglishMode);

    }

    private void setTypeFaceDefaults(Typeface normal, Typeface bold, Typeface italic, Typeface boldItalic, int typefaceIndex) throws NoSuchFieldException, IllegalAccessException {
        Field typeFacesField = Typeface.class.getDeclaredField("sTypefaceCache");
        typeFacesField.setAccessible(true);

        LongSparseArray<LongSparseArray<Typeface>> sTypefaceCacheLocal = new LongSparseArray<LongSparseArray<Typeface>>(3);
        typeFacesField.get(sTypefaceCacheLocal);

        LongSparseArray<Typeface> newValues = new LongSparseArray<Typeface>(4);
        newValues.put(Typeface.NORMAL, normal);
        newValues.put(Typeface.BOLD, bold);
        newValues.put(Typeface.ITALIC, italic);
        newValues.put(Typeface.BOLD_ITALIC, boldItalic);
        sTypefaceCacheLocal.put(typefaceIndex, newValues);

        typeFacesField.set(null, sTypefaceCacheLocal);
    }

    public void setLanguageSpecificFonts(boolean isEnglish) {
        if (!isEnglish) {
            //Arabic fonts
            DEFAULT_NORMAL_BOLD_FONT_FILENAME = "fonts/jameel_noori_nastaleeq_kasheeda.ttf";
            DEFAULT_NORMAL_BOLD_ITALIC_FONT_FILENAME = "fonts/jameel_noori_nastaleeq_kasheeda.ttf";
            DEFAULT_NORMAL_ITALIC_FONT_FILENAME = "fonts/jameel_noori_nastaleeq_kasheeda.ttf";
            DEFAULT_NORMAL_NORMAL_FONT_FILENAME = "fonts/jameel_noori_nastaleeq_kasheeda.ttf";


            DEFAULT_SANS_BOLD_FONT_FILENAME = "fonts/jameel_noori_nastaleeq_kasheeda.ttf";
            DEFAULT_SANS_BOLD_ITALIC_FONT_FILENAME = "fonts/jameel_noori_nastaleeq_kasheeda.ttf";
            DEFAULT_SANS_ITALIC_FONT_FILENAME = "fonts/jameel_noori_nastaleeq_kasheeda.ttf";
            DEFAULT_SANS_NORMAL_FONT_FILENAME = "fonts/jameel_noori_nastaleeq_kasheeda.ttf";


            DEFAULT_SERIF_BOLD_FONT_FILENAME = "fonts/jameel_noori_nastaleeq_kasheeda.ttf";
            DEFAULT_SERIF_BOLD_ITALIC_FONT_FILENAME = "fonts/jameel_noori_nastaleeq_kasheeda.ttf";
            DEFAULT_SERIF_ITALIC_FONT_FILENAME = "fonts/jameel_noori_nastaleeq_kasheeda.ttf";
            DEFAULT_SERIF_NORMAL_FONT_FILENAME = "fonts/jameel_noori_nastaleeq_kasheeda.ttf";


            DEFAULT_MONOSPACE_BOLD_FONT_FILENAME = "fonts/jameel_noori_nastaleeq_kasheeda.ttf";
            DEFAULT_MONOSPACE_BOLD_ITALIC_FONT_FILENAME = "fonts/jameel_noori_nastaleeq_kasheeda.ttf";
            DEFAULT_MONOSPACE_ITALIC_FONT_FILENAME = "fonts/jameel_noori_nastaleeq_kasheeda.ttf";
            DEFAULT_MONOSPACE_NORMAL_FONT_FILENAME = "fonts/jameel_noori_nastaleeq_kasheeda.ttf";
        } else {
            //English fonts
            DEFAULT_NORMAL_BOLD_FONT_FILENAME = "fonts/Montserrat_Regular.ttf";
            DEFAULT_NORMAL_BOLD_ITALIC_FONT_FILENAME = "fonts/Montserrat_Regular.ttf";
            DEFAULT_NORMAL_ITALIC_FONT_FILENAME = "fonts/Montserrat_Regular.ttf";
            DEFAULT_NORMAL_NORMAL_FONT_FILENAME = "fonts/Montserrat_Regular.ttf";


            DEFAULT_SANS_BOLD_FONT_FILENAME = "fonts/Montserrat_Regular.ttf";
            DEFAULT_SANS_BOLD_ITALIC_FONT_FILENAME = "fonts/Montserrat_Regular.ttf";
            DEFAULT_SANS_ITALIC_FONT_FILENAME = "fonts/Montserrat_Regular.ttf";
            DEFAULT_SANS_NORMAL_FONT_FILENAME = "fonts/Montserrat_Regular.ttf";


            DEFAULT_SERIF_BOLD_FONT_FILENAME = "fonts/Montserrat_Regular.ttf";
            DEFAULT_SERIF_BOLD_ITALIC_FONT_FILENAME = "fonts/Montserrat_Regular.ttf";
            DEFAULT_SERIF_ITALIC_FONT_FILENAME = "fonts/Montserrat_Regular.ttf";
            DEFAULT_SERIF_NORMAL_FONT_FILENAME = "fonts/Montserrat_Regular.ttf";


            DEFAULT_MONOSPACE_BOLD_FONT_FILENAME = "fonts/Montserrat_Regular.ttf";
            DEFAULT_MONOSPACE_BOLD_ITALIC_FONT_FILENAME = "fonts/Montserrat_Regular.ttf";
            DEFAULT_MONOSPACE_ITALIC_FONT_FILENAME = "fonts/Montserrat_Regular.ttf";
            DEFAULT_MONOSPACE_NORMAL_FONT_FILENAME = "fonts/Montserrat_Regular.ttf";
        }


        try {
//            setThemeDefaultFonts(THEME_DEFAULT_FONT);

            // The following code is only necessary if you are using the android:typeface attribute
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                setThemeDefaultFonts(THEME_DEFAULT_FONT);

            }
        } catch (NoSuchFieldException e) {
            // Field does not exist in this (version of the) class
            //logFontError(e);
        } catch (IllegalAccessException e) {
            // Access rights not set correctly on field, i.e. we made a programming error
            // logFontError(e);
        } catch (Throwable e) {
            // Must not crash app if there is a failure with overriding fonts!
            // logFontError(e);
        }

    }

    private void setThemeDefaultFonts(String defaultFontNameToOverride) throws NoSuchFieldException, IllegalAccessException {

        final Typeface normal = Typeface.createFromAsset(mContext.getAssets(), DEFAULT_NORMAL_NORMAL_FONT_FILENAME);
        final Typeface bold = Typeface.createFromAsset(mContext.getAssets(), DEFAULT_NORMAL_BOLD_FONT_FILENAME);
        final Typeface italic = Typeface.createFromAsset(mContext.getAssets(), DEFAULT_NORMAL_ITALIC_FONT_FILENAME);
        final Typeface boldItalic = Typeface.createFromAsset(mContext.getAssets(), DEFAULT_NORMAL_BOLD_ITALIC_FONT_FILENAME);


        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {

            Map<String, Typeface> normalFontMap = new HashMap<String, Typeface>();
            normalFontMap.put(defaultFontNameToOverride, normal);
            final Field staticField = Typeface.class.getDeclaredField("sSystemFontMap");
            staticField.setAccessible(true);
            staticField.set(null, normalFontMap);

        } else {
            Field defaultField = Typeface.class.getDeclaredField("DEFAULT");
            defaultField.setAccessible(true);
            defaultField.set(null, normal);

            Field defaultBoldField = Typeface.class.getDeclaredField("DEFAULT_BOLD");
            defaultBoldField.setAccessible(true);
            defaultBoldField.set(null, bold);

            Field sDefaults = Typeface.class.getDeclaredField("sDefaults");
            sDefaults.setAccessible(true);
            sDefaults.set(null, new Typeface[]{normal, bold, italic, boldItalic});

            final Typeface normal_sans = Typeface.createFromAsset(mContext.getAssets(), DEFAULT_SANS_NORMAL_FONT_FILENAME);
            Field sansSerifDefaultField = Typeface.class.getDeclaredField("SANS_SERIF");
            sansSerifDefaultField.setAccessible(true);
            sansSerifDefaultField.set(null, normal_sans);

            final Typeface normal_serif = Typeface.createFromAsset(mContext.getAssets(), DEFAULT_SERIF_NORMAL_FONT_FILENAME);
            Field serifDefaultField = Typeface.class.getDeclaredField("SERIF");
            serifDefaultField.setAccessible(true);
            serifDefaultField.set(null, normal_serif);

            final Typeface normal_monospace = Typeface.createFromAsset(mContext.getAssets(), DEFAULT_MONOSPACE_NORMAL_FONT_FILENAME);
            Field monospaceDefaultField = Typeface.class.getDeclaredField("MONOSPACE");
            monospaceDefaultField.setAccessible(true);
            monospaceDefaultField.set(null, normal_monospace);
        }

    }

    private void applyFonts() {
        try {
            setThemeDefaultFonts(THEME_DEFAULT_FONT);

            // The following code is only necessary if you are using the android:typeface attribute
//            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
//                setDefaultFontForTypeFaceSans();
//                setDefaultFontForTypeFaceSansSerif();
//                setDefaultFontForTypeFaceMonospace();
//            }
        } catch (NoSuchFieldException e) {
            // Field does not exist in this (version of the) class
            logFontError(e);
        } catch (IllegalAccessException e) {
            // Access rights not set correctly on field, i.e. we made a programming error
            logFontError(e);
        } catch (Throwable e) {
            // Must not crash app if there is a failure with overriding fonts!
            logFontError(e);
        }
    }

    private void setDefaultFontForTypeFaceSans() throws NoSuchFieldException, IllegalAccessException {
        final Typeface bold = Typeface.createFromAsset(mContext.getAssets(), DEFAULT_SANS_BOLD_FONT_FILENAME);
        final Typeface italic = Typeface.createFromAsset(mContext.getAssets(), DEFAULT_SANS_ITALIC_FONT_FILENAME);
        final Typeface boldItalic = Typeface.createFromAsset(mContext.getAssets(), DEFAULT_SANS_BOLD_ITALIC_FONT_FILENAME);
        final Typeface normal = Typeface.createFromAsset(mContext.getAssets(), DEFAULT_SANS_NORMAL_FONT_FILENAME);

        setTypeFaceDefaults(normal, bold, italic, boldItalic, sans_idx);
    }

    private void setDefaultFontForTypeFaceSansSerif() throws NoSuchFieldException, IllegalAccessException {
        final Typeface bold = Typeface.createFromAsset(mContext.getAssets(), DEFAULT_SERIF_BOLD_FONT_FILENAME);
        final Typeface italic = Typeface.createFromAsset(mContext.getAssets(), DEFAULT_SERIF_ITALIC_FONT_FILENAME);
        final Typeface boldItalic = Typeface.createFromAsset(mContext.getAssets(), DEFAULT_SERIF_BOLD_ITALIC_FONT_FILENAME);
        final Typeface normal = Typeface.createFromAsset(mContext.getAssets(), DEFAULT_SERIF_NORMAL_FONT_FILENAME);

        setTypeFaceDefaults(normal, bold, italic, boldItalic, serif_idx);
    }

    private void setDefaultFontForTypeFaceMonospace() throws NoSuchFieldException, IllegalAccessException {

        final Typeface bold = Typeface.createFromAsset(mContext.getAssets(), DEFAULT_MONOSPACE_BOLD_FONT_FILENAME);
        final Typeface italic = Typeface.createFromAsset(mContext.getAssets(), DEFAULT_MONOSPACE_ITALIC_FONT_FILENAME);
        final Typeface boldItalic = Typeface.createFromAsset(mContext.getAssets(), DEFAULT_MONOSPACE_BOLD_ITALIC_FONT_FILENAME);
        final Typeface normal = Typeface.createFromAsset(mContext.getAssets(), DEFAULT_MONOSPACE_NORMAL_FONT_FILENAME);

        setTypeFaceDefaults(normal, bold, italic, boldItalic, monospace_idx);
    }

    private void logFontError(Throwable e) {
        Log.e("font_override", "Error overriding font", e);
    }

}
